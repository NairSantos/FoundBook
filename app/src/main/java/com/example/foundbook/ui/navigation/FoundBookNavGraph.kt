package com.example.foundbook.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.foundbook.ui.home.HomeDestination
import com.example.foundbook.ui.home.HomeScreen
import com.example.foundbook.ui.order.OrderDetails
import com.example.foundbook.ui.order.OrderDetailsDestination
import com.example.foundbook.ui.order.OrderDetailsScreen
import com.example.foundbook.ui.order.OrderEditDestination
import com.example.foundbook.ui.order.OrderEditScreen
import com.example.foundbook.ui.order.OrderEntryDestination
import com.example.foundbook.ui.order.OrderEntryScreen

@Composable
fun FoundBookNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = HomeDestination.route,
        modifier = modifier
    ) {
        composable(route = HomeDestination.route){
            HomeScreen(
                navigateToOrderEntry = { navController.navigate(OrderEntryDestination.route) },
                navigateToOrderDetail = { navController.navigate("${OrderDetailsDestination.route}/${it}") }
            )
        }

        composable(route = OrderEntryDestination.route) {
            OrderEntryScreen(
                navigateBack = { navController.popBackStack() }
            )
        }

        composable(
            route = OrderDetailsDestination.routeWithArgs,
            arguments = listOf(navArgument(OrderDetailsDestination.orderIdArg) {
                type = NavType.IntType
            })
        ) {
            OrderDetailsScreen(
                navigateToEditOrder = { navController.navigate("${OrderEditDestination.route}/$it") },
                navigateBack = { navController.navigateUp() }
            )
        }

        composable(
            route = OrderEditDestination.routeWithArgs,
            arguments = listOf(navArgument(OrderEditDestination.orderIdArg) {
                type = NavType.IntType
            })
        ) {
            OrderEditScreen(
                navigateBack = { navController.popBackStack() },
            )
        }
    }
}