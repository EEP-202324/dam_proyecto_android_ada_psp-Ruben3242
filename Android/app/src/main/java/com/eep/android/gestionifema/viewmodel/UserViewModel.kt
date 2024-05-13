package com.eep.android.gestionifema.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eep.android.gestionifema.api.ApiClientCenters

import com.eep.android.gestionifema.api.ApiClientUsers
import com.eep.android.gestionifema.model.Center
import com.eep.android.gestionifema.model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class UserViewModel : ViewModel() {
        private val _user = MutableStateFlow<User?>(null)
        val user: StateFlow<User?> = _user.asStateFlow()

        private val _centers = MutableStateFlow<List<Center>>(emptyList())
        val centers: StateFlow<List<Center>> = _centers.asStateFlow()

        fun getUserById(userId: Int) {
            viewModelScope.launch {
                try {
                    val response = ApiClientUsers.retrofitService.getUserById(userId)
                    if (response.isSuccessful) {
                        _user.value = response.body()
                        Log.d("UserViewModel", "User fetched: ${_user.value}")
                    } else {
                        Log.e("UserViewModel", "Error fetching user: ${response.errorBody()?.string()}")
                    }
                } catch (e: Exception) {
                    Log.e("UserViewModel", "Network error", e)
                }
            }
        }

        fun getCenters() {
            viewModelScope.launch {
                try {
                    val response = ApiClientCenters.retrofitService.getCenters()
                    if (response.isSuccessful) {
                        _centers.value = response.body() ?: emptyList()
                        Log.d("UserViewModel", "Centers fetched: ${_centers.value}")
                    } else {
                        Log.e("UserViewModel", "Error fetching centers: ${response.errorBody()?.string()}")
                    }
                } catch (e: Exception) {
                    Log.e("UserViewModel", "Network error", e)
                }
            }
        }

    fun updateUser(userId: Int, updatedUser: User, onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            try {
                // Asegurando que la contraseña no se incluya si no es necesario cambiarla
                val userToUpdate = updatedUser.copy() // Aquí se elimina la contraseña del objeto
                val response = ApiClientUsers.retrofitService.updateUserById(userId, userToUpdate)
                if (response.isSuccessful) {
                    _user.value = response.body()
                    onSuccess()
                } else {
                    onError("Error al actualizar el usuario: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                onError("Error de red: ${e.localizedMessage}")
                Log.e("UserViewModel", "Network error", e)
            }
        }
    }

}