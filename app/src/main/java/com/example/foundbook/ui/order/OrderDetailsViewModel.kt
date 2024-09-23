package com.example.foundbook.ui.order

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foundbook.data.OrderRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class OrderDetailsViewModel (
    savedStateHandle: SavedStateHandle,
    private val ordersRepository: OrderRepository
) : ViewModel() {

    private val orderId: Int = checkNotNull(savedStateHandle[OrderDetailsDestination.orderIdArg])

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }

    val uiState: StateFlow<OrderDetailsUiState> =
        ordersRepository.getOrderStream(orderId)
            .filterNotNull()
            .map {
                OrderDetailsUiState(alreadyPicked = it.status, orderDetails = it.toOrderDetails())
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = OrderDetailsUiState()
            )

    fun checkOut(){
        viewModelScope.launch {
            val currentOrder = uiState.value.orderDetails.toOrder()
            if(!currentOrder.status) {
                ordersRepository.updateOrder(currentOrder.copy(status = true))
            }
        }
    }

    suspend fun deleteOrder(){
        ordersRepository.deleteOrder(uiState.value.orderDetails.toOrder())
    }

}

data class OrderDetailsUiState(
    val alreadyPicked: Boolean = false,
    val orderDetails: OrderDetails = OrderDetails()
)