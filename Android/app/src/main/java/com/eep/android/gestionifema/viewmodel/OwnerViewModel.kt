package com.eep.android.gestionifema.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.eep.android.gestionifema.api.ApiClientCenters
import com.eep.android.gestionifema.model.Center

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class OwnerViewModel : ViewModel() {
    private val _centers = MutableStateFlow<List<Center>>(emptyList())
    val centers: StateFlow<List<Center>> = _centers.asStateFlow()

    private val _center = MutableStateFlow<Center?>(null)
    val center: StateFlow<Center?> = _center.asStateFlow()
    fun getCenters() {
        viewModelScope.launch {
            try {
                val response = ApiClientCenters.retrofitService.getCenters()
                if (response.isSuccessful) {
                    _centers.value = response.body() ?: emptyList()
                    Log.d("OwnerViewModel", "Centers fetched: ${_centers.value}")
                } else {
                    Log.e("OwnerViewModel", "Error fetching centers: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                Log.e("OwnerViewModel", "Network error", e)
            }
        }
    }

    fun getCenterById(centerId: Int, onSuccess: (Center) -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            try {
                val response = ApiClientCenters.retrofitService.getCenterById(centerId)
                if (response.isSuccessful) {
                    _center.value = response.body()
                    onSuccess(response.body()!!)
                    Log.d("OwnerViewModel", "Center fetched: ${_center.value}")
                } else {
                    onError(response.errorBody()?.string() ?: "Unknown error")
                    Log.e("OwnerViewModel", "Error fetching center: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                onError(e.message ?: "Unknown error")
                Log.e("OwnerViewModel", "Network error", e)
            }
        }
    }

    fun updateCenter(centerId: Int, updatedCenter: Center, onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            try {
                val response = ApiClientCenters.retrofitService.updateCenterById(centerId, updatedCenter)
                if (response.isSuccessful) {
                    onSuccess()
                } else {
                    onError(response.errorBody()?.string() ?: "Unknown error")
                }
            } catch (e: Exception) {
                onError(e.message ?: "Unknown error")
            }
        }
    }

    fun createCenter(center: Center, onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            try {
                val response = ApiClientCenters.retrofitService.createCenter(center)
                if (response.isSuccessful) {
                    onSuccess()
                } else {
                    onError(response.errorBody()?.string() ?: "Unknown error")
                }
            } catch (e: Exception) {
                onError(e.message ?: "Unknown error")
            }
        }
    }

    fun deleteCenter(centerId: Int, onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            try {
                ApiClientCenters.retrofitService.deleteCenterById(centerId)
                onSuccess()
            } catch (e: Exception) {
                onError(e.message ?: "Unknown error")
            }
        }
    }

}