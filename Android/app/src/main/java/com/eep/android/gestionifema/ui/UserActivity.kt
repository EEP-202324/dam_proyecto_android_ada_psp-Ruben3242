package com.eep.android.gestionifema.ui

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.eep.android.gestionifema.api.ApiClient
import com.eep.android.gestionifema.model.Center
import com.eep.android.gestionifema.model.User
import com.eep.android.gestionifema.ui.theme.GestionIFEMATheme
import com.eep.android.gestionifema.viewmodel.UserViewModel

var userUpda = User(0, "", "", "", 0, "", "")
@Composable
fun UserScreen(navController: NavHostController, userId: Int) {
    val viewModel: UserViewModel = viewModel()
    val user by viewModel.user.collectAsState()
    val centers by viewModel.centers.collectAsState()
    val context = LocalContext.current
    val selectedCenters = remember { mutableStateListOf<Center>() }

    var selectedCenter by remember { mutableStateOf<Center?>(null) }
    var showSelectedCenters by remember { mutableStateOf(false) }


    var nombre by remember { mutableStateOf(user?.nombre ?: "") }
    var edad by remember { mutableStateOf(user?.edad.toString()) }




    LaunchedEffect(key1 = userId) {
        viewModel.getUserById(userId)
        viewModel.getCenters()
        obtenerUsuario(userId)
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
        Button(
            onClick = {showSelectedCenters = !showSelectedCenters }
        ) {
            Text("Mis Centros")
        }

        if (showSelectedCenters) {
            selectedCenters.forEach { center ->
                CenterCard(center)
            }

            }


//        DropdownMenuCentros(centers = centers, selectedCenter = ) { center ->
//            selectedCenter = center
//        }
        Row {
            Button(
                onClick = {val updatedUser = userUpda.copy(
                    nombre = nombre,
                    edad = edad.toIntOrNull() ?: user?.edad ?: 0,
                    centroVisita = selectedCenters.joinToString { it.nombreCentro },
                    email = user?.email ?: "",  // Mantén el email existente
                     // Mantén la contraseña existente
                    rol = user?.rol ?: ""  // Mantén el rol existente
                )
                    viewModel.updateUser(userId, updatedUser, onSuccess = {
                        Toast.makeText(context, "Usuario actualizado correctamente", Toast.LENGTH_SHORT).show()
                    }, onError = { error ->
                        Toast.makeText(context, error, Toast.LENGTH_LONG).show()
                    })
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

        Text("Centros:", style = MaterialTheme.typography.headlineSmall)
        LazyColumn {
            items(centers) { center ->
                CenterListItem(center, onClick = {
                    if (!selectedCenters.contains(center)) {
                        selectedCenters.add(center)
                    }
                })
            }
        }


        // Botón para obtener más información del centro seleccionado


        // Botón para cerrar sesión y volver al login

    }
}

@Composable
fun CenterCard(center: Center) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(center.nombreCentro, style = MaterialTheme.typography.titleMedium)
            Text(center.paginaWeb, style = MaterialTheme.typography.bodySmall)
        }
    }
}


suspend fun obtenerUsuario(userId: Int) {
    var response = ApiClient.retrofitService.getUserById(userId)
    if (response.isSuccessful) {
         userUpda = response.body()!!
        Log.d("UserScreen", "Usuario obtenido: $userUpda")
    } else {
        Log.e("UserScreen", "Error al obtener el usuario: ${response.errorBody()?.string()}")
    }

}
// fun addUser(userId: Int, nombre: String, edad: String, selectedCenter: Center?) {
//     val response = ApiClient.retrofitService.updateUserById(userId, User(id = userId, nombre = nombre, email = "", password = "", rol = "", centroVisita = selectedCenter?.nombreCentro ?: "", edad = edad.toIntOrNull() ?: 0))
//        if (response.isCanceled) {
//            Log.d("UserScreen", "Usuario actualizado correctamente")
//        } else {
//            Log.e("UserScreen", "Error al actualizar el usuario: ${response.isCanceled?.equals(false)}")
//        }

// }

//fun addUser(userId: Int, nombre: String, edad: String, selectedCenter: Center?) {
//    val updatedUser = User(
//        id = userId,
//        nombre = nombre,
//        email = Usuario.email,  // Debes manejar este valor de acuerdo a tus necesidades
//        password = Usuario.password,  // Idealmente no deberías manejar contraseñas así
//        rol = Usuario.rol,  // Actualiza según corresponda
//        centroVisita = selectedCenter?.nombreCentro ?: "",
//        edad = edad.toIntOrNull() ?: 0
//    )
//
//    ApiClient.retrofitService.updateUserById(userId, updatedUser).enqueue(object : Callback<User> {
//        override fun onResponse(call: Call<User>, response: Response<User>) {
//            if (response.isSuccessful) {
//                Log.d("UserScreen", "Usuario actualizado correctamente")
//            } else {
//                Log.e("UserScreen", "Error al actualizar el usuario: ${response.errorBody()?.string()}")
//            }
//        }
//
//        override fun onFailure(call: Call<User>, t: Throwable) {
//            Log.e("UserScreen", "Error al actualizar el usuario", t)
//        }
//    })
//
//}

@Composable
fun CenterListItem(
    center: Center,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
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

            }
            Spacer(modifier = Modifier.weight(1f))
            IconButton(
                onClick = onClick
            ) {
                Icon(Icons.Default.Add, contentDescription = "Añadir")
            }
        }
    }
}


//fun obtenerCentros() {
//    ApiClient.retrofitService.getCenters().enqueue(object : Callback<List<Center>> {
//        override fun onResponse(call: Call<List<Center>>, response: Response<List<Center>>) {
//            if (response.isSuccessful) {
//                listaCentros = response.body() as MutableList<Center>
//                Log.d("UserScreen", "Centros obtenidos: $listaCentros")
//            }
//        }
//
//        override fun onFailure(call: Call<List<Center>>, t: Throwable) {
//            Log.e("UserScreen", "Error al obtener los centros", t)
//        }
//    })
//}


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
        CenterListItem(
            center = Center(
                1,
                "Centro de Prueba",
                "www",
                "Type",
                "Esta es la descripscion"
            )
        ) {
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CenterCardPreview() {
    GestionIFEMATheme {
        CenterCard(
            center = Center(
                1,
                "Centro de Prueba",
                "www",
                "Type",
                "Esta es la descripscion"
            )
        )
    }
}
