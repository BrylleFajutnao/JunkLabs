package com.example.junklabs.features

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.junklabs.auth.AuthViewModel
import com.example.junklabs.auth.ErrorDialog
import com.example.junklabs.auth.LoadingScreen
import com.example.junklabs.auth.LoginScreen
import com.example.junklabs.auth.RegisterScreen
import com.example.junklabs.auth.AuthState

@Composable
fun AuthNavigation() {
    val navController = rememberNavController()
    val authViewModel: AuthViewModel = viewModel()
    val authState by authViewModel.authState.collectAsState()
    var showErrorDialog by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var fullName by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }

    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            LoginScreen(
                email = email,
                onEmailChange = { email = it },
                password = password,
                onPasswordChange = { password = it },
                onLogin = {
                    authViewModel.login(email, password)
                },
                onNavigateToRegister = {
                    navController.navigate("register")
                }
            )
        }
        composable("register") {
            RegisterScreen(
                fullName = fullName,
                onFullNameChange = { fullName = it },
                username = username,
                onUsernameChange = { username = it },
                email = email,
                onEmailChange = { email = it },
                password = password,
                onPasswordChange = { password = it },
                onRegister = {
                    authViewModel.register(fullName, username, email, password)
                },
                onNavigateToLogin = {
                    navController.navigate("login")
                }
            )
        }
        composable("home") {
            HomeScreen()
        }
    }

    when (val state = authState) {
        is AuthState.LoggedIn -> {
            navController.navigate("home") {
                popUpTo("login") { inclusive = true }
            }
        }
        is AuthState.Error -> {
            errorMessage = state.message
            showErrorDialog = true
        }
        is AuthState.Loading -> {
            LoadingScreen()
        }
        else -> {}
    }

    if (showErrorDialog) {
        ErrorDialog(message = errorMessage) {
            showErrorDialog = false
            authViewModel.resetAuthState()
        }
    }
}