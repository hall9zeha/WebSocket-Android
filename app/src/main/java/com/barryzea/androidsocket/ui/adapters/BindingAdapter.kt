package com.barryzea.androidsocket.ui.adapters

import android.view.View
import androidx.databinding.BindingAdapter

/****
 * Project AndroidSocket
 * Created by Barry Zea H. on 17/05/2023.
 * Copyright (c)  All rights reserved.
 ***/
@BindingAdapter("isGone")
fun bindGone(view:View, isLoading:Boolean){
    view.visibility = if(isLoading) View.VISIBLE else View.GONE
}