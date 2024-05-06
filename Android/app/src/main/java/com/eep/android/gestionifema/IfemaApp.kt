package com.eep.android.gestionifema

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

import com.eep.android.gestionifema.ui.LoginScreen
import com.eep.android.gestionifema.ui.OwnerScreen
import com.eep.android.gestionifema.ui.Screen
import com.eep.android.gestionifema.ui.UserScreen
import com.eep.android.gestionifema.ui.theme.GestionIFEMATheme


@Composable
fun IfemaApp() {
    val navController = rememberNavController()
    Scaffold { innerPadding ->
        NavHost(navController = navController, startDestination = Screen.Login, modifier = Modifier.padding(innerPadding)) {
            composable(Screen.Login) { LoginScreen(navController) }
            composable(Screen.User) { UserScreen(navController) }
            composable(Screen.Owner) { OwnerScreen(navController) }
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

