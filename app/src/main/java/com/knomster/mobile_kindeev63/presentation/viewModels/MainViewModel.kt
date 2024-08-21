package com.knomster.mobile_kindeev63.presentation.viewModels

import android.app.Application
import android.content.Context
import android.location.Location
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.knomster.mobile_kindeev63.data.apis.BoredApiImpl
import com.knomster.mobile_kindeev63.data.apis.NagerApiImpl
import com.knomster.mobile_kindeev63.data.apis.PlacesApiImpl
import com.knomster.mobile_kindeev63.data.apis.WeatherApiImpl
import com.knomster.mobile_kindeev63.data.database.CacheRepositoryImpl
import com.knomster.mobile_kindeev63.data.database.NoteRepositoryImpl
import com.knomster.mobile_kindeev63.domain.entities.DetailPlaceData
import com.knomster.mobile_kindeev63.domain.entities.Holiday
import com.knomster.mobile_kindeev63.domain.entities.PlacesPage
import com.knomster.mobile_kindeev63.domain.entities.WeatherWidgetData
import com.knomster.mobile_kindeev63.domain.entities.database.Note
import com.knomster.mobile_kindeev63.domain.useCases.AdviceUseCase
import com.knomster.mobile_kindeev63.domain.useCases.CacheUseCase
import com.knomster.mobile_kindeev63.domain.useCases.LocationUseCase
import com.knomster.mobile_kindeev63.domain.useCases.NagerUseCase
import com.knomster.mobile_kindeev63.domain.useCases.NotesDatabaseUseCase
import com.knomster.mobile_kindeev63.domain.useCases.PlacesUseCase
import com.knomster.mobile_kindeev63.domain.useCases.UserPreferenceUseCase
import com.knomster.mobile_kindeev63.domain.useCases.WeatherUseCase
import com.knomster.mobile_kindeev63.presentation.MainApp
import com.knomster.userslibrary.data.UsersApiImpl
import com.knomster.userslibrary.data.UsersRepositoryImpl
import com.knomster.userslibrary.domain.UserGeneratorUseCase
import com.knomster.userslibrary.domain.UsersDatabaseUseCase
import com.knomster.userslibrary.domain.entities.RandomUser
import com.knomster.userslibrary.domain.entities.UserData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.concurrent.thread

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val weatherApi = WeatherApiImpl()
    private val placesApi = PlacesApiImpl()
    private val boredApi = BoredApiImpl()
    private val nagerApi = NagerApiImpl()
    private val cacheDao = (application as MainApp).appDataBase.cacheDao()
    private val cacheRepository = CacheRepositoryImpl(cacheDao)
    private val weatherUseCase = WeatherUseCase(weatherApi)
    private val placesUseCase = PlacesUseCase(placesApi)
    private val adviceUseCase = AdviceUseCase(boredApi)
    private val nagerUseCase = NagerUseCase(nagerApi)
    private val locationUseCase = LocationUseCase(application.applicationContext)
    private val cacheUseCase = CacheUseCase(cacheRepository)

    private val usersDao = (application as MainApp).usersDataBase.usersDao()
    private val usersRepository = UsersRepositoryImpl(usersDao)
    private val usersApi = UsersApiImpl()
    private val usersDatabaseUseCase = UsersDatabaseUseCase(usersRepository)
    private val userGeneratorUseCase = UserGeneratorUseCase(usersApi)
    private val userPreferenceUseCase = UserPreferenceUseCase(
        application.applicationContext.getSharedPreferences(
            "user",
            Context.MODE_PRIVATE
        )
    )

    private val noteDao = (application as MainApp).appDataBase.noteDao()
    private val noteRepository = NoteRepositoryImpl(noteDao)
    private val notesDatabaseUseCase = NotesDatabaseUseCase(noteRepository)

    private val _account = MutableLiveData<UserData?>(null)
    val account: LiveData<UserData?> = _account
    val allNotes: LiveData<List<Note>> = notesDatabaseUseCase.getAllNotes()

    fun deleteNotes(ids: List<Int>) = viewModelScope.launch {
        notesDatabaseUseCase.deleteNotes(ids)
    }

    fun getWeather(latitude: Double, longitude: Double, onGet: (WeatherWidgetData) -> Unit) =
        thread {
            onGet(weatherUseCase.getWeather(latitude, longitude))
        }

    fun getAllPlaces(latitude: Double, longitude: Double, onGet: (List<PlacesPage>) -> Unit) =
        thread {
            onGet(placesUseCase.getAllPlaces(latitude, longitude))
        }

    fun getPlaceData(id: String, onGet: (DetailPlaceData?) -> Unit) = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            placesUseCase.getPlaceData(
                id = id,
                cacheUseCase = cacheUseCase,
                onGet = onGet
            )
        }
    }

    fun getLocation(
        getPermissionQuery: (() -> Unit)? = null,
        onGetLocation: (Location) -> Unit
    ) = viewModelScope.launch {
        locationUseCase.getLocation(getPermissionQuery, onGetLocation)
    }

    fun validateCache() = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            cacheUseCase.validateCache()
        }
    }

    fun generateUser(onGenerate: (RandomUser?) -> Unit) = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            while (true) {
                val user = userGeneratorUseCase.generateUser()
                if (user == null) {
                    onGenerate(null)
                    break
                }
                if (usersDatabaseUseCase.checkUserLogin(user.login)) {
                    onGenerate(user)
                    break
                }
            }
        }
    }

    /**
     * Вход в аккаунт если он сохранён
     */
    fun loginWithPreferences() {
        if (userPreferenceUseCase.hasData()) {
            val userPreferenceData = userPreferenceUseCase.getData()
            viewModelScope.launch {
                usersDatabaseUseCase.loginWithEncryptedPassword(
                    login = userPreferenceData.login,
                    encryptedPassword = userPreferenceData.password.replace("\n", " "),
                    onLogin = { user ->
                        _account.postValue(user)
                    },
                    onFail = { userPreferenceUseCase.deleteData() }
                )
            }
        }
    }

    fun loginToAccount(
        login: String,
        password: String,
        onFail: () -> Unit
    ) = viewModelScope.launch {
        usersDatabaseUseCase.loginUser(
            login = login,
            password = password,
            onLogin = { user ->
                _account.postValue(user)
                userPreferenceUseCase.insertData(
                    login = user.login,
                    password = user.password
                )
            },
            onFail = onFail
        )
    }

    fun registerAccount(
        randomUser: RandomUser,
        password: String
    ) = viewModelScope.launch {
        usersDatabaseUseCase.registerUser(
            randomUser = randomUser,
            password = password,
            onRegister = { user ->
                _account.postValue(user)
                userPreferenceUseCase.insertData(
                    login = user.login,
                    password = user.password
                )
            }
        )
    }

    fun logOut() {
        userPreferenceUseCase.deleteData()
        _account.postValue(null)
    }

    suspend fun makeEmptyNote(noteId: Int, placeId: String? = null) {
        val note = Note(
            id = noteId,
            date = null,
            title = "",
            text = "",
            pinnedPlaceId = placeId
        )
        notesDatabaseUseCase.insertNote(note)
    }

    fun insertNoteAnd(note: Note, function: () -> Unit) = viewModelScope.launch {
        notesDatabaseUseCase.insertNote(note)
        function()
    }

    fun getNoteById(id: Int, function: (Note?) -> Unit) = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            val note = notesDatabaseUseCase.getNoteById(id)
            function(note)
        }
    }

    fun randomAdvice(function: (String) -> Unit) = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            val advice = adviceUseCase.randomAdvice()
            function(advice)
        }
    }

    fun getNextHoliday(function: (Holiday?) -> Unit) = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            val holiday = nagerUseCase.getNextHoliday()
            function(holiday)
        }
    }
}