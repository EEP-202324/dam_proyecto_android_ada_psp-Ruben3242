package com.eep.android.gestionifema.ui

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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


var listaCentros = mutableListOf<Center>()


@Composable
fun UserScreen(navController: NavHostController, userId: Int) {
    var nombre by remember { mutableStateOf("") }
    var edad by remember { mutableStateOf("") }
    var selectedCenter by remember { mutableStateOf<Center?>(null) }

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
//        CenterListItem(center = Center(1, "Centro de Prueba", 2, "Type", "Prueba")) {
//
//        }
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = edad,
            onValueChange = { edad = it.filter { it.isDigit() } },
            label = { Text("Edad") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        Row {
            Button(
                onClick = {
                    addUser(userId, nombre, edad, selectedCenter)
                },
                modifier = Modifier.weight(1f)
            ) {
                Text("Guardar")
            }
            Spacer(modifier = Modifier.width(8.dp))
            Button(
                onClick = {
                    navController.navigate(Screen.Login)
                },
                modifier = Modifier.weight(1f)
            ) {
                Text("Cerrar sesión")
            }
        }
//        Row {
//            Spacer(modifier = Modifier.height(16.dp))
//            Button(onClick = { }) {
//                Text("Cerrar sesión")
//            }
//
//            // Botón para terminar de tramitar todo
//            Spacer(modifier = Modifier.height(8.dp))
//            Button(
//                onClick = {
////                navController.navigate(Screen.TramiteFinalizado);
//                    addUser(userId, nombre, edad, selectedCenter)
//                    // Navegar a la pantalla de trámite finalizado
//                }
//            ) {
//                Text("Tramitar")
//            }
//        }

        Text("Centros:", style = MaterialTheme.typography.headlineSmall)
        LazyColumn {
            items(listaCentros) { center ->
                CenterListItem(center = center, navController = navController) {
                    val newCenter = center.copy(
//                        isExpanded = !center.isExpanded,
                        type = center.type ?: "Default Type"
                    )
                    listaCentros[listaCentros.indexOf(center)] = newCenter
                }
            }
        }




        // Botón para obtener más información del centro seleccionado


        // Botón para cerrar sesión y volver al login

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
                Log.e(
                    "UserScreen",
                    "Error al actualizar el usuario: ${response.errorBody()?.string()}"
                )
            }
        }

        override fun onFailure(call: Call<User>, t: Throwable) {
            Log.e("UserScreen", "Fallo al actualizar el usuario", t)
        }
    })
}

@Composable
fun CenterListItem(
    center: Center,
    navController: NavHostController,
    onExpandClick: (Center) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { navController.navigate("centerDetail/${center.id}") },  // Asegúrate de que center.id es un Int
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    )  {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.School,
                contentDescription = null,  // Considera agregar una descripción adecuada
                modifier = Modifier.size(40.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(center.nombreCentro, style = MaterialTheme.typography.titleMedium)
                Text(center.paginaWeb, style = MaterialTheme.typography.bodySmall)
//                if (center.isExpanded) {
//                    // Asumiendo que tienes más información para mostrar cuando está expandido
//                    Text("Información adicional aquí")
//                }
            }
            Spacer(modifier = Modifier.weight(1f))
            IconButton(
                onClick = {
                    // Aquí es donde usas el código para expandir y actualizar el objeto
                    onExpandClick(center)
                }
            ) {
                Icon(Icons.Default.Info, contentDescription = "Más información")
            }
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
@Preview(showBackground = true)
@Composable
fun centros() {
    GestionIFEMATheme {
        CenterListItem(center = Center(1, "Centro de Prueba", "www", "Type", "Esta es la descripscion"), navController = rememberNavController()) {

        }
    }
}

