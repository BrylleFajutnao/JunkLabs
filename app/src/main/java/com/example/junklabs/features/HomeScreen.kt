package com.example.junklabs.features

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
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
import com.example.junklabs.model.TrashCategory
import com.example.junklabs.model.TrashItem
import androidx.compose.foundation.background
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.foundation.shape.RoundedCornerShape

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
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Logo()
            }
            item {
                Spacer(modifier = Modifier.height(16.dp))
            }
            item {
                Text(text = "Welcome, ${user!!.fullName}!", fontSize = 20.sp)
            }

            // THE NEW STATISTICS VIEWER
            item {
                Spacer(modifier = Modifier.height(16.dp))
            }
            item {
                SimpleStatisticsViewer(trashItems)
            }

            // TRASH INPUT SECTION
            item {
                Spacer(modifier = Modifier.height(16.dp))
            }
            item {
                TrashInput { name, category ->
                    homeViewModel.addTrashItem(name, category)
                }
            }

            // TRASH SUMMARY HEADER
            item {
                Spacer(modifier = Modifier.height(16.dp))
            }
            item {
                Text(
                    text = "Collected Trash",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    modifier = Modifier.fillMaxWidth()
                )
            }
            item {
                Spacer(modifier = Modifier.height(8.dp))
            }

            // TRASH LIST ITEMS
            if (trashItems.isEmpty()) {
                item {
                    Text("No trash collected yet.")
                }
            } else {
                items(trashItems) { item ->
                    val category = TrashCategory.fromString(item.categoryName)
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(text = item.name, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                            Text(text = "Category: ${category.name}")
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(text = category.suggestion)
                        }
                    }
                }
            }

            // SIGN OUT BUTTON
            item {
                Spacer(modifier = Modifier.height(16.dp))
            }
            item {
                Button(onClick = onSignOut) {
                    Text("Sign Out")
                }
            }
        }
    }
}

@Composable
fun SimpleStatisticsViewer(trashItems: List<TrashItem>) {
    // 1. Calculate Total Items
    val totalItems = trashItems.size

    // 2. Calculate Items per Category
    val categoryCounts = trashItems.groupBy { it.categoryName }
        .mapValues { it.value.size }
        .toList()
        .sortedByDescending { it.second }

    // 3. Create the Category Breakdown String
    val categorySummary = categoryCounts.joinToString(separator = " | ") { (category, count) ->
        "$category: $count"
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .background(Color(0xFFE3F2FD), RoundedCornerShape(8.dp))
            .padding(12.dp)
    ) {
        // Line 1: Total Items
        Text(
            text = buildAnnotatedString {
                withStyle(style = SpanStyle(color = Color.Black, fontWeight = FontWeight.SemiBold)) {
                    append("Total items: ")
                }
                withStyle(style = SpanStyle(color = Color(0xFF1976D2), fontWeight = FontWeight.ExtraBold)) {
                    append(totalItems.toString())
                }
            },
            fontSize = 16.sp
        )

        Spacer(modifier = Modifier.height(4.dp))

        // Line 2: Category Breakdown
        Text(
            text = categorySummary,
            color = Color.DarkGray,
            fontSize = 14.sp
        )
    }
}