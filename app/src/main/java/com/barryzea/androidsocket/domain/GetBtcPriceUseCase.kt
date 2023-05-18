package com.barryzea.androidsocket.domain

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.barryzea.androidsocket.common.Constants
import com.barryzea.androidsocket.model.entities.BTCEntity
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import javax.inject.Inject

/****
 * Project AndroidSocket
 * Created by Barry Zea H. on 16/05/2023.
 * Copyright (c)  All rights reserved.
 ***/

@Module
@InstallIn(ViewModelComponent::class)
class  GetBtcPriceUseCase @Inject constructor() {
   var webSocketListener:WebSocketListener?=null
    private var webSocket:WebSocket ? = null
    private var okHttpClient = OkHttpClient()

    private var coinbaseResponse:MutableLiveData<BTCEntity>  = MutableLiveData()
    private var isLoading:MutableLiveData<Boolean> = MutableLiveData(true)

    init {
        setUpOkhttpSocket()
    }
    private fun setUpOkhttpSocket(){
        webSocketListener = object:WebSocketListener(){
            override fun onOpen(webSocket: WebSocket, response: Response) {
                super.onOpen(webSocket, response)
                subscribe()

            }

            override fun onMessage(webSocket: WebSocket, text: String) {
                super.onMessage(webSocket, text)
                isLoading.postValue(false)
                getPrice(text)
            }

            override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
                super.onFailure(webSocket, t, response)
                Log.e("ERROR_SOCKET",t.message.toString())
                isLoading.postValue(false)
            }

            override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
                super.onClosed(webSocket, code, reason)
                unSubscribe()
            }

            override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
                super.onClosing(webSocket, code, reason)

            }

        }
    }
    private fun createRequest() = Request.Builder()
            .url(Constants.COINBASE_SOCKET_URL)
            .build()

    private fun subscribe(){
        webSocket?.send(Constants.SUBSCRIBE_STRING_BODY)
    }
    private fun unSubscribe(){
        webSocket?.send(Constants.UNSUBSCRIBE_STRING_BODY)
    }
    fun startSocket(){
        webSocket = okHttpClient.newWebSocket(createRequest(),webSocketListener!!)
    }
    fun closeSocket(){
        webSocket?.close(1000,"on pause status disconnect")
    }

    fun onDestroy(){
        okHttpClient.dispatcher.executorService.shutdown()
    }

    private fun getPrice(response:String?){
        response?.let{
            val moshi = Moshi.Builder().build()
            val adapter: JsonAdapter<BTCEntity> = moshi.adapter(BTCEntity::class.java)
            val bitcoin= adapter.fromJson(response)
            coinbaseResponse.postValue(bitcoin)
        }

    }
    fun getCoinbaseResult():LiveData<BTCEntity> =  coinbaseResponse

    fun getIsLoading():MutableLiveData<Boolean> = isLoading
}