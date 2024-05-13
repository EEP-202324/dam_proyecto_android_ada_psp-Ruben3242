package com.eep.android.gestionifema.ui

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument

@Composable
fun WebViewScreen(url: String) {
    WebViewSample(url = url, webViewClient = CustomWebViewClient())
}
@Composable
fun AppNavHost(navController: NavHostController) {
    NavHost(navController, startDestination = "ownerScreen") {
        composable("ownerScreen") { OwnerScreen(navController, viewModel()) }
        composable("webview/{url}", arguments = listOf(navArgument("url") { type = NavType.StringType })) { backStackEntry ->
            WebViewScreen(backStackEntry.arguments?.getString("url") ?: "")
        }
    }
}