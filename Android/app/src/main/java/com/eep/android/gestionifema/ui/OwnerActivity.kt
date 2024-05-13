package com.eep.android.gestionifema.ui

import android.net.Uri
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Public
import androidx.compose.material3.*
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.simulateHotReload
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import com.eep.android.gestionifema.model.Center
import com.eep.android.gestionifema.viewmodel.OwnerViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController

@Composable
fun OwnerScreen(navController: NavController, viewModel: OwnerViewModel = viewModel()) {
    val items = viewModel.centers.collectAsState().value


    // Cargar los centros al iniciar la pantalla
    LaunchedEffect(key1 = true) {
        viewModel.getCenters()
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { /* Aquí lógica para añadir un nuevo centro */ }) {
                Icon(Icons.Filled.Add, contentDescription = "Agregar centro")
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier.padding(paddingValues),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(items) { center ->
                CenterCard(center, viewModel, navController)
//                showDetails(center)
            }
        }
    }
}
//
//@Composable
//fun CenterCard(center: Center, viewModel: OwnerViewModel, navController: NavController) {
//    Card(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(16.dp),
//        elevation = CardDefaults.cardElevation(8.dp)
//    ) {
//        Column(modifier = Modifier.padding(16.dp)) {
//            Text(text = center.nombreCentro, style = MaterialTheme.typography.titleMedium)
//            Text(text = "Dirección: ${center.address}", style = MaterialTheme.typography.bodySmall)
//            Row(
//                horizontalArrangement = Arrangement.SpaceEvenly,
//                modifier = Modifier.fillMaxWidth()
//            ) {
//                IconButton(onClick = {}) {
//                    Icon(Icons.Filled.Info, contentDescription = "Ver detalles")
//                }
//                IconButton(onClick = { }) {
//                    Icon(Icons.Filled.Edit, contentDescription = "Editar")
//                }
//                IconButton(onClick = {
//                    viewModel.deleteCenter(center.id, onSuccess = { Toast.makeText(null, "Centro eliminado", Toast.LENGTH_SHORT).show(); simulateHotReload(navController)
//                    }, onError = {Toast.makeText(null, "Error al eliminar el centro", Toast.LENGTH_SHORT).show()})
//                }) {
//                    Icon(Icons.Filled.Delete, contentDescription = "Eliminar")
//                }
//                IconButton(onClick = {
//                    val encodedUrl = Uri.encode(center.paginaWeb)
//                    navController.navigate("webview/$encodedUrl")
//                }) {
//                    Icon(Icons.Filled.Public, contentDescription = "Abrir página web")
//                }
//            }
//        }
//    }
//}
//
//
//@Composable
//fun showDetails(center: Center) {
//    var detallesVisibles by remember { mutableStateOf(false) }
//
//    Card(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(8.dp)
//            .clickable { detallesVisibles = !detallesVisibles },  // Agrega interactividad al Card
//        shape = RoundedCornerShape(10.dp),
//        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp)
//    ) {
//        Column(modifier = Modifier.padding(16.dp)) {
//            // Nombre del curso en grande y en negrita
//            Text(
//                text = center.nombreCentro,
//                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
//            )
//            // Condición para mostrar los detalles del curso
//            if (detallesVisibles) {
//                Text(text = center.paginaWeb, style = MaterialTheme.typography.bodyMedium)
//                Text(text = "Prueba", style = MaterialTheme.typography.bodyMedium)
//            }
//        }
//    }
//
//}


@Composable
fun CenterCard(center: Center, viewModel: OwnerViewModel, navController: NavController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = center.nombreCentro, style = MaterialTheme.typography.titleMedium)
            Text(text = "Dirección: ${center.address}", style = MaterialTheme.typography.bodySmall)
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                IconButton(onClick = { showDetails(center) }) {
                    Icon(Icons.Filled.Info, contentDescription = "Ver detalles")
                }
                IconButton(onClick = { showEditDialog(center, viewModel) }) {
                    Icon(Icons.Filled.Edit, contentDescription = "Editar")
                }
                IconButton(onClick = {
                    showDeleteDialog(center, viewModel, navController)
                }) {
                    Icon(Icons.Filled.Delete, contentDescription = "Eliminar")
                }
                IconButton(onClick = {
                    val encodedUrl = Uri.encode(center.paginaWeb)
                    navController.navigate("webview/$encodedUrl")
                }) {
                    Icon(Icons.Filled.Public, contentDescription = "Abrir página web")
                }
            }
        }
    }
}

@Composable
fun showDetails(center: Center) {
    var detallesVisibles by remember { mutableStateOf(false) }
    if (detallesVisibles) {
        AlertDialog(
            onDismissRequest = { detallesVisibles = false },
            title = { Text("Detalles del Centro") },
            text = {
                Column {
                    Text("Página Web: ${center.paginaWeb}")
                    Text("Teléfono: ${center.telefono}")
                    Text("Descripción: ${center.descripcion}")
                }
            },
            confirmButton = {
                TextButton(onClick = { detallesVisibles = false }) {
                    Text("Cerrar")
                }
            }
        )
    }
}

@Composable
fun showEditDialog(center: Center, viewModel: OwnerViewModel) {
    var showDialog by remember { mutableStateOf(true) }
    var nombre by remember(center.nombreCentro) { mutableStateOf(center.nombreCentro) }
    var direccion by remember(center.address) { mutableStateOf(center.address) }
    var paginaWeb by remember(center.paginaWeb) { mutableStateOf(center.paginaWeb) }
    var telefono by remember(center.telefono) { mutableStateOf(center.telefono) }
    var descripcion by remember(center.descripcion) { mutableStateOf(center.descripcion) }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Editar Centro") },
            text = {
                Column {
                    TextField(value = nombre, onValueChange = { nombre = it }, label = { Text("Nombre") })
                    TextField(value = direccion, onValueChange = { direccion = it }, label = { Text("Dirección") })
                    TextField(value = paginaWeb, onValueChange = { paginaWeb = it }, label = { Text("Página Web") })
                    TextField(value = telefono, onValueChange = { telefono = it }, label = { Text("Teléfono") })
                    TextField(value = descripcion, onValueChange = { descripcion = it }, label = { Text("Descripción") })
                }
            },
            confirmButton = {
                TextButton(onClick = {
                    viewModel.updateCenter(center.id, Center(center.id, nombre, paginaWeb, center.type, direccion, telefono, descripcion),
                        onSuccess = { /* Success logic here */ },
                        onError = { /* Error handling here */ })
                    showDialog = false
                }) {
                    Text("Guardar")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("Cancelar")
                }
            }
        )
    }
}

@Composable
fun showDeleteDialog(center: Center, viewModel: OwnerViewModel, navController: NavController) {
    var showDialog by remember { mutableStateOf(true) }
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Confirmar eliminación") },
            text = { Text("¿Estás seguro de que quieres eliminar este centro?") },
            confirmButton = {
                TextButton(onClick = {
                    viewModel.deleteCenter(center.id, onSuccess = {
                        Toast.makeText(null, "Centro eliminado", Toast.LENGTH_SHORT).show()
                        showDialog = false
                        navController.popBackStack()
                    }, onError = {
                        Toast.makeText(null, "Error al eliminar el centro", Toast.LENGTH_SHORT).show()
                    })
                }) {
                    Text("Eliminar")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("Cancelar")
                }
            }
        )
    }
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

//class CustomWebViewClient : WebViewClient() {
//    override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
//        if (url != null && url.startsWith("https://example.com")) {
//            view?.loadUrl(url)
//            return true
//        }
//        return false
//    }
//}

@Preview
@Composable
fun PreviewOwnerScreen() {
    CenterCard(
        Center(
            id = 1,
            nombreCentro = "Centro de pruebas",
            address = "Calle de las pruebas, 123",
            paginaWeb = "https://example.com",
            telefono = "123456789",
            descripcion = "Descripción del centro de pruebas",
            type = "Tipo de centro"
        ),
        viewModel = viewModel(),
        navController = rememberNavController()
    )
}

