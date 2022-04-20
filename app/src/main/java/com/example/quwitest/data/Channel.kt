package com.example.quwitest.data

import com.google.gson.annotations.SerializedName

data class Channel(
    val id: Int,
    @SerializedName("message_last")
    val message_last: MessageLast,
    val pin_to_top: Boolean
) {
    val lastMessage get() = message_last
    val pin get() = pin_to_top
}