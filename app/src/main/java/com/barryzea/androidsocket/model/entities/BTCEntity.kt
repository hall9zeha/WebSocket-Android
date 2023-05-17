package com.barryzea.androidsocket.model.entities

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/****
 * Project AndroidSocket
 * Created by Barry Zea H. on 16/05/2023.
 * Copyright (c)  All rights reserved.
 ***/

@JsonClass(generateAdapter = true)
data class BTCEntity (@Json(name = "price")val price:String?)