package com.knomster.mobile_kindeev63.presentation.viewModels

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.knomster.mobile_kindeev63.domain.entities.PlacesPage
import com.knomster.mobile_kindeev63.domain.entities.WeatherWidgetData

class PlacesListScreenViewModel(private val mainViewModel: MainViewModel) : ViewModel() {
    private val _weatherData = MutableLiveData<WeatherWidgetData>(WeatherWidgetData.Load)
    val weatherData: LiveData<WeatherWidgetData> = _weatherData
    private val _placesPages = MutableLiveData<List<PlacesPage>?>(null)
    val placesPages: LiveData<List<PlacesPage>?> = _placesPages
    private val _currentPage = MutableLiveData(1)
    val currentPage: LiveData<Int> = _currentPage

    fun getDataFromApis() {
        mainViewModel.getLocation { location ->
            mainViewModel.getWeather(
                latitude = location.latitude,
                longitude = location.longitude,
                onGet = {
                    Handler(Looper.getMainLooper()).post {
                        _weatherData.postValue(it)
                    }
                }
            )
        }
        mainViewModel.getLocation { location ->
            mainViewModel.getAllPlaces(
                latitude = location.latitude,
                longitude = location.longitude,
                onGet = {
                    Handler(Looper.getMainLooper()).post {
                        _placesPages.postValue(it)
                    }
                }
            )
        }
    }

    fun locationPermissionNotGranted() {
        _weatherData.postValue(WeatherWidgetData.LocationError)
        _placesPages.postValue(emptyList())
    }

    fun setPage(page: Int) {
        _currentPage.postValue(page)
    }
}