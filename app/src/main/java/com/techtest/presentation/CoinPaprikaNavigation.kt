package com.techtest.presentation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.techtest.presentation.detail.CoinDetailScreen
import com.techtest.presentation.list.CoinListScreen

private const val ARG_COIN_ID = "coinId"

@Composable
fun CoinPaprikaNavigation(
    controller: NavHostController = rememberNavController()
) {
    NavHost(
        navController = controller,
        startDestination = Screen.CoinList.route
    ) {
        composable(route = Screen.CoinList.route) {
            CoinListScreen(
                goToDetails = { coinId ->
                    controller.navigate(
                        Screen.CoinDetail.route.plus("/$coinId")
                    )
                }
            )
        }

        composable(
            route = Screen.CoinDetail.route.plus("/{$ARG_COIN_ID}"),
            arguments = listOf(navArgument(ARG_COIN_ID) { type = NavType.StringType })
        ) {
            CoinDetailScreen(
                coinId = requireNotNull(it.arguments?.getString(ARG_COIN_ID)),
                goToBack = {
                    controller.navigateUp()
                }
            )
        }
    }
}

sealed class Screen(val route: String) {
    object CoinList : Screen(route = "coin_list")
    object CoinDetail : Screen(route = "coin_detail")
}