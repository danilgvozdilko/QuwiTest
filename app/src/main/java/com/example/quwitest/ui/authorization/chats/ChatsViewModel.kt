package com.example.quwitest.ui.authorization.chats

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quwitest.data.Channels
import com.example.quwitest.network.ApiService
import kotlinx.coroutines.launch

class ChatsViewModel(private val apiService: ApiService) : ViewModel() {

    private val _users = MutableLiveData<Channels>()
    val users: LiveData<Channels> = _users

    fun getDialogs() {
        viewModelScope.launch {

            _users.value = apiService.getChannels()
        }
    }
}