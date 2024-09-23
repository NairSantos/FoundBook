package com.example.foundbook.ui.order

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.foundbook.R
import com.example.foundbook.data.Order
import com.example.foundbook.ui.AppViewModelProvider
import com.example.foundbook.ui.navigation.NavigationDestination
import kotlinx.coroutines.launch

object OrderDetailsDestination : NavigationDestination {
    override val route = "order_details"
    override val titleRes = R.string.order_detail_title
    const val orderIdArg = "orderId"
    val routeWithArgs = "$route/{$orderIdArg}"
}

@Composable
fun OrderDetailsScreen(
    navigateToEditOrder: (Int) -> Unit,
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,

    viewModel: OrderDetailsViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {

    val uiState = viewModel.uiState.collectAsState()
    val coroutineScope = rememberCoroutineScope()

    Box(
       Modifier
           .fillMaxWidth()
           .background(Color.White)
    ) {
        Column (
            Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .background(Color.White)
        ) {
            Spacer(modifier = Modifier.height(20.dp))

            Row (
                Modifier
                    .fillMaxWidth(),
                Arrangement.Center
            ) {
                Text(
                    text = "Pedido",
                    textAlign = TextAlign.Center,
                    fontSize = 30.sp
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            OrderDetailsBody(
                orderDetailsUiState = uiState.value,
                onCheckout = { viewModel.checkOut() },
                onDelete = {
                    coroutineScope.launch {
                        viewModel.deleteOrder()
                        navigateBack()
                    }
                }
            )

        }

        FloatingActionButton(
            onClick = { navigateToEditOrder(uiState.value.orderDetails.id) },
            containerColor = Color.Black,
            shape = RoundedCornerShape(50),
            modifier = Modifier
                .padding(16.dp)
                .size(70.dp)
                .align(Alignment.BottomEnd)
                .offset(y = (-70.dp), x = (-30).dp)
        ) {
            Icon(
                Icons.Default.Edit,
                contentDescription = "Adicionar tarefa",
                tint = Color.White,
                modifier = Modifier.size(40.dp)
            )
        }
    }
}

@Composable
fun OrderDetailsBody(
    orderDetailsUiState: OrderDetailsUiState,
    onCheckout: () -> Unit,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier
){
    Column (
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        var deleteConfirmationRequired by rememberSaveable { mutableStateOf(false) }

        OrderDetails(
            order = orderDetailsUiState.orderDetails.toOrder(),
            modifier = Modifier
                .fillMaxWidth()

        )

        if (!orderDetailsUiState.alreadyPicked)
        {
            Button(
                onClick = onCheckout,
                modifier = Modifier
                    .fillMaxWidth(),
                shape = RoundedCornerShape(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Black,
                    contentColor = Color.White,
                ),
                enabled = true
            ) {

                Text(text = "CONCLUIR", fontSize = 20.sp, fontWeight = FontWeight.Bold)

            }
        }
        else
        {
            Button(
                onClick = onCheckout,
                modifier = Modifier
                    .fillMaxWidth(),
                shape = RoundedCornerShape(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Black,
                    contentColor = Color.White,
                    disabledContainerColor = Color.Gray,
                ),
                enabled = false
            ) {

                Text(text = "CONCLUÍDA", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.White)

            }
        }


        OutlinedButton(
            onClick = { deleteConfirmationRequired = true },
            modifier = Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(50.dp),
            enabled = true
        ) {
            Text(text = "DELETAR", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.Black)
        }

        if (deleteConfirmationRequired){
            DeleteConfirmDialog(
                onDeleteConfirm = {
                    deleteConfirmationRequired = false
                    onDelete()
                },
                onDeleteCancel = { deleteConfirmationRequired = false },
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}

@Composable
fun OrderDetails (
    order: Order,
    modifier: Modifier
){
    Card (
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(bottomStart = 20.dp, topEnd = 20.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.Gray
        )
    ) {
       Column (
           modifier = Modifier
               .fillMaxWidth()
               .padding(20.dp)
       ) {
           Row (
               modifier = Modifier
                   .fillMaxWidth()
                   .padding(bottom = 8.dp),
               Arrangement.Center
           ) {
               Text(
                   text = order.bookTitle,
                   color = Color.White,
                   fontWeight = FontWeight.Bold,
                   fontSize = 25.sp,
               )
           }

           Spacer(modifier = Modifier.height(30.dp))

           Row (
               modifier = Modifier
                   .fillMaxWidth()
                   .padding(bottom = 8.dp),
           ) {
               Text(
                   text = order.receiverName,
                   color = Color.White,
                   fontSize = 20.sp,
               )
           }

           Spacer(modifier = Modifier.height(40.dp))

           Row (
               modifier = Modifier
                   .fillMaxWidth()
                   .padding(bottom = 8.dp),
           ) {
               Text(
                   text = "Data de retirada: ",
                   color = Color.White,
                   fontWeight = FontWeight.Bold,
                   fontSize = 20.sp,
               )
               Text(
                   text = order.withdrawalData,
                   color = Color.White,
                   fontSize = 20.sp,
               )
           }
       }
    }
}

@Composable
private fun DeleteConfirmDialog(
    onDeleteConfirm: () -> Unit,
    onDeleteCancel: () -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(onDismissRequest = { /* Do nothing */ },
        title = { Text(text = "Atenção!") },
        text = { Text(text= "Tem certeza que quer excluir?") },
        modifier = modifier,
        dismissButton = {
            TextButton(onClick = onDeleteCancel) {
                Text(text = "Não")
            }
        },
        confirmButton = {
            TextButton(onClick = onDeleteConfirm) {
                Text(text = "Sim")
            }
        })
}
