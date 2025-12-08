package com.example.junklabs.features

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
fun HomeScreen() {
    val homeViewModel: HomeViewModel = viewModel()
    val user by homeViewModel.user.collectAsState()
    val trashItems by homeViewModel.trashItems.collectAsState()

    Column(
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Logo()
        Spacer(modifier = Modifier.height(16.dp))
        user?.let {
            Text(text = "Welcome, ${it.fullName}!", fontSize = 20.sp)
        }
        Spacer(modifier = Modifier.height(16.dp))
        TrashInput { name, category ->
            homeViewModel.addTrashItem(name, category)
        }
        Spacer(modifier = Modifier.height(16.dp))
        TrashSummary(trashItems)
    }
}

@Composable
fun TrashSummary(trashItems: List<com.example.junklabs.model.TrashItem>) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Collected Trash", fontWeight = FontWeight.Bold, fontSize = 20.sp)
            Spacer(modifier = Modifier.height(8.dp))
            if (trashItems.isEmpty()) {
                Text(text = "No trash collected yet.")
            } else {
                TrashList(trashItems)
            }
        }
    }
}