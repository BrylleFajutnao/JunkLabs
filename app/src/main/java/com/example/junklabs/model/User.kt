package com.example.junklabs.model

data class User(
    val fullName: String = "",
    val username: String = "",
    val email: String = "",
    val trashBagCount: Int = 0
)