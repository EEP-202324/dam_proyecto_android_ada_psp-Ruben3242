package com.eep.android.gestionifema

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.eep.android.gestionifema.ui.CenterDetailScreen

import com.eep.android.gestionifema.ui.LoginScreen
import com.eep.android.gestionifema.ui.OwnerScreen
import com.eep.android.gestionifema.ui.Screen
import com.eep.android.gestionifema.ui.UserScreen
import com.eep.android.gestionifema.ui.theme.GestionIFEMATheme


@Composable
fun IfemaApp() {
    val navController = rememberNavController()
    GestionIFEMATheme {
        Scaffold { innerPadding ->
            NavHost(navController = navController, startDestination = Screen.Login, modifier = Modifier.padding(innerPadding)) {
                composable(Screen.Login) {
                    LoginScreen(navController)
                }
                composable(
                    "userScreen/{userId}",
                    arguments = listOf(navArgument("userId") { type = NavType.IntType })
                ) { backStackEntry ->
                    UserScreen(navController, backStackEntry.arguments?.getInt("userId") ?: 0)
                }
                composable(Screen.Owner) {
                    OwnerScreen(navController)
                }
                composable(Screen.CenterDetail) { backStackEntry ->
                    // Supongamos que pasamos los datos necesarios como argumentos o a través de ViewModel
                    CenterDetailScreen(
                        navController = navController,
                        centerName = "Nombre del Centro",  // Estos deberían ser argumentos pasados
                        centerDescription = "Descripción del Centro",
                        imageUrl = "URL de la imagen"
                    )
                }
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    GestionIFEMATheme {
        IfemaApp()
    }
}

