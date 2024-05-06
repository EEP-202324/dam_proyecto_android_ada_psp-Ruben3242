package com.eep.android.gestionifema.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.eep.android.gestionifema.api.ApiClient
import com.eep.android.gestionifema.model.Center
import com.eep.android.gestionifema.model.User
import com.eep.android.gestionifema.ui.theme.GestionIFEMATheme
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun UserScreen(navController: NavHostController) {
    var user by remember { mutableStateOf(User(id = 1, nombre = "John Doe", email = "john@example.com", password = "password123", rol = "User", centroVisita = "", edad = 30)) }
    var centers by remember { mutableStateOf(listOf<Center>()) }
    

    LaunchedEffect(key1 = userId) {
        ApiClient.retrofitService.getUserById(userId).enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                if (response.isSuccessful) {
                    user = response.body() ?: user
                }
            }
            override fun onFailure(call: Call<User>, t: Throwable) {
                // Handle failure
            }
        })

        ApiClient.retrofitService.getCenters().enqueue(object : Callback<List<Center>> {
            override fun onResponse(call: Call<List<Center>>, response: Response<List<Center>>) {
                if (response.isSuccessful) {
                    centers = response.body() ?: listOf()
                }
            }
            override fun onFailure(call: Call<List<Center>>, t: Throwable) {
                // Handle failure
            }
        })
    }

    UserContent(user, onUpdateUser = { updatedUser -> user = updatedUser }, navController = navController)
}

@Composable
fun UserContent(user: User, onUpdateUser: (User) -> Unit, navController: NavHostController) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text("Bienvenido, ${user.nombre}", style = MaterialTheme.typography.headlineMedium)
        UserInfoForm(user, onUpdateUser)
        // Example of logout button - you would need to implement actual navigation logic
        Button(onClick = { navController.navigate("login")  }) {
            Text("Cerrar sesión")
        }
    }
}

@Composable
fun UserInfoForm(user: User, onUpdateUser: (User) -> Unit) {
    var nombre by remember { mutableStateOf(user.nombre) }
    var edad by remember { mutableStateOf(user.edad.toString()) }

    OutlinedTextField(
        value = nombre,
        onValueChange = { nombre = it },
        label = { Text("Nombre") },
        modifier = Modifier.fillMaxWidth()
    )
    Spacer(modifier = Modifier.height(8.dp))
    OutlinedTextField(
        value = edad,
        onValueChange = { newEdad ->
            edad = newEdad.filter { it.isDigit() }
        },
        label = { Text("Edad") },
        modifier = Modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
    )
    Button(onClick = { onUpdateUser(user.copy(nombre = nombre, edad = edad.toIntOrNull() ?: user.edad)) }) {
        Text("Actualizar datos")
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewUserScreen() {
    GestionIFEMATheme {
        UserScreen(NavHostController(LocalContext.current), 1)
    }
}
