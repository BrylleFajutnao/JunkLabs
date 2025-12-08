package com.example.junklabs.auth

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
import com.example.junklabs.features.HomeScreen

@Composable
fun AuthNavigation() {
    val navController = rememberNavController()
    val authViewModel: AuthViewModel = viewModel()
    val authState by authViewModel.authState.collectAsState()
    var showErrorDialog by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            var email by remember { mutableStateOf("") }
            var password by remember { mutableStateOf("") }

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
            var fullName by remember { mutableStateOf("") }
            var username by remember { mutableStateOf("") }
            var email by remember { mutableStateOf("") }
            var password by remember { mutableStateOf("") }

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
        }
    }
}