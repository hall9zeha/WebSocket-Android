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
import org.java_websocket.client.WebSocketClient
import org.java_websocket.handshake.ServerHandshake
import java.lang.Exception
import java.net.URI
import javax.inject.Inject
import javax.net.ssl.SSLSocketFactory

/****
 * Project AndroidSocket
 * Created by Barry Zea H. on 16/05/2023.
 * Copyright (c)  All rights reserved.
 ***/

@Module
@InstallIn(ViewModelComponent::class)
class  GetBtcPriceUseCase @Inject constructor() {
    var webSocketClient:WebSocketClient?=null
    private var coinbaseResponse:MutableLiveData<BTCEntity>  = MutableLiveData()
    private var isLoading:MutableLiveData<Boolean> = MutableLiveData(true)

    private fun setUpWebSocketClient(){
        val coinBaseUri: URI? = URI(Constants.COINBASE_SOCKET_URL)

        createWebSocketClient(coinBaseUri)
        val socketFactory = SSLSocketFactory.getDefault() as SSLSocketFactory
        webSocketClient?.setSocketFactory(socketFactory)
        webSocketClient?.connect()
    }
    private fun createWebSocketClient(coinBaseURI: URI?){
        webSocketClient = object:WebSocketClient(coinBaseURI){
            override fun onOpen(handshakedata: ServerHandshake?) {
                subscribe()
            }

            override fun onMessage(message: String?) {
                //Log.e("TAG", message.toString() )
                isLoading.postValue(false)
                getPrice(message)
            }

            override fun onClose(code: Int, reason: String?, remote: Boolean) {
                unSubscribe()
            }

            override fun onError(ex: Exception?) {
                Log.e("ERROR_SOCKET",ex?.message.toString())
                isLoading.postValue(false)
            }
        }
    }
    fun closeSocket(){
        webSocketClient?.close()
    }
    fun startSocket(){
        setUpWebSocketClient()
    }
     private fun subscribe(){
        webSocketClient?.send(Constants.SUBSCRIBE_STRING_BODY)
    }

    private fun unSubscribe(){
        webSocketClient?.send(Constants.UNSUBSCRIBE_STRING_BODY)
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