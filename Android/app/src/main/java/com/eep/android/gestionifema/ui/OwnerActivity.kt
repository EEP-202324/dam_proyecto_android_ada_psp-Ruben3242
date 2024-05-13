package com.eep.android.gestionifema.ui

import android.net.Uri
import android.util.Log
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import com.eep.android.gestionifema.model.Center
import com.eep.android.gestionifema.viewmodel.OwnerViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import androidx.compose.ui.platform.LocalContext


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OwnerScreen(navController: NavController, viewModel: OwnerViewModel = viewModel()) {
    val items = viewModel.centers.collectAsState().value

    // Cargar los centros al iniciar la pantalla
    LaunchedEffect(key1 = true) {
        viewModel.getCenters()
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate("center")}) {
                Icon(Icons.Filled.Add, contentDescription = "Agregar centro")
            }
        },
        topBar = {
            TopAppBar(
                title = { Text("Centros") },
                actions = {
                    IconButton(onClick = { viewModel.getCenters() }) {
                        Icon(Icons.Filled.Refresh, contentDescription = "Actualizar")
                    }
                }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier.padding(paddingValues),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(items) { center ->
                CenterCard(center, viewModel, navController)
            }
        }
    }
}

@Composable
fun CenterCard(center: Center, viewModel: OwnerViewModel, navController: NavController) {
    var showDialogEdit by remember { mutableStateOf(false) }
    var showDialogDelete by remember { mutableStateOf(false) }
    var showDetailsDialog by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            Text(text = center.name ?: "Nombre no disponible", style = MaterialTheme.typography.titleMedium)
            Text(text = "Dirección: ${center.address ?: "Dirección no disponible"}", style = MaterialTheme.typography.bodySmall)
//            Text(text = "Página Web: ${center.web ?: "Página web no disponible"}", style = MaterialTheme.typography.bodySmall)
//            Text(text = "Teléfono: ${center.phone ?: "Teléfono no disponible"}", style = MaterialTheme.typography.bodySmall)
//            Text(text = "Descripción: ${center.descr ?: "Descripción no disponible"}", style = MaterialTheme.typography.bodySmall)
            Row(horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.fillMaxWidth()) {
                IconButton(onClick = { showDetailsDialog = true }) {
                    Icon(Icons.Filled.Info, contentDescription = "Ver detalles")
                }
                IconButton(onClick = { showDialogEdit = true }) {
                    Icon(Icons.Filled.Edit, contentDescription = "Editar")
                }
                IconButton(onClick = { showDialogDelete = true }) {
                    Icon(Icons.Filled.Delete, contentDescription = "Eliminar")
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

    if (showDialogEdit) {
        ShowEditDialog(center, viewModel) { showDialogEdit = false }
    }
    if (showDialogDelete) {
        ShowDeleteDialog(center, viewModel, navController) { showDialogDelete = false }
    }
    if (showDetailsDialog) {
        ShowDetailsDialog(center) { showDetailsDialog = false }
    }
}


@Composable
fun ShowDetailsDialog(center: Center, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Detalles del Centro") },
        text = {
            Column {
                Text("Página Web: ${center.web}")
                Text("Teléfono: ${center.phone}")
                Text("Descripción: ${center.descr}")
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
fun ShowEditDialog(center: Center, viewModel: OwnerViewModel, onDismiss: () -> Unit) {
    val context = LocalContext.current
    var nombre by remember { mutableStateOf(center.name) }
    var direccion by remember { mutableStateOf(center.address) }
    var paginaWeb by remember { mutableStateOf(center.web) }
    var telefono by remember { mutableStateOf(center.phone) }
    var descripcion by remember { mutableStateOf(center.descr) }

    AlertDialog(
        onDismissRequest = onDismiss,
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
                val updatedCenter = Center(center.id, nombre, paginaWeb, center.type, direccion, telefono, descripcion)
                viewModel.updateCenter(center.id, updatedCenter, onSuccess = {
                    Toast.makeText(context, "Centro actualizado", Toast.LENGTH_SHORT).show()
                    onDismiss()
                }, onError = { errorMsg ->
                    Toast.makeText(context, "Error al actualizar el centro: $errorMsg", Toast.LENGTH_SHORT).show()
                    Log.e("OwnerScreen", "Error al actualizar el centro: $errorMsg")
                })
            }) {
                Text("Guardar")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    )
}

@Composable
fun ShowDeleteDialog(center: Center, viewModel: OwnerViewModel, navController: NavController, onDismiss: () -> Unit) {
    val context = LocalContext.current  // Obtiene el contexto actual de Compose

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Confirmar eliminación") },
        text = { Text("¿Estás seguro de que quieres eliminar este centro?") },
        confirmButton = {
            TextButton(onClick = {
                viewModel.deleteCenter(center.id, onSuccess = {
                    Toast.makeText(context, "Centro eliminado", Toast.LENGTH_SHORT).show()  // Usa el contexto obtenido
                    navController.popBackStack()
                }, onError = {
                    Toast.makeText(context, "Error al eliminar el centro", Toast.LENGTH_SHORT).show()  // Usa el contexto obtenido
                })
                onDismiss()
            }) {
                Text("Eliminar")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    )
}
@Preview
@Composable
fun PreviewOwnerScreen() {
    val navController = rememberNavController()
    val viewModel = viewModel<OwnerViewModel>()
    OwnerScreen(navController, viewModel)
}

@Preview
@Composable
fun PreviewCenterCard() {
    val center = Center(1, "Centro de Prueba", "https://example.com", "type", "Calle de Prueba", "123456789", "Descripción de prueba")
    val viewModel = viewModel<OwnerViewModel>()
    val navController = rememberNavController()
    CenterCard(center, viewModel, navController)
}

@Preview
@Composable
fun PreviewShowEditDialog() {
    val center = Center(1, "Centro de Prueba", "https://example.com", "type", "Calle de Prueba", "123456789", "Descripción de prueba")
    val viewModel = viewModel<OwnerViewModel>()
    ShowEditDialog(center, viewModel) {}
}

@Preview
@Composable
fun PreviewShowDeleteDialog() {
    val center = Center(1, "Centro de Prueba", "https://example.com", "type", "Calle de Prueba", "123456789", "Descripción de prueba")
    val viewModel = viewModel<OwnerViewModel>()
    val navController = rememberNavController()
    ShowDeleteDialog(center, viewModel, navController) {}
}

@Preview
@Composable
fun PreviewShowDetailsDialog() {
    val center = Center(1, "Centro de Prueba", "https://example.com", "type", "Calle de Prueba", "123456789", "Descripción de prueba")
    ShowDetailsDialog(center) {}
}

//@Preview
//@Composable
//fun PreviewAddCenterScreen() {
//    val navController = rememberNavController()
//    val viewModel = viewModel<OwnerViewModel>()
//    AddCenterScreen(navController, viewModel)
//}