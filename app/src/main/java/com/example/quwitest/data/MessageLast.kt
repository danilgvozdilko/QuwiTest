package com.example.quwitest.data

data class MessageLast(
    val user:User,
    val is_read: Integer,
    val text:String,
    val pin_to_top:Boolean,
    val dta_create:String
)