package com.example.foundbook.ui.order

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foundbook.data.Order
import com.example.foundbook.data.OrderRepository
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class OrderEditViewModel (
    savedStateHandle: SavedStateHandle,
    private val ordersRepository: OrderRepository
) : ViewModel() {

    var orderUiState by mutableStateOf(OrderUiState())
        private set

    private val orderId: Int = checkNotNull(savedStateHandle[OrderDetailsDestination.orderIdArg])

    private fun validateInput(uiState: OrderDetails = orderUiState.orderDetails): Boolean {
        return with(uiState) {
            bookTitle.isNotBlank() && receiverName.isNotBlank() && withdrawalData.isNotBlank()
        }
    }

    init {
        viewModelScope.launch {
            orderUiState = ordersRepository.getOrderStream(orderId)
                .filterNotNull()
                .first()
                .toUiState(true)
        }
    }

    fun updateUiState(orderDetails: OrderDetails) {
        orderUiState =
            OrderUiState(orderDetails = orderDetails, isEntryValid = validateInput(orderDetails))
    }

    suspend fun updateOrder(){
        if(validateInput(orderUiState.orderDetails)) {
            ordersRepository.updateOrder(orderUiState.orderDetails.toOrder())

        }
    }

}