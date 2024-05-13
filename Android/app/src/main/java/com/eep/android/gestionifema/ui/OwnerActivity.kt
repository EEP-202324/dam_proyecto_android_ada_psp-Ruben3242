package com.eep.android.gestionifema.ui

import android.net.Uri
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
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

@Composable
fun OwnerScreen(navController: NavController, viewModel: OwnerViewModel = viewModel()) {
    val items = viewModel.centers.collectAsState().value

    // Cargar los centros al iniciar la pantalla
    LaunchedEffect(key1 = true) {
        viewModel.getCenters()
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { /* Lógica para añadir un nuevo centro */ }) {
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
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = center.nombreCentro, style = MaterialTheme.typography.titleMedium)
            Text(text = "Dirección: ${center.address}", style = MaterialTheme.typography.bodySmall)
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
                    val encodedUrl = Uri.encode(center.paginaWeb)
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
                Text("Página Web: ${center.paginaWeb}")
                Text("Teléfono: ${center.telefono}")
                Text("Descripción: ${center.descripcion}")
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
    var nombre by remember { mutableStateOf(center.nombreCentro) }
    var direccion by remember { mutableStateOf(center.address) }
    var paginaWeb by remember { mutableStateOf(center.paginaWeb) }
    var telefono by remember { mutableStateOf(center.telefono) }
    var descripcion by remember { mutableStateOf(center.descripcion) }

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
                viewModel.updateCenter(center.id, Center(center.id, nombre, paginaWeb, center.type, direccion, telefono, descripcion), onSuccess = {}, onError = {})
                onDismiss()
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
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Confirmar eliminación") },
        text = { Text("¿Estás seguro de que quieres eliminar este centro?") },
        confirmButton = {
            TextButton(onClick = {
                viewModel.deleteCenter(center.id, onSuccess = {
                    Toast.makeText(null, "Centro eliminado", Toast.LENGTH_SHORT).show()
                    navController.popBackStack()
                }, onError = {
                    Toast.makeText(null, "Error al eliminar el centro", Toast.LENGTH_SHORT).show()
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
