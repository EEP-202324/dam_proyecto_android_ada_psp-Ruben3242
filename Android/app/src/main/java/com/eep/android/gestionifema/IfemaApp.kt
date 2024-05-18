package com.eep.android.gestionifema

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.eep.android.gestionifema.ui.screen.AddCenterScreen


import com.eep.android.gestionifema.ui.screen.LoginScreen
import com.eep.android.gestionifema.ui.screen.OwnerScreen
import com.eep.android.gestionifema.ui.screen.Screen
import com.eep.android.gestionifema.ui.screen.UserScreen
import com.eep.android.gestionifema.ui.screen.WebViewScreen
import com.eep.android.gestionifema.ui.theme.GestionIFEMATheme


@Composable
fun IfemaApp() {
    val navController = rememberNavController()
    GestionIFEMATheme {
        Scaffold { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = Screen.Login,
                modifier = Modifier.padding(innerPadding)
            ) {
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
                    OwnerScreen(navController, viewModel())
                }
                composable(
                    "webview/{url}",
                    arguments = listOf(navArgument("url") { type = NavType.StringType })
                ) { backStackEntry ->
                    WebViewScreen(url = backStackEntry.arguments?.getString("url") ?: "")
                }
                composable(Screen.Center) {
                    AddCenterScreen(navController, viewModel())
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

