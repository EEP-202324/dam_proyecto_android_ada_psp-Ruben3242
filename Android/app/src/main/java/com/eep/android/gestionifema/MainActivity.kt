package com.eep.android.gestionifema

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.eep.android.gestionifema.ui.theme.GestionIFEMATheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GestionIFEMATheme {
                IfemaApp()

            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    GestionIFEMATheme {
        IfemaApp()
    }
}
