package com.example.junklabs.features

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.junklabs.model.TrashItem
import com.example.junklabs.model.TrashCategory

@Composable
fun TrashList(trashItems: List<TrashItem>) {
    LazyColumn(modifier = Modifier.fillMaxWidth()) {
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
}