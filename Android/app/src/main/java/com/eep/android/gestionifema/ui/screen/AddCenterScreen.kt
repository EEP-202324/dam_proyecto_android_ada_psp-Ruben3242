package com.eep.android.gestionifema.ui.screen

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.eep.android.gestionifema.model.Center
import com.eep.android.gestionifema.viewmodel.OwnerViewModel


@Composable
fun AddCenterScreen(navController: NavController, viewModel: OwnerViewModel) {
    var nombre by remember { mutableStateOf("") }
    var direccion by remember { mutableStateOf("") }
    var paginaWeb by remember { mutableStateOf("") }
    var telefono by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    val tipoOptions = listOf("Universidad", "Formación Profesional")
    var selectedTipo by remember { mutableStateOf(tipoOptions.first()) } // Universidad por defecto
    val context = LocalContext.current

    Column(modifier = Modifier
        .padding(16.dp)
        .verticalScroll(rememberScrollState())) {
        Text("Agregar Nuevo Centro", style = MaterialTheme.typography.titleMedium)
        OutlinedTextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text("Nombre del Centro") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = direccion,
            onValueChange = { direccion = it },
            label = { Text("Dirección") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = paginaWeb,
            onValueChange = { paginaWeb = it },
            label = { Text("Página Web") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = telefono,
            onValueChange = { telefono = it },
            label = { Text("Teléfono") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = descripcion,
            onValueChange = { descripcion = it },
            label = { Text("Descripción") },
            modifier = Modifier.fillMaxWidth()
        )

        // Radio Buttons para seleccionar el tipo
        Text("Tipo de centro:", style = MaterialTheme.typography.titleSmall)
        tipoOptions.forEach { text ->
            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                    selected = selectedTipo == text,
                    onClick = { selectedTipo = text }
                )
                Text(text = text, style = MaterialTheme.typography.titleSmall)
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End

        ) {
            Button(
                onClick = {
                    navController.popBackStack()
                },

            ) {
                Text("Cancelar")
            }
            Button(
                onClick = {
                    viewModel.createCenter(
                        Center(
                            0,
                            nombre,
                            paginaWeb,
                            selectedTipo,
                            direccion,
                            telefono,
                            descripcion
                        ), {
                            Toast.makeText(
                                context,
                                "Centro agregado correctamente",
                                Toast.LENGTH_SHORT
                            ).show()
                            navController.navigate(Screen.Owner)
                        }, {
                            Toast.makeText(context, it, Toast.LENGTH_LONG).show()
                        })
                },
                modifier = Modifier.padding(start = 8.dp)
            ) {
                Text("Agregar Centro")
            }
        }
    }
}

@Preview
@Composable
fun PreviewAddCenterScreen() {
    val navController = rememberNavController()
    val viewModel = viewModel<OwnerViewModel>()
    AddCenterScreen(navController, viewModel)
}