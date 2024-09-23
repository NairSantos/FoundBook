package com.example.foundbook.data

import kotlinx.coroutines.flow.Flow

class OfflineOrderRepository(private val orderDao: OrderDao) : OrderRepository {

    override fun getAllOrdersStream(): Flow<List<Order>> = orderDao.getAllOrders()

    override fun getOrderStream(id: Int): Flow<Order?> = orderDao.getOrder(id)

    override suspend fun insertOrder(order: Order) = orderDao.insert(order)

    override suspend fun updateOrder(order: Order) = orderDao.update(order)

    override suspend fun deleteOrder(order: Order) = orderDao.delete(order)

}