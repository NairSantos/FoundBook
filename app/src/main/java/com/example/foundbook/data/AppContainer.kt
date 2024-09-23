package com.example.foundbook.data

import android.content.Context

interface AppContainer {
    val orderRepository: OrderRepository
}

class AppDataContainer(private val context: Context) : AppContainer {
    override val orderRepository: OrderRepository by lazy {
        OfflineOrderRepository(BookOrderDatabase.getDatabase(context).orderDao())
    }
}