package com.eep.android.gestionifema.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.eep.android.gestionifema.R
import com.eep.android.gestionifema.api.ApiClient
import com.eep.android.gestionifema.ui.theme.GestionIFEMATheme
import com.eep.android.gestionifema.model.LoginRequest
import com.eep.android.gestionifema.model.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun LoginScreen() {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var selectedRole by remember { mutableStateOf("User") }

    Column(
        modifier = Modifier
            .statusBarsPadding()
            .padding(horizontal = 40.dp)
            .verticalScroll(rememberScrollState())
            .safeDrawingPadding(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Login / Register", modifier = Modifier.padding(bottom = 16.dp, top = 40.dp))
        EditTextField(label = R.string.email_message, value = email, onValueChanged = { email = it })
        EditTextField(label = R.string.password_message, value = password, onValueChanged = { password = it })
        RoleSelection(selectedRole, onRoleChanged = { selectedRole = it })

        Button(onClick = { performLogin(email, password, selectedRole) },
            modifier = Modifier
                .padding(top = 32.dp)
                .fillMaxWidth()) {
            Text("Enter")
        }

        Spacer(modifier = Modifier.height(150.dp))
    }
}

@Composable
fun EditTextField(@StringRes label: Int, value: String, onValueChanged: (String) -> Unit) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChanged,
        singleLine = true,
        label = { Text(stringResource(label)) },
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = if (label == R.string.password_message) KeyboardType.Password else KeyboardType.Text,
            imeAction = if (label == R.string.password_message) ImeAction.Done else ImeAction.Next
        )
    )
}

@Composable
fun RoleSelection(selectedRole: String, onRoleChanged: (String) -> Unit) {
    val roles = listOf("User", "Owner")
    Column {
        roles.forEach { role ->
            Row(
                modifier = Modifier
                    .padding(16.dp)
                    .wrapContentWidth()
                    .align(Alignment.Start),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = role == selectedRole,
                    onClick = { onRoleChanged(role) }
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(text = role)
            }
        }
    }
}

private fun performLogin(email: String, password: String, role: String) {
    val loginRequest = LoginRequest(email, password, role)
    ApiClient.instance.loginUser(loginRequest).enqueue(object : Callback<User>{
        override fun onResponse(call: Call<User>, response: Response<User>) {
            if (response.isSuccessful) {
                // Handle successful login
            } else {
                // Handle login failure
            }
        }

        override fun onFailure(call: Call<User>, t: Throwable) {
            // Handle network error or exception
        }
    })
}

private fun Any.enqueue(callback: Callback<User>) {


}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    GestionIFEMATheme {
        LoginScreen()

    }
}


