package com.example.quwitest.data

data class MessageLast(
    val user: User,
    val is_read: Integer,
    val text: String,
    val dta_create: String
) {
    fun isRead(): Boolean {
        return is_read.toString() == "1"
    }
}