package com.eep.android.gestionifema.ui.screen

import android.net.Uri
import android.util.Log
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.eep.android.gestionifema.api.ApiClientUsers
import com.eep.android.gestionifema.model.Center
import com.eep.android.gestionifema.model.User
import com.eep.android.gestionifema.ui.theme.GestionIFEMATheme
import com.eep.android.gestionifema.viewmodel.UserViewModel

var userUpda = User(0, "", 0, "", "", "", "")

@Composable
fun UserScreen(navController: NavHostController, userId: Int) {
    val viewModel: UserViewModel = viewModel()
    val user by viewModel.user.collectAsState()
    val centers by viewModel.centers.collectAsState()
    val context = LocalContext.current
    val selectedCenters = remember { mutableStateListOf<Center>() }

    var showSelectedCenters by remember { mutableStateOf(false) }
    var nombre by remember { mutableStateOf("") }
    var edad by remember { mutableStateOf("") }
    var sortAscending by remember { mutableStateOf(true) }  // Verdadero para A-Z, falso para Z-A



    LaunchedEffect(key1 = userId) {
        viewModel.getUserById(userId)
        viewModel.getCenters()
        obtenerUsuario(userId)
    }
    Column(modifier = Modifier.padding(16.dp)) {
        // Formulario para introducir nombre y edad
        Text(text = "Editar usuario", style = MaterialTheme.typography.titleLarge)
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
        TextButton(
            onClick = { showSelectedCenters = !showSelectedCenters }
        ) {
            Text("Centro de visita: ${if (showSelectedCenters) "Ocultar" else "Mostrar"} centros seleccionados")
        }

        // Mostrar los centros seleccionados si showSelectedCenters es verdadero
        if (showSelectedCenters) {
            selectedCenters.forEach { center ->
                CenterCard(center, navController)
            }
        }
        Row {
            Button(
                onClick = {
                    val updatedUser = userUpda.copy(
                        nombre = nombre,
                        edad = edad.toIntOrNull() ?: 0,
                        centroVisita = selectedCenters.joinToString { it.name },
                        email = user?.email ?: "",  // Mantén el email existente
                        // Mantén la contraseña existente
                        rol = user?.rol ?: ""  // Mantén el rol existente
                    )
                    viewModel.updateUser(userId, updatedUser, onSuccess = {
                        Toast.makeText(
                            context,
                            "Usuario actualizado correctamente",
                            Toast.LENGTH_SHORT
                        ).show()
                    }, onError = { error ->
                        Toast.makeText(context, error, Toast.LENGTH_LONG).show()
                    })
                    selectedCenters.forEach { center ->
                        viewModel.addUserCenter(userId, center.id, onSuccess = {
                            Toast.makeText(
                                context,
                                "Centro añadido correctamente",
                                Toast.LENGTH_SHORT
                            ).show()
                        }, onError = { error ->
                            Toast.makeText(context, error, Toast.LENGTH_LONG).show()
                        })
                    }
                },

                modifier = Modifier.weight(1f)
            ) {
                Text("Guardar")
            }
            Spacer(modifier = Modifier.width(8.dp))
            Button(
                onClick = {
                    navController.navigate("login")
                },
                modifier = Modifier.weight(1f)
            ) {
                Text("Cerrar sesión")
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically

        ) {
            Text(
                "Centros:", style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.align(Alignment.CenterVertically)
            )
            Button(
                onClick = { sortAscending = !sortAscending },
                modifier = Modifier
                    .align(Alignment.CenterVertically)

            ) {
                Text(if (sortAscending) "Priorizar UNI" else "Priorizar FP")
            }
        }
        val sortedCenters = if (sortAscending) {
            centers.sortedBy { it.type }
        } else {
            centers.sortedByDescending { it.type }
        }

        LazyColumn {

            items(sortedCenters) { center ->
                CenterListItem(
                    center = center,
                    onClick = {
                        if (selectedCenters.contains(center)) {
                            selectedCenters.remove(center)
                        } else {
                            selectedCenters.add(center)
                        }

                    },

                    )
            }
        }

    }
}


@Composable
fun CenterCard(center: Center, navController: NavHostController) {
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
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(center.name, style = MaterialTheme.typography.titleMedium)
                Text(center.web, style = MaterialTheme.typography.bodySmall)
            }
            IconButton(onClick = {
                val encodedUrl = Uri.encode(center.web ?: "")
                navController.navigate("webview/$encodedUrl")
            }) {
                Icon(Icons.Filled.Public, contentDescription = "Abrir página web")
            }
        }
    }
}


suspend fun obtenerUsuario(userId: Int) {
    var response = ApiClientUsers.retrofitService.getUserById(userId)
    if (response.isSuccessful) {
        userUpda = response.body()!!
        Log.d("UserScreen", "Usuario obtenido: $userUpda")
    } else {
        Log.e("UserScreen", "Error al obtener el usuario: ${response.errorBody()?.string()}")
    }

}


@Composable
fun ShowDetailsCenter(center: Center, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Detalles del Centro") },
        text = {
            Column {
                Text("Nombre: ${center.name}")
                Text("Dirección: ${center.address}")
                Text("Telefono: ${center.phone}")
                Text("Descripcion: ${center.descr}")

            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("Cerrar")
            }
        }
    )
}

@Composable
fun WebViewSample(
    url: String,
    webViewClient: WebViewClient = WebViewClient()
) {
    AndroidView(
        factory = { context ->
            WebView(context).apply {
                this.webViewClient = webViewClient
            }
        },
        update = { webView ->
            webView.loadUrl(url)
        }
    )
}

class CustomWebViewClient : WebViewClient() {
    override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
        if (url != null && url.startsWith("https://example.com")) {
            view?.loadUrl(url)
            return true
        }
        return false
    }
}

@Composable
fun CenterListItem(
    center: Center,
    onClick: () -> Unit,

    ) {
    var showDetailsDialog by remember { mutableStateOf(false) }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { showDetailsDialog = true },

        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.School,
                contentDescription = "Centro",
                modifier = Modifier.size(40.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    center.name,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.width(250.dp),
                )

                Text(center.web, style = MaterialTheme.typography.bodySmall)

            }
            Spacer(modifier = Modifier.weight(1f))
            IconButton(
                onClick = onClick
            ) {
                Icon(Icons.Default.Add, contentDescription = "Añadir")
            }
        }
        if (showDetailsDialog) {
            AlertDialog(
                onDismissRequest = { showDetailsDialog = false },
                title = { Text("Detalles del Centro") },
                text = {
                    Column {
                        Text("Nombre: ${center.name}")
                        Text("Dirección: ${center.address}")
                        Text("Web: ${center.web}")
                    }
                },
                confirmButton = {
                    Button(onClick = { showDetailsDialog = false }) {
                        Text("Cerrar")
                    }
                }
            )
        }
    }
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
        CenterListItem(
            center = Center(
                1,
                "Centro de Prueba",
                "www",
                "Type",
                "Esta es la descripscion",
                "Telefono",
                "Direccion",
                ),

            ) {
        }
    }
}

@Preview(showBackground = true)
@Composable
fun descripcion() {
    GestionIFEMATheme {
        ShowDetailsCenter(
            center = Center(
                1,
                "Centro de Prueba pero si es mas largo que pasa",
                "www",
                "Type",
                "Esta es la descripscion",
                "Direccion",
                "Telefono",

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
                "Esta es la descripscion",
                "Direccion",
                "Telefono",
            ),
            navController = rememberNavController()
        )
    }
}
