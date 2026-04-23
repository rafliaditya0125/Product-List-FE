package id.my.rafliaditya.login.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import id.my.rafliaditya.login.viewmodel.AuthState
import id.my.rafliaditya.login.viewmodel.AuthViewModel

@Composable
fun LoginScreen(navController: NavController, viewModel: AuthViewModel) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val state by viewModel.loginState.collectAsStateWithLifecycle()

    LaunchedEffect(state) {
        if (state is AuthState.Success) {
            navController.navigate("product_list") { popUpTo("login") { inclusive = true } }
            viewModel.resetStates()
        }
    }

    AuthLayout {
        TextField(value = username, onValueChange = { username = it }, label = { Text("Username") }, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(8.dp))
        TextField(value = password, onValueChange = { password = it }, label = { Text("Password") }, visualTransformation = PasswordVisualTransformation(), modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { viewModel.login(username, password) }, modifier = Modifier.fillMaxWidth(), enabled = state !is AuthState.Loading) {
            Text(if (state is AuthState.Loading) "Logging in..." else "Login")
        }
        if (state is AuthState.Error) Text(text = (state as AuthState.Error).message, color = MaterialTheme.colorScheme.error)
        TextButton(onClick = { navController.navigate("register") }) { Text("Don't have an account? Register") }
    }
}

@Composable
fun RegisterScreen(navController: NavController, viewModel: AuthViewModel) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    val state by viewModel.registerState.collectAsStateWithLifecycle()

    LaunchedEffect(state) {
        if (state is AuthState.Success) {
            navController.navigate("login") { popUpTo("register") { inclusive = true } }
            viewModel.resetStates()
        }
    }

    AuthLayout {
        TextField(value = username, onValueChange = { username = it }, label = { Text("Username") }, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(8.dp))
        TextField(value = email, onValueChange = { email = it }, label = { Text("Email") }, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(8.dp))
        TextField(value = password, onValueChange = { password = it }, label = { Text("Password") }, visualTransformation = PasswordVisualTransformation(), modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { viewModel.register(username, password, email) }, modifier = Modifier.fillMaxWidth(), enabled = state !is AuthState.Loading) {
            Text(if (state is AuthState.Loading) "Registering..." else "Register")
        }
        if (state is AuthState.Error) Text(text = (state as AuthState.Error).message, color = MaterialTheme.colorScheme.error)
        TextButton(onClick = { navController.navigate("login") }) { Text("Already have an account? Login") }
    }
}

@Composable
fun HomeScreen(navController: NavController) {
    Column(modifier = Modifier.fillMaxSize().padding(24.dp), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
        Text("✅ Login Successful!", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { navController.navigate("login") { popUpTo("home") { inclusive = true } } }) { Text("Logout") }
    }
}

@Composable
private fun AuthLayout(content: @Composable ColumnScope.() -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        content = content
    )
}