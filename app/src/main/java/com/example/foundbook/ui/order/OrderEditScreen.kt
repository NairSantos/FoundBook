package com.example.foundbook.ui.order

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.foundbook.R
import com.example.foundbook.ui.AppViewModelProvider
import com.example.foundbook.ui.navigation.NavigationDestination
import kotlinx.coroutines.launch

object OrderEditDestination : NavigationDestination {
    override val route = "order_edit"
    override val titleRes = R.string.order_edit_title
    const val orderIdArg = "orderId"
    val routeWithArgs = "$route/{$orderIdArg}"
}

@Composable
fun OrderEditScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    canNavigateBack: Boolean = true,

    viewModel: OrderEditViewModel = viewModel(factory = AppViewModelProvider.Factory)
){

    val coroutineScope = rememberCoroutineScope()

    Column (
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(Color.White),
    ) {
        Spacer(Modifier.height(10.dp))

        Row (
            Modifier
                .fillMaxWidth()
                .absoluteOffset(x = 10.dp, y = 50.dp),
            Arrangement.Start
        ) {
            if (canNavigateBack) {
                IconButton(
                    onClick = {
                        navigateBack()
                    }
                ) {
                    Icon(
                        Icons.Default.ArrowBack,
                        contentDescription = "Voltar",
                        modifier = Modifier.size(35.dp),
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
                text = stringResource(OrderEditDestination.titleRes),
                textAlign = TextAlign.Center,
                fontSize = 30.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black
            )
        }

        Spacer(modifier = Modifier.height(40.dp))

        OrderEntryBody(
            orderUiState = viewModel.orderUiState,
            onOrderValueChange = viewModel::updateUiState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.updateOrder()
                    navigateBack()
                }
            },
            modifier = Modifier
        )

    }
}
