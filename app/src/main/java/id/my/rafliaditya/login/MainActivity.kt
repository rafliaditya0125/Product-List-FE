package id.my.rafliaditya.login

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import id.my.rafliaditya.login.ui.HomeScreen
import id.my.rafliaditya.login.ui.LoginScreen
import id.my.rafliaditya.login.ui.ProductScreen
import id.my.rafliaditya.login.ui.RegisterScreen
import id.my.rafliaditya.login.ui.theme.AppTheme
import id.my.rafliaditya.login.viewmodel.AuthViewModel
import id.my.rafliaditya.login.viewmodel.ProductViewModel

class MainActivity : ComponentActivity() {
    private val authViewModel: AuthViewModel by viewModels()
    private val productViewModel: ProductViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                AuthNavigation(authViewModel, productViewModel)
            }
        }
    }
}

@Composable
fun AuthNavigation(authViewModel: AuthViewModel, productViewModel: ProductViewModel) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "login") {
        composable("login") { LoginScreen(navController, authViewModel) }
        composable("register") { RegisterScreen(navController, authViewModel) }
        composable("product_list") { ProductScreen(productViewModel, navController) }
        composable("home") { HomeScreen(navController) }
    }
}