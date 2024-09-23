package com.example.foundbook.ui
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.foundbook.FoundBookApplication
import com.example.foundbook.ui.home.HomeViewModel
import com.example.foundbook.ui.order.OrderDetailsViewModel
import com.example.foundbook.ui.order.OrderEditViewModel
import com.example.foundbook.ui.order.OrderEntryViewModel

object AppViewModelProvider {

    val Factory = viewModelFactory {
        initializer {
            HomeViewModel(foundBookApplication().container.orderRepository)
        }

        initializer {
            OrderEntryViewModel(foundBookApplication().container.orderRepository)
        }

        initializer {
            OrderDetailsViewModel(
                this.createSavedStateHandle(),
                foundBookApplication().container.orderRepository
            )
        }

        initializer {
            OrderEditViewModel(
                this.createSavedStateHandle(),
                foundBookApplication().container.orderRepository
            )
        }
    }

}

fun CreationExtras.foundBookApplication(): FoundBookApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as FoundBookApplication)