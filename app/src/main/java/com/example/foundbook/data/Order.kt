package com.example.foundbook.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "orders")
data class Order (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val bookTitle: String,
    val receiverName: String,
    val withdrawalData: String,
    val status: Boolean,
)