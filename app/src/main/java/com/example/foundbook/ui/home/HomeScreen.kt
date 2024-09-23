package com.example.foundbook.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.foundbook.R
import com.example.foundbook.data.Order
import com.example.foundbook.ui.AppViewModelProvider
import com.example.foundbook.ui.navigation.NavigationDestination

object HomeDestination : NavigationDestination {
    override val route = "home"
    override val titleRes = R.string.app_name
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen (
    navigateToOrderEntry: () -> Unit,
    navigateToOrderDetail: (Int) -> Unit,
    modifier: Modifier = Modifier,

    viewModel: HomeViewModel = viewModel(factory = AppViewModelProvider.Factory)
)
{
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val homeUiState by viewModel.homeUiState.collectAsState()

    Box(
        Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(30.dp)
    ) {
        Column (
            Modifier
                .fillMaxWidth(),
            Arrangement.Center
        ) {
            Spacer(modifier = Modifier.height(20.dp))

            Row (
                Modifier
                    .fillMaxWidth(),
                Arrangement.Center
            ) {
                Text(
                    text = "I FOUND THE BOOK",
                    textAlign = TextAlign.Center,
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // LOCAL DO CORPO
            HomeBody(
                orderList = homeUiState.orderList,
                onOrderClick = navigateToOrderDetail,
                modifier = Modifier
                    .fillMaxSize()
                    .nestedScroll(scrollBehavior.nestedScrollConnection)
            )

        }

        FloatingActionButton(
            onClick = navigateToOrderEntry,
            containerColor = Color.Black,
            shape = RoundedCornerShape(50),
            modifier = Modifier
                .size(60.dp)
                .align(Alignment.BottomStart)
                .offset(y = (-50).dp)
        ) {
            Icon(
                Icons.Default.Add,
                contentDescription = "Add",
                tint = Color.White,
                modifier = Modifier
                    .size(40.dp)
            )
        }
    }

}

@Composable
private fun HomeBody(
    orderList: List<Order>,
    onOrderClick: (Int) -> Unit,
    modifier: Modifier
){

    if(orderList.isEmpty())
    {
        Spacer(modifier = Modifier.height(20.dp))

        Row (
            Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            Arrangement.Center
        ) {
            Text(
                text = """
                    Você ainda não encomendou nenhum livro.
                    Clique no botão de adicionar para pedir um.
                """.trimIndent(),
                fontSize = 20.sp,
                color = Color.Black
            )
        }
    }
    else
    {
        OrderList(
            orderList = orderList,
            onOrderClick = { onOrderClick(it.id) },
            modifier = Modifier
        )
    }

}

@Composable
private fun OrderList(
    orderList: List<Order>,
    onOrderClick: (Order) -> Unit,
    modifier: Modifier
){
    LazyColumn (
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxHeight()
    ) {
        items(items = orderList, key = {it.id}) { order ->

            OrderItem(
                order = order,
                onOrderClick = { onOrderClick(order) },
                modifier
            )

        }
    }
}

@Composable
private fun OrderItem(
    order: Order,
    onOrderClick: (Order) -> Unit,
    modifier: Modifier = Modifier
) {
    Card (
        modifier = Modifier
            .padding(20.dp)
            .clickable {
                onOrderClick(order)
            },
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column (
            modifier = Modifier
                .background(Color.Gray)
        ) {

            Row (
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(10.dp)
            ) {
                Text(
                    text = order.bookTitle,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.fillMaxWidth().padding(2.dp).weight(1f)
                )
            }

            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            ) {
                Text(
                    text = order.receiverName,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.fillMaxWidth().padding(2.dp).weight(1f)
                )
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeBodyPreview() {
    HomeBody(
        orderList = listOf(
            Order(1, "Livro 2", "João Pedro", "26 de setembro", false),
            Order(2, "Livro 25", "Victor", "22 de novembro", false)
        ),
        onOrderClick = {},
        modifier = Modifier.fillMaxSize()
    )
}

@Preview(showBackground = true)
@Composable
fun HomeBodyEmptyListPreview() {
    HomeBody(listOf(), onOrderClick = {}, modifier = Modifier)
}


@Preview(showBackground = true)
@Composable
fun OrderItemPreview(){
    OrderItem(
        order = Order(1, "Livro 2", "João Pedro", "26 de setembro", false),
        onOrderClick = {}
    )

}