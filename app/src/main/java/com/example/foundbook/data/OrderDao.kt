package com.example.foundbook.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface OrderDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(order: Order)

    @Update
    suspend fun update(order: Order)

    @Delete
    suspend fun delete(order: Order)

    @Query("SELECT * FROM orders WHERE id = :id")
    fun getOrder(id: Int): Flow<Order>

    @Query("SELECT * FROM orders ORDER BY bookTitle ASC")
    fun getAllOrders(): Flow<List<Order>>

}