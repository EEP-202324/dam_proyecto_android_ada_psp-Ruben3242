package com.eep.android.gestionifema.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.eep.android.gestionifema.api.ApiClientLogin

import com.eep.android.gestionifema.model.LoginRequest
import com.eep.android.gestionifema.model.User

import kotlinx.coroutines.launch


class LoginViewModel : ViewModel() {

    fun loginUser(
        email: String,
        password: String,
        role: String,
        onSuccess: (User?) -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            try {
                val request = LoginRequest(email, password, role)
                val response = ApiClientLogin.retrofitService.loginUser(request)
                if (response.isSuccessful) {
                    onSuccess(response.body())
                } else {
                    onError(response.errorBody()?.string() ?: "Unknown error")
                }
            } catch (e: Exception) {
                onError(e.message ?: "Network error")
            }
        }
    }
}