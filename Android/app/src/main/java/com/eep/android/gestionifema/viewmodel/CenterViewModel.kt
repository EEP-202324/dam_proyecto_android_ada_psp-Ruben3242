package com.eep.android.gestionifema.viewmodel
//
//import android.util.Log
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import androidx.navigation.NavHostController
//import kotlinx.coroutines.flow.MutableStateFlow
//import kotlinx.coroutines.flow.asStateFlow
//import kotlinx.coroutines.launch
//import com.eep.android.gestionifema.api.ApiClient
//import com.eep.android.gestionifema.model.LoginRequest
//import com.eep.android.gestionifema.model.User
//import com.eep.android.gestionifema.ui.Screen
//
//import retrofit2.Response
//
//class CenterViewModel : ViewModel() {
//
//
//    private val _email = MutableStateFlow("")
//    val email = _email.asStateFlow()
//
//    private val _password = MutableStateFlow("")
//    val password = _password.asStateFlow()
//
//    private val _passwordVisible = MutableStateFlow(false)
//    val passwordVisible = _passwordVisible.asStateFlow()
//
//    private val _selectedRole = MutableStateFlow("User")
//    val selectedRole = _selectedRole.asStateFlow()
//
//    private val _user = MutableStateFlow<User?>(null)
//    val user = _user.asStateFlow()
//
//    fun updateEmail(newEmail: String) {
//        _email.value = newEmail
//    }
//
//    fun updatePassword(newPassword: String) {
//        _password.value = newPassword
//    }
//
//    fun togglePasswordVisibility() {
//        _passwordVisible.value = !_passwordVisible.value
//    }
//
//    fun updateRole(newRole: String) {
//        _selectedRole.value = newRole
//    }
//
//    fun login(navController: NavHostController) {
//        viewModelScope.launch {
//            val response = ApiClient.retrofitService.loginUser(
//                LoginRequest(
//                    _email.value,
//                    _password.value,
//                    _selectedRole.value
//                )
//            )
//            if (response.isSuccessful && response.body() != null) {
//                _user.value = response.body()
//                if (_user.value?.rol == "User") {
//                    navController.navigate("userScreen/${_user.value!!.id}")
//                } else if (_user.value?.rol == "Owner") {
//                    navController.navigate(Screen.Owner)
//                }
//            } else {
//                // Handle login failure
//                Log.e("LoginViewModel", "Login failed")
//            }
//        }
//    }
//}

