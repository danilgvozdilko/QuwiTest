package com.example.quwitest.ui.authorization

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quwitest.data.AuthRequest
import com.example.quwitest.data.AuthResponse
import com.example.quwitest.network.ApiService
import kotlinx.coroutines.launch

class AuthorizationViewModel(
    private val apiService: ApiService
) : ViewModel() {

    private var password: String? = ""
    private var email: String? = ""
    private val _users = MutableLiveData<AuthResponse>()
    val users: LiveData<AuthResponse> = _users

    fun setEmail(email:String){
        this.email = email
    }

    fun setPassword(password: String) {
        this.password = password
    }

     fun authUser(email: String, password: String) {
        viewModelScope.launch {
            _users.value = apiService.login(AuthRequest(email = email, password = password))
        }
    }
}