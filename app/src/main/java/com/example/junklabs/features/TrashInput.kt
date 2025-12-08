package com.example.junklabs.features

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.junklabs.model.TrashCategory
import com.example.junklabs.model.getAllTrashCategories

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrashInput(onAddTrashItem: (String, TrashCategory) -> Unit) {
    var trashName by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }
    val trashCategories = getAllTrashCategories()
    var selectedCategory by remember { mutableStateOf(trashCategories.first()) }

    Column(modifier = Modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = trashName,
            onValueChange = { trashName = it },
            label = { Text("Enter trash item") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            OutlinedTextField(
                value = selectedCategory.name,
                onValueChange = { },
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = Modifier.fillMaxWidth().menuAnchor()
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                trashCategories.forEach { category ->
                    DropdownMenuItem(
                        text = { Text(category.name) },
                        onClick = {
                            selectedCategory = category
                            expanded = false
                        }
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                if (trashName.isNotBlank()) {
                    onAddTrashItem(trashName, selectedCategory)
                    trashName = ""
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Add Trash Item")
        }
    }
}