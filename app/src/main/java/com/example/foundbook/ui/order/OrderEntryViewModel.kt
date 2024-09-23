package com.example.foundbook.ui.order

import android.content.ClipData.Item
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.foundbook.data.Order
import com.example.foundbook.data.OrderRepository

class OrderEntryViewModel(private val ordersRepository: OrderRepository) : ViewModel() {

    var orderUiState by mutableStateOf(OrderUiState())
        private set

    fun updateUiState(orderDetails: OrderDetails){
        orderUiState =
            OrderUiState(orderDetails = orderDetails, isEntryValid = validateInput(orderDetails))
    }

    private fun validateInput(uiState: OrderDetails = orderUiState.orderDetails): Boolean {
        return with(uiState) {
            bookTitle.isNotBlank() && receiverName.isNotBlank() && withdrawalData.isNotBlank()
        }
    }

    suspend fun saveOrder(){
        if(validateInput()){
            ordersRepository.insertOrder(orderUiState.orderDetails.toOrder())
        }
    }

}

data class OrderUiState(
    val orderDetails: OrderDetails = OrderDetails(),
    val isEntryValid: Boolean = false
)

data class OrderDetails(
    val id: Int = 0,
    val bookTitle: String = "",
    val receiverName: String = "",
    val withdrawalData: String = "",
    val status: Boolean = false
)

fun OrderDetails.toOrder(): Order = Order(
    id = id,
    bookTitle = bookTitle,
    receiverName = receiverName,
    withdrawalData = withdrawalData,
    status = status
)


fun Order.toUiState(isEntryValid: Boolean = false): OrderUiState = OrderUiState(
    orderDetails = this.toOrderDetails(),
    isEntryValid = isEntryValid
)

fun Order.toOrderDetails(): OrderDetails = OrderDetails(
    id = id,
    bookTitle = bookTitle,
    receiverName = receiverName,
    withdrawalData = withdrawalData,
    status = status
)