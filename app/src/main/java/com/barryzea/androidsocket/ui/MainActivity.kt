package com.barryzea.androidsocket.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.barryzea.androidsocket.R
import com.barryzea.androidsocket.databinding.ActivityMainBinding
import com.barryzea.androidsocket.ui.viewModel.MainViewModel
import com.barryzea.androidsocket.BR
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val mainViewModel:MainViewModel by viewModels()
    private lateinit var bind:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bind = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setUpViewModel()
        mainViewModel.getPriceOfBtc()
    }
    private fun setUpViewModel(){
        bind.lifecycleOwner = this
        bind.setVariable(BR.mainViewModel,mainViewModel)

    }
    override fun onPause() {
        super.onPause()
        mainViewModel.closeSocket()
    }
    override fun onResume() {
        super.onResume()
        mainViewModel.startSocket()

    }



}