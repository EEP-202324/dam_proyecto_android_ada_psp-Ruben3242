package com.eep.android.gestionifema.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.eep.android.gestionifema.api.ApiClient
import com.eep.android.gestionifema.api.ApiService
import com.eep.android.gestionifema.model.LoginRequest
import com.eep.android.gestionifema.model.User
import com.eep.android.gestionifema.ui.Screen

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.Response



class LoginViewModel : ViewModel() {

    fun loginUser(email: String, password: String, role: String, onSuccess: (User?) -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            try {
                val request = LoginRequest(email, password, role)
                val response = ApiClient.retrofitService.loginUser(request)
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