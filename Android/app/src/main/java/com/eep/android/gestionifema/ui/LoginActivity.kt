package com.eep.android.gestionifema.ui

import android.util.Log
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.NavigateNext
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.eep.android.gestionifema.IfemaApp
import com.eep.android.gestionifema.R
import com.eep.android.gestionifema.api.ApiClient
import com.eep.android.gestionifema.ui.theme.GestionIFEMATheme
import com.eep.android.gestionifema.model.LoginRequest
import com.eep.android.gestionifema.model.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun LoginScreen(navController: NavHostController) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var selectedRole by remember { mutableStateOf("User") }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF6200EE))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            elevation = CardDefaults.cardElevation(4.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Login / Register", style = MaterialTheme.typography.headlineMedium)

                EditTextField(
                    label = R.string.email_message,
                    value = email,
                    onValueChanged = { email = it },
                    keyboardType = KeyboardType.Email,
                    leadingIcon = {
                        Icon(Icons.Filled.Email, contentDescription = "Email Icon")
                    }
                )

                // Campo de texto para la contraseña
                EditTextField(
                    label = R.string.password_message,
                    value = password,
                    onValueChanged = { password = it },
                    keyboardType = KeyboardType.Password,
                    leadingIcon = {
                        Icon(Icons.Filled.Lock, contentDescription = "Lock Icon")
                    },
                    trailingIcon = {
                        IconButton(onClick = { passwordVisible = !passwordVisible }) {
                            Icon(
                                imageVector = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                                contentDescription = "Toggle Password Visibility"
                            )
                        }
                    }
                )

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    verticalArrangement = Arrangement.Center
                ) {
                    RoleSelection(selectedRole, onRoleChanged = { selectedRole = it })
                }

                Button(
                    onClick = { performLogin(email, password, "User", navController) },
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .align(Alignment.End),
                    enabled = email.isNotEmpty() && password.isNotEmpty()
                ) {
                    Icon(Icons.Filled.NavigateNext, contentDescription = "Go")
                }

//                Row(
//                    modifier = Modifier.padding(top = 8.dp),
//                    verticalAlignment = Alignment.CenterVertically,
//                    horizontalArrangement = Arrangement.Center
//                ) {
//                    Text("Don't have an Account? ")
//                    TextButton(onClick = { /* TODO: Navigate to Sign Up */ }) {
//                        Text("Sign Up", color = Color.Blue)
//                    }
//                }
            }
        }
    }
}


fun performLogin(email: String, password: String, role: String, navController: NavHostController) {
    val request = LoginRequest(email, password, role)
    ApiClient.retrofitService.loginUser(request).enqueue(object : Callback<User> {
        override fun onResponse(call: Call<User>, response: Response<User>) {
            if (response.isSuccessful) {
                val user = response.body()
                Log.d("LoginActivity", "Login successful: $user")
                // Navegar basado en el rol del usuario
                if (user != null) {
                    if (user.rol == "User") {
                        navController.navigate("userScreen/${user.id}")


                    } else if (user.rol == "Owner") {
                        navController.navigate(Screen.Owner)
                    }
                }
            } else {
                Log.e("LoginActivity", "Login error: ${response.errorBody()?.string()}")

            }
        }

        override fun onFailure(call: Call<User>, t: Throwable) {
            Log.e("LoginActivity", "Login failed", t)
        }
    })
}



@Composable
fun EditTextField(
    @StringRes label: Int,
    value: String,
    onValueChanged: (String) -> Unit,
    keyboardType: KeyboardType,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChanged,
        singleLine = true,
        label = { Text(stringResource(label)) },
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = keyboardType,
            imeAction = if (label == R.string.password_message) ImeAction.Done else ImeAction.Next
        ),
        visualTransformation = if (keyboardType == KeyboardType.Password) PasswordVisualTransformation() else VisualTransformation.None
    )
}



@Composable
fun RoleSelection(selectedRole: String, onRoleChanged: (String) -> Unit) {
    val roles = listOf("User", "Owner")
    Row (
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ){
        roles.forEach { role ->
            Row(
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
        IfemaApp()
    }
}


