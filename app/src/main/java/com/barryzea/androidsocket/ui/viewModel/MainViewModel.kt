package com.barryzea.androidsocket.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.barryzea.androidsocket.domain.GetBtcPriceUseCase
import com.barryzea.androidsocket.model.entities.BTCEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/****
 * Project AndroidSocket
 * Created by Barry Zea H. on 16/05/2023.
 * Copyright (c)  All rights reserved.
 ***/

@HiltViewModel
class MainViewModel @Inject constructor(private val useCase: GetBtcPriceUseCase):ViewModel() {

    //Este nombre será el que llamaremos desde nuestro layout con dataBinding  el cual solo se escribirá como "response"
    fun  getResponse():LiveData<BTCEntity>  = getPriceOfBtc()
    fun  getLoading():LiveData<Boolean> = useCase.getIsLoading()

    fun getPriceOfBtc():LiveData<BTCEntity>{
       return useCase.getCoinbaseResult()
    }
    fun startSocket(){
        useCase.startSocket()
    }
    fun closeSocket(){
        useCase.closeSocket()
    }


}