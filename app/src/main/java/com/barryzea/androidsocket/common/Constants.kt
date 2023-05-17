package com.barryzea.androidsocket.common

/****
 * Project AndroidSocket
 * Created by Barry Zea H. on 16/05/2023.
 * Copyright (c)  All rights reserved.
 ***/
object Constants {
   // const val COINBASE_SOCKET_URL = "wss://ws-feed.exchange.coinbase.com"
    const val COINBASE_SOCKET_URL = "wss://ws-feed.pro.coinbase.com"
    const val SUBSCRIBE_STRING_BODY="""
            {
                "type": "subscribe",
                "channels": [
                    {
                        "name": "ticker",
                        "product_ids": [
                            "BTC-USD"
                            
                        ]
                    }
                ]
            }
        """
    const val UNSUBSCRIBE_STRING_BODY="""
            {
                "type": "unsubscribe",
                "channels": [
                    "ticker"
                ]
            }
        """
}