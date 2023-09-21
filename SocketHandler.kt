package com.alexon.chatsocket.helper.socket

import android.util.Log
import io.socket.client.IO
import io.socket.client.Socket
import java.net.URISyntaxException

object SocketHandler {

    private var mSocket: Socket? = null

    @Synchronized
    fun setSocket(ip: String, port: String) {
        try {
            mSocket = IO.socket("$ip:$port")
            Log.e("SocketHandler", "Socket set to $ip:$port")
        } catch (e: URISyntaxException) {
            Log.e("SocketHandler", e.toString())
        }
    }

    @Synchronized
    fun getSocket(): Socket? {
        return try {
            mSocket
        } catch (e: Exception) {
            null
        }
    }

    @Synchronized
    fun establishConnection() {
        try {
            mSocket?.connect()
            Log.e("Is Socket connected ?", mSocket?.connected().toString())
        } catch (e: Exception) {
            Log.e("failed to establishConnection cause of: ", mSocket?.connected().toString())
        }
    }

    @Synchronized
    fun closeConnection() {
        mSocket?.disconnect()
        Log.e("Close socket", mSocket?.connected().toString())
    }
}
