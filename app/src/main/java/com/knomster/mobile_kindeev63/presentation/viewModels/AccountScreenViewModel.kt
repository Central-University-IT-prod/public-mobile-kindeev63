package com.knomster.mobile_kindeev63.presentation.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.knomster.mobile_kindeev63.presentation.entities.AccountScreenSate
import com.knomster.userslibrary.domain.entities.RandomUser
import com.knomster.userslibrary.domain.entities.UserData

class AccountScreenViewModel(val mainViewModel: MainViewModel) : ViewModel() {
    private val _accountScreenState = MutableLiveData<AccountScreenSate>(AccountScreenSate.SignIn)
    val accountScreenSate: LiveData<AccountScreenSate> = _accountScreenState

    fun setAccountInformation(userData: UserData?) {
        if (userData == null) {
            if (_accountScreenState.value is AccountScreenSate.AccountInformation) {
                _accountScreenState.postValue(AccountScreenSate.SignIn)
            }
        } else {
            _accountScreenState.postValue(
                AccountScreenSate.AccountInformation(userData = userData)
            )
        }

    }

    fun goToSignIn() {
        _accountScreenState.postValue(AccountScreenSate.SignIn)
    }

    fun goToSignUp() {
        _accountScreenState.postValue(AccountScreenSate.SignUp())
        mainViewModel.generateUser { randomUser ->
            if (randomUser == null) {
                _accountScreenState.postValue(AccountScreenSate.SignIn)
            } else {
                _accountScreenState.postValue(AccountScreenSate.SignUp(randomUser))
            }
        }
    }

    fun logOut() {
        mainViewModel.logOut()
    }

    fun getAccountInformation(): UserData {
        return (accountScreenSate.value as AccountScreenSate.AccountInformation).userData
    }

    fun signIn(login: String, password: String, onFail: () -> Unit) {
        mainViewModel.loginToAccount(
            login = login,
            password = password,
            onFail = onFail
        )
    }

    fun signUp(randomUser: RandomUser, password: String) {
        mainViewModel.registerAccount(
            randomUser = randomUser,
            password = password
        )
    }
}