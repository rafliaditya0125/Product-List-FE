package id.my.rafliaditya.login.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import id.my.rafliaditya.login.data.User
import id.my.rafliaditya.login.data.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class AuthState {
    object Idle : AuthState()
    object Loading : AuthState()
    object Success : AuthState()
    data class Error(val message: String) : AuthState()
}

class AuthViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = UserRepository(application)

    private val _loginState = MutableStateFlow<AuthState>(AuthState.Idle)
    val loginState: StateFlow<AuthState> = _loginState

    private val _registerState = MutableStateFlow<AuthState>(AuthState.Idle)
    val registerState: StateFlow<AuthState> = _registerState

    fun login(username: String, password: String) {
        if (username.isBlank() || password.isBlank()) {
            _loginState.value = AuthState.Error("Username and password required")
            return
        }
        viewModelScope.launch {
            _loginState.value = AuthState.Loading
            try {
                val success = repository.login(username, password)
                _loginState.value = if (success) AuthState.Success else AuthState.Error("Invalid credentials")
            } catch (e: Exception) {
                _loginState.value = AuthState.Error(e.localizedMessage ?: "Login failed")
            }
        }
    }

    fun register(username: String, password: String, email: String) {
        if (username.isBlank() || password.isBlank() || email.isBlank()) {
            _registerState.value = AuthState.Error("All fields are required")
            return
        }
        viewModelScope.launch {
            _registerState.value = AuthState.Loading
            try {
                val success = repository.registerUser(User(username = username, password = password, email = email))
                _registerState.value = if (success) AuthState.Success else AuthState.Error("Registration failed (username may exist)")
            } catch (e: Exception) {
                _registerState.value = AuthState.Error(e.localizedMessage ?: "Registration failed")
            }
        }
    }

    fun resetStates() {
        _loginState.value = AuthState.Idle
        _registerState.value = AuthState.Idle
    }
}