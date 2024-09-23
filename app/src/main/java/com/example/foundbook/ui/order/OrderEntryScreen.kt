package com.example.foundbook.ui.order

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.DrawModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
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

object OrderEntryDestination : NavigationDestination {
    override val route = "order_entry"
    override val titleRes = R.string.order_entry_title
}

@Composable
fun OrderEntryScreen(
    navigateBack: () -> Unit,
    canNavigateBack: Boolean = true,

    viewModel: OrderEntryViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {

    Column (
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(Color.White)
    ) {
        Spacer(modifier = Modifier.height(20.dp))

        Row (
           Modifier
               .fillMaxWidth()
               .absoluteOffset(x = 10.dp, y = 50.dp),
            Arrangement.Start
        ) {
            if (canNavigateBack) {
                IconButton(
                    onClick = { navigateBack() }
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Voltar",
                        tint = Color.Black
                    )
                }
            }
        }

        Row (
            Modifier
                .fillMaxWidth()
                .offset(y = 10.dp),
            Arrangement.Center
        ) {
            Text(
                text = stringResource(OrderEntryDestination.titleRes),
                textAlign = TextAlign.Center,
                fontSize = 30.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        val coroutineScope = rememberCoroutineScope()

        OrderEntryBody(
            orderUiState = viewModel.orderUiState,
            onOrderValueChange = viewModel::updateUiState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.saveOrder()
                    navigateBack()
                }

            }
        )

    }

}


@Composable
fun OrderEntryBody(
    orderUiState: OrderUiState,
    onOrderValueChange: (OrderDetails) -> Unit,
    onSaveClick: () -> Unit,

    modifier: Modifier = Modifier
){

    val focusManager = LocalFocusManager.current

    Column (
        Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(top = 20.dp)
            .background(Color.White)
            .clickable { focusManager.clearFocus() },
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        OrderInputForm(
            orderDetails = orderUiState.orderDetails,
            onValueChange = onOrderValueChange,
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = onSaveClick,
            shape = RoundedCornerShape(50),
            modifier = Modifier.fillMaxWidth(0.5f),
            enabled = orderUiState.isEntryValid,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Blue,
                contentColor = Color.White,
                disabledContainerColor = Color.Gray
            )
        ) {
            Text(
                text = "Salvar",
                fontSize = 25.sp
            )
        }
    }

}

@Composable
fun OrderInputForm(
    orderDetails: OrderDetails,
    modifier: Modifier = Modifier,
    onValueChange: (OrderDetails) -> Unit = {},
    enabled: Boolean = true
) {

    Spacer(modifier = Modifier.height(20.dp))

    Row (
        Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, bottom = 10.dp, top = 10.dp, end = 20.dp)
    ) {
        OutlinedTextField(
            value = orderDetails.bookTitle,
            onValueChange = { onValueChange(orderDetails.copy(bookTitle = it)) },
            label = { Text(text = "Título do Livro") },
            modifier = Modifier.weight(1f),
            enabled = enabled,
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,
                unfocusedLabelColor = Color.Black,
                focusedLabelColor = Color.Black
            )
        )
    }


    Row (
        Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, bottom = 10.dp, top = 10.dp, end = 20.dp)
    ) {
        OutlinedTextField(
            value = orderDetails.receiverName,
            onValueChange = { onValueChange(orderDetails.copy(receiverName = it)) },
            label = { Text(text = "Destinatário") },
            modifier = Modifier.weight(1f),
            enabled = enabled,
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,
                unfocusedLabelColor = Color.Black,
                focusedLabelColor = Color.Black
            )
        )
    }

    Row (
        Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, bottom = 10.dp, top = 10.dp, end = 20.dp)
    ) {
        OutlinedTextField(
            value = orderDetails.withdrawalData,
            onValueChange = { onValueChange(orderDetails.copy(withdrawalData = it)) },
            label = { Text(text = "Data de retirada") },
            modifier = Modifier.weight(1f),
            enabled = enabled,
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black,
                unfocusedLabelColor = Color.Black,
                focusedLabelColor = Color.Black
            )
        )
    }

    Spacer(modifier = Modifier.height(20.dp))

}