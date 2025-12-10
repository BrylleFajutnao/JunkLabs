package com.example.junklabs.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.junklabs.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.auth.auth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class AuthViewModel : ViewModel() {

    private val auth: FirebaseAuth = Firebase.auth
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    private val _authState = MutableStateFlow<AuthState>(AuthState.Loading) // Start in a loading state
    val authState: StateFlow<AuthState> = _authState

    init {
        // Listen for authentication state changes
        viewModelScope.launch {
            auth.addAuthStateListener { firebaseAuth ->
                val user = firebaseAuth.currentUser
                if (user != null) {
                    _authState.value = AuthState.LoggedIn
                } else {
                    _authState.value = AuthState.LoggedOut
                }
            }
        }
    }

    fun login(email: String, pass: String) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            try {
                auth.signInWithEmailAndPassword(email, pass).await()
                // The auth state listener will automatically handle the successful login
            } catch (e: Exception) {
                _authState.value = AuthState.Error(e.message ?: "An unknown error occurred")
            }
        }
    }

    fun register(fullName: String, username: String, email: String, pass: String) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            try {
                val authResult = auth.createUserWithEmailAndPassword(email, pass).await()
                val firebaseUser = authResult.user
                if (firebaseUser != null) {
                    val newUser = User(fullName, username, email)
                    firestore.collection("users").document(firebaseUser.uid).set(newUser).await()
                    // The auth state listener will automatically handle the successful registration
                }
            } catch (e: Exception) {
                _authState.value = AuthState.Error(e.message ?: "An unknown error occurred")
            }
        }
    }

    fun signOut() {
        auth.signOut()
    }

    fun resetErrorState() {
        _authState.value = AuthState.LoggedOut // Reset to a neutral state
    }
}

sealed class AuthState {
    object Loading : AuthState()
    object LoggedIn : AuthState()
    object LoggedOut : AuthState()
    data class Error(val message: String) : AuthState()
}
