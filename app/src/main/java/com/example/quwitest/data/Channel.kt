package com.example.quwitest.data

import com.google.gson.annotations.SerializedName

data class Channel(
    val id: Int,
    @SerializedName("message_last")
    val message_last: MessageLast,
    val pin_to_top:Boolean
){
    val messageLast get() = message_last
}