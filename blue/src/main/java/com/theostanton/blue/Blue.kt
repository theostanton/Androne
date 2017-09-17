package com.theostanton.blue

import android.content.Context
import android.util.Log
import com.appunite.websocket.rx.`object`.ObjectParseException
import com.appunite.websocket.rx.`object`.ObjectSerializer
import com.google.gson.Gson
import com.google.gson.JsonParseException
import com.jakewharton.rxrelay2.BehaviorRelay
import io.reactivex.Observable
import okhttp3.*
import okio.ByteString
import java.lang.reflect.Type


class Blue(context: Context, ipAddress: String) : WebSocketListener() {

  private val relay = BehaviorRelay.create<String>()

  val request = Request.Builder()
      .get()
      .url("ws://$ipAddress:8080/ws")
      .build()

  val client = OkHttpClient()

  val webSocket = client.newWebSocket(request, this)

  fun push(msg: String) {
    webSocket.send(msg)
  }

  fun observe() = relay as Observable<String>


  override fun onOpen(webSocket: WebSocket?, response: Response?) {
    super.onOpen(webSocket, response)
    log("onOpen")
  }

  override fun onFailure(webSocket: WebSocket?, t: Throwable?, response: Response?) {
    super.onFailure(webSocket, t, response)
    log("onFailure")
  }

  override fun onClosing(webSocket: WebSocket?, code: Int, reason: String?) {
    super.onClosing(webSocket, code, reason)
    log("onClosing")
  }

  override fun onMessage(webSocket: WebSocket?, text: String?) {
    super.onMessage(webSocket, text)
    log("onMessage:$text")
    relay.accept(text)
  }

  override fun onMessage(webSocket: WebSocket?, bytes: ByteString?) {
    super.onMessage(webSocket, bytes)
    log("onMessage")
  }

  override fun onClosed(webSocket: WebSocket?, code: Int, reason: String?) {
    super.onClosed(webSocket, code, reason)
    log("onClosed")
  }

  private fun log(msg:String){
    Log.d("Blue",msg)
  }
}

data class Message(val msg: String)

class GsonObjectSerializer(val gson: Gson, val type: Type) : ObjectSerializer {

  @Throws(ObjectParseException::class)
  override fun serialize(message: String): Any {
    try {
      return gson.fromJson(message, type)
    } catch (e: JsonParseException) {
      throw ObjectParseException("Could not parse", e)
    }

  }

  @Throws(ObjectParseException::class)
  override fun serialize(message: ByteArray): Any {
    throw ObjectParseException("Could not parse binary messages")
  }

  @Throws(ObjectParseException::class)
  override fun deserializeBinary(message: Any): ByteArray {
    throw IllegalStateException("Only serialization to string is available")
  }

  @Throws(ObjectParseException::class)
  override fun deserializeString(message: Any): String {
    try {
      return gson.toJson(message)
    } catch (e: JsonParseException) {
      throw ObjectParseException("Could not parse", e)
    }

  }

  override fun isBinary(message: Any): Boolean {
    return false
  }


}

