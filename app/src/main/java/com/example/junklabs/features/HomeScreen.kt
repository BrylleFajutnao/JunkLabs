package com.example.junklabs.features

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.junklabs.components.Logo

@Composable
fun HomeScreen(onSignOut: () -> Unit) {
    val homeViewModel: HomeViewModel = viewModel()
    val user by homeViewModel.user.collectAsState()
    val trashItems by homeViewModel.trashItems.collectAsState()

    // Fetch user data when the composable is first launched
    LaunchedEffect(Unit) {
        homeViewModel.fetchUserData()
    }

    if (user == null) {
        // Display a loading indicator while user data is being fetched
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator()
        }
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Logo()
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "Welcome, ${user!!.fullName}!", fontSize = 20.sp)
            Spacer(modifier = Modifier.height(16.dp))
            TrashInput { name, category ->
                homeViewModel.addTrashItem(name, category)
            }
            Spacer(modifier = Modifier.height(16.dp))
            TrashSummary(trashItems)
            Spacer(modifier = Modifier.weight(1f))
            Button(onClick = onSignOut) {
                Text("Sign Out")
            }
        }
    }
}

@Composable
fun TrashSummary(trashItems: List<com.example.junklabs.model.TrashItem>) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text("Collected Trash", fontWeight = FontWeight.Bold, fontSize = 20.sp)
        Spacer(modifier = Modifier.height(8.dp))
        if (trashItems.isEmpty()) {
            Text("No trash collected yet.")
        } else {
            TrashList(trashItems)
        }
    }
}