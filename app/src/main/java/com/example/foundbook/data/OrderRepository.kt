package com.example.foundbook.data

import kotlinx.coroutines.flow.Flow

interface OrderRepository {

    fun getAllOrdersStream(): Flow<List<Order>>

    fun getOrderStream(id: Int): Flow<Order?>

    suspend fun insertOrder(order: Order)

    suspend fun updateOrder(order: Order)

    suspend fun deleteOrder(order: Order)

}