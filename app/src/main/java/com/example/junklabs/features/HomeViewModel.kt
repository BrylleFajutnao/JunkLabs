package com.example.junklabs.features

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.junklabs.model.TrashCategory
import com.example.junklabs.model.TrashItem
import com.example.junklabs.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class HomeViewModel : ViewModel() {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    private val _user = MutableStateFlow<User?>(null)
    val user: StateFlow<User?> = _user

    private val _trashItems = MutableStateFlow<List<TrashItem>>(emptyList())
    val trashItems: StateFlow<List<TrashItem>> = _trashItems

    init {
        fetchUserData()
    }

    private fun fetchUserData() {
        viewModelScope.launch {
            val firebaseUser = auth.currentUser ?: return@launch
            val userRef = firestore.collection("users").document(firebaseUser.uid)

            try {
                val userSnapshot = userRef.get().await()
                _user.value = userSnapshot.toObject(User::class.java)

                val trashSnapshot = userRef.collection("trashItems").get().await()
                val items = trashSnapshot.documents.mapNotNull { it.toObject(TrashItem::class.java) }
                _trashItems.value = items
            } catch (e: Exception) {
                // Handle error
            }
        }
    }

    fun addTrashItem(name: String, category: TrashCategory) {
        viewModelScope.launch {
            val firebaseUser = auth.currentUser ?: return@launch
            val userRef = firestore.collection("users").document(firebaseUser.uid)

            val newItem = TrashItem(name, category.name)
            try {
                userRef.collection("trashItems").add(newItem).await()
                _trashItems.value = _trashItems.value + newItem
            } catch (e: Exception) {
                // Handle error
            }
        }
    }
}