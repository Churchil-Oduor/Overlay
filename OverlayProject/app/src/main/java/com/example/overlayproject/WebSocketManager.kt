package com.example.overlayproject

import android.util.Log
import okhttp3.Response
import okhttp3.WebSocket
import org.json.JSONException
import org.json.JSONObject

class WebSocketManager(private val onPoseReceived: (FloatArray) -> Unit) {
    private val client = okhttp3.OkHttpClient()
    private lateinit var webSocket: WebSocket
    private val url_address: String = "10.177.181.22:8000/ws/camera"

    fun connect() {
        val request = okhttp3.Request.Builder()
            .url("ws://" + url_address)
            .build()

        webSocket = client.newWebSocket(request, object : okhttp3.WebSocketListener()
        {
            override fun onOpen(webSocket: WebSocket, response: Response) {
                super.onOpen(webSocket, response)
                Log.d("Websocket", "Connected Suceesfully")
            }

            override fun onMessage(webSocket: WebSocket,text: String) {
                super.onMessage(webSocket, text)

                try {
                    val json = JSONObject(text)
                    val mat = json.getJSONArray("pose")

                    val pose = FloatArray(16)
                    var idx = 0
                    for (r in 0..3) {
                        for (c in 0..3) {
                            var row = mat.getJSONArray(r)
                            pose[c * 4 + r] = row.getDouble(c).toFloat()
                            idx++
                        }
                    }
                    Log.d("Pix", pose.toString())
                    onPoseReceived(pose)

                } catch (e: JSONException) {
                    Log.e("Websocket", "Invalid Matrix", e)
                }
            }
        })
    }
    fun sendFrame(bytes: ByteArray) {
        webSocket.send(okio.ByteString.of(*bytes))
    }
}