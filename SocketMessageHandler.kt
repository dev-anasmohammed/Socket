package com.alexon.chatsocket.helper.socket

import android.util.Log
import com.alexon.chatsocket.helper.chat.models.ChatMessage
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import io.socket.client.Socket

fun Socket.emitMessage(onEvent: String, message: ChatMessage) {
    //send to socket
    Log.e("emitFileMessage",message.toString())
    emit(onEvent, Gson().toJson(message))
}

fun Socket.onReceiveMessage(
    onEvent: String,
    senderId: Int,
    onMessageReceived: (ChatMessage) -> Unit
) {
    on(onEvent) { args ->
        if (args[0] != null) {
            //response
            val socketResponse = args[0]

            //data
            val allData: JsonObject = JsonParser().parse(socketResponse.toString()).asJsonObject
            val chatMessage = Gson().fromJson(allData, ChatMessage::class.java)

            //if the message from the current user return
            if (chatMessage.senderId == senderId) return@on

            //add message to chat
            onMessageReceived.invoke(chatMessage)
        }
    }
}