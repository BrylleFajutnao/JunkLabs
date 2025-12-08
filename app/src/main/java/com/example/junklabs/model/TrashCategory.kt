package com.example.junklabs.model

sealed class TrashCategory(val name: String, val suggestion: String) {
    object Plastic : TrashCategory("Plastic", "Rinse plastic containers before recycling. Check the number code on the bottom to see if it's accepted by your local recycling service.")
    object Paper : TrashCategory("Paper", "Keep paper and cardboard clean and dry. Flatten cardboard boxes to save space.")
    object Metal : TrashCategory("Metal", "Rinse metal cans (like soda or food cans). Most metal items, including aluminum foil, are recyclable.")
    object Glass : TrashCategory("Glass", "Rinse glass bottles and jars. Some services require you to separate glass by color (clear, green, brown).")
    object Other : TrashCategory("Other", "For items not in other categories, check with your local waste management service for proper disposal instructions.")

    companion object {
        fun fromString(name: String): TrashCategory {
            return when (name) {
                "Plastic" -> Plastic
                "Paper" -> Paper
                "Metal" -> Metal
                "Glass" -> Glass
                else -> Other
            }
        }
    }
}

fun getAllTrashCategories(): List<TrashCategory> {
    return listOf(TrashCategory.Plastic, TrashCategory.Paper, TrashCategory.Metal, TrashCategory.Glass, TrashCategory.Other)
}
