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
import com.eep.android.gestionifema.ui.UserScreen


class IfemaApp : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            IfemaNavigation()
        }
    }
}

@Composable
fun IfemaNavigation() {
    val navController = rememberNavController()
    Scaffold { innerPadding ->
        NavHost(navController, startDestination = "login", Modifier.padding(innerPadding)) {
            composable("login") { LoginScreen() }
            composable("user") { UserScreen(navController) }
            composable("owner") { OwnerScreen(navController) }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    IfemaNavigation()
}
