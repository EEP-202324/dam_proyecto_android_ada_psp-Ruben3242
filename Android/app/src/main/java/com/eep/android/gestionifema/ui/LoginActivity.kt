package com.eep.android.gestionifema.ui

import android.util.Log
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.eep.android.gestionifema.R
import com.eep.android.gestionifema.api.ApiClient
import com.eep.android.gestionifema.ui.theme.GestionIFEMATheme
import com.eep.android.gestionifema.model.LoginRequest
import com.eep.android.gestionifema.model.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

//var NavHostController: NavHostController = TODO()
@Composable
fun LoginScreen(navController: NavHostController) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var selectedRole by remember { mutableStateOf("User") }

    Column(
        modifier = Modifier
            .statusBarsPadding()
            .padding(horizontal = 40.dp)
            .verticalScroll(rememberScrollState())
            .safeDrawingPadding(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Login / Register", modifier = Modifier.padding(bottom = 16.dp, top = 40.dp))
        EditTextField(
            label = R.string.email_message,
            value = email,
            onValueChanged = { email = it },
            keyboardType = KeyboardType.Password
        )
        EditTextField(label = R.string.password_message, value = password, onValueChanged = { password = it }, keyboardType = KeyboardType.Password)
        RoleSelection(selectedRole, onRoleChanged = { selectedRole = it })

        Button(onClick = {
            performLogin(email, password, selectedRole, navController)
        },
            modifier = Modifier
                .padding(top = 32.dp)
                .fillMaxWidth()) {
            Text("Enter")
        }

        Text(text = "Email: $email, Password: $password, Role: $selectedRole", modifier = Modifier.padding(top = 16.dp))

        Spacer(modifier = Modifier.height(150.dp))
    }
}

fun performLogin(email: String, password: String, role: String, navController: NavHostController) {
    val request = LoginRequest(email, password, role)
    Log.d("LoginActivity", "Logging in with: Email: $email, Password: $password, Role: $role")

    ApiClient.retrofitService.loginUser(request).enqueue(object : Callback<User> {
        override fun onResponse(call: Call<User>, response: Response<User>) {
            if (response.isSuccessful) {
                Log.d("LoginActivity", "Login successful: ${response.body()}")
                navController.navigate(Screen.User) {
                    popUpTo(Screen.Login) { inclusive = true }
                }
            } else {
                Log.e("LoginActivity", "Login failed with response: ${response.errorBody()?.string()}")
            }
        }

        override fun onFailure(call: Call<User>, t: Throwable) {
            Log.e("LoginActivity", "Login failed", t)
//            navController.navigate(Screen.User) {
//                popUpTo(Screen.Login) { inclusive = true }
//            }
        }
    })
}


@Composable
fun EditTextField(
    @StringRes label: Int,
    value: String,
    onValueChanged: (String) -> Unit,
    keyboardType: KeyboardType
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChanged,
        singleLine = true,
        label = { Text(stringResource(label)) },
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = if (label == R.string.password_message) KeyboardType.Password else KeyboardType.Text,
            imeAction = if (label == R.string.password_message) ImeAction.Done else ImeAction.Next
        )
    )
}

@Composable
fun RoleSelection(selectedRole: String, onRoleChanged: (String) -> Unit) {
    val roles = listOf("User", "Owner")
    roles.forEach { role ->
        Row(
            modifier = Modifier
                .padding(vertical = 8.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            RadioButton(
                selected = selectedRole == role,
                onClick = { onRoleChanged(role) }
            )
            Text(role)
        }
    }
}



//fun performLogin(email: String, password: String, role: String, navController: NavHostController) {
//    val request = LoginRequest(email, password, role)
//    Log.d("LoginActivity", "Logging in with: Email: $email, Password: $password, Role: $role")
//
//    ApiClient.retrofitService.loginUser(request).enqueue(object : Callback<User> {
//        override fun onResponse(call: Call<User>, response: Response<User>) {
//            if (response.isSuccessful) {
//                Log.d("LoginActivity", "Login successful: ${response.body()}")
//            } else {
//                Log.e("LoginActivity", "Login failed with response: ${response.errorBody()?.string()}")
//            }
//        }
//
//        override fun onFailure(call: Call<User>, t: Throwable) {
//            Log.e("LoginActivity", "Login failed", t)
//        }
//    })
//}



@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    GestionIFEMATheme{
        LoginScreen(navController = TODO())
    }
}


