package com.eep.android.gestionifema.ui

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.eep.android.gestionifema.api.ApiClient
import com.eep.android.gestionifema.model.Center
import com.eep.android.gestionifema.model.User
import com.eep.android.gestionifema.ui.theme.GestionIFEMATheme
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

//val dummyCenters = listOf(
//    Center(1, "Centro 1", "www.centro1.com", 1),
//    Center(2, "Centro 2", "www.centro2.com", 2),
//    Center(3, "Centro 3", "www.centro3.com", 3)
//)
var listaCentros = mutableListOf<Center>()
var listaCentroMostrar = ""

@Composable
fun UserScreen(navController: NavHostController, userId: Int) {
    var nombre by remember { mutableStateOf("") }
    var edad by remember { mutableStateOf("") }
    var selectedCenter by remember { mutableStateOf<Center?>(null) }
    var mostarlista by remember { mutableStateOf(false) }
//    val centers by remember { mutableStateOf(dummyCenters) } // Supongamos que tienes una lista de centros

    LaunchedEffect(key1 = Unit) {
        obtenerCentros()
    }
    Column(modifier = Modifier.padding(16.dp)) {
        // Formulario para introducir nombre y edad
        OutlinedTextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text("Nombre") },
            modifier = Modifier.fillMaxWidth(),
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = edad,
            onValueChange = { edad = it.filter { it.isDigit() } },
            label = { Text("Edad") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        // Lista de centros
        TextButton(onClick = {mostarlista = !mostarlista}) {
            Text("Mostrar centros", style = MaterialTheme.typography.headlineSmall)
        }
        if (mostarlista){
            Text(listaCentros.toString())
        }

        Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
            listaCentros.forEach { center ->
                CenterListItem(center = center) {
                    selectedCenter = center
                }
            }
        }

        // Botón para obtener más información del centro seleccionado
        Button(
            onClick = {
                selectedCenter?.let { center ->
                    // Aquí iría la lógica para mostrar más información del centro
                }
            },
            enabled = selectedCenter != null
        ) {
            Text("Obtener más información del centro")
        }

        // Botón para añadir el centro seleccionado al usuario
        Button(
            onClick = {
                selectedCenter?.let { center ->
                    // Aquí iría la lógica para añadir el centro al usuario
                    // Por ejemplo:
                    // usuario.centrosVisita.add(center.nombre)
                }
            },
            enabled = selectedCenter != null
        ) {
            Text("Añadir centro al usuario")
        }

        // Botón para cerrar sesión y volver al login
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { navController.navigate(Screen.Login) }) {
            Text("Cerrar sesión")
        }

        // Botón para terminar de tramitar todo
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = {
//                navController.navigate(Screen.TramiteFinalizado);
                addUser(userId, nombre, edad, selectedCenter)
            // Navegar a la pantalla de trámite finalizado
            }
        ) {
            Text("Tramitar")
        }
    }
}

fun addUser(userId: Int, nombre: String, edad: String, selectedCenter: Center?) {
    val updatedUser = User(
        id = userId,
        nombre = nombre,
        email = "",  // Debes manejar este valor de acuerdo a tus necesidades
        password = "",  // Idealmente no deberías manejar contraseñas así
        rol = "",  // Actualiza según corresponda
        centroVisita = selectedCenter?.nombreCentro ?: "",
        edad = edad.toIntOrNull() ?: 0
    )

    ApiClient.retrofitService.updateUserById(userId, updatedUser).enqueue(object : Callback<User> {
        override fun onResponse(call: Call<User>, response: Response<User>) {
            if (response.isSuccessful) {
                Log.d("UserScreen", "Usuario actualizado correctamente")
                // Aquí puedes manejar navegación o mostrar un mensaje de éxito
            } else {
                Log.e("UserScreen", "Error al actualizar el usuario: ${response.errorBody()?.string()}")
            }
        }

        override fun onFailure(call: Call<User>, t: Throwable) {
            Log.e("UserScreen", "Fallo al actualizar el usuario", t)
        }
    })
}

@Composable
fun CenterListItem(center: Center, onItemClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onItemClick)
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(text = center.nombreCentro, style = MaterialTheme.typography.bodySmall)
            Text(text = center.paginaWeb, style = MaterialTheme.typography.bodySmall)
            Text(text = "Stand: ${center.stand}", style = MaterialTheme.typography.bodySmall)
        }
        IconButton(onClick = onItemClick) {
            Icon(Icons.Filled.Info, contentDescription = "Más información")
        }
    }
}



fun obtenerCentros() {
    ApiClient.retrofitService.getCenters().enqueue(object : Callback<List<Center>> {
    override fun onResponse(call: Call<List<Center>>, response: Response<List<Center>>) {
            if (response.isSuccessful) {
                listaCentros = response.body() as MutableList<Center>
                Log.d("UserScreen", "Centros obtenidos: $listaCentros")
            }
        }



        override fun onFailure(call: Call<List<Center>>, t: Throwable) {
            Log.e("UserScreen", "Error al obtener los centros", t)
        }
    })
}




@Preview(showBackground = true)
@Composable
fun PreviewUserScreen() {
    GestionIFEMATheme {
        UserScreen(navController = rememberNavController(), userId = 1)
    }
}
