package com.eep.android.gestionifema.ui
//
//import androidx.compose.foundation.Image
//import androidx.compose.foundation.layout.*
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.ArrowBack
//import androidx.compose.material3.*
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import androidx.navigation.NavHostController
//import com.eep.android.gestionifema.R
//
//import com.eep.android.gestionifema.ui.theme.GestionIFEMATheme
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun centerDetail(navController: NavHostController, centerId: Int) {
//    val center = findCenterById(centerId)
//    Scaffold(
//        topBar = {
//            TopAppBar(
//                title = { Text("Detalle del Centro") },
//                navigationIcon = {
//                    IconButton(onClick = { navController.navigateUp() }) {
//                        Icon(Icons.Filled.ArrowBack, contentDescription = "Volver")
//                    }
//                }
//            )
//        }
//    ) { padding ->
//        Column(
//            modifier = Modifier
//                .padding(padding)
//                .padding(16.dp),
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
//            Image(
//                painter = painterResource(id = R.drawable.doce),
//                contentDescription = "Imagen del Centro",
//                modifier = Modifier
//                    .height(180.dp)
//                    .fillMaxWidth()
//            )
//            Text(
//                text = ListaCentros.listaCentros[0].nombreCentro,
//                style = MaterialTheme.typography.headlineMedium,
//                modifier = Modifier.padding(top = 16.dp)
//            )
//            Text(
//                text = ListaCentros.listaCentros[0].descripcion,
//                style = MaterialTheme.typography.bodyMedium,
//                modifier = Modifier.padding(top = 8.dp)
//            )
//            Button(
//                onClick = { navController.navigateUp() },
//                modifier = Modifier.padding(top = 24.dp)
//            ) {
//                Text("Salir")
//            }
//        }
//    }
//}
//
//fun findCenterById(centerId: Int): Any {
//    return ListaCentros.listaCentros[0]
//}
//
//@Preview(showBackground = true)
//@Composable
//fun PreviewCenterDetailScreen() {
//    GestionIFEMATheme {
////        CenterDetailScreen(
////            navController = rememberNavController(),
////        )
//    }
//}
