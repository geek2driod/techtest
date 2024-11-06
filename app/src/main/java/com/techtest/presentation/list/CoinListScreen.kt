package com.techtest.presentation.list

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.techtest.R
import com.techtest.domain.model.Coin
import com.techtest.utils.UiMessage

@Composable
fun CoinListScreen(
    goToDetails: (coinId: String) -> Unit,
    viewModel: CoinListViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.load()
    }

    ScreenContent(
        onCoinClick = { goToDetails(it.id) },
        state = state,
        refreshCoins = viewModel::onRefreshCoins
    )
}

//NOTE: Experimental APIs not recommend to use in production apps
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ScreenContent(
    onCoinClick: (Coin) -> Unit,
    state: CoinListState,
    refreshCoins: () -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.coins))
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = refreshCoins) {
                Icon(
                    imageVector = Icons.Default.Refresh,
                    contentDescription = null
                )
            }
        }
    ) { paddingValues ->
        when (state) {
            CoinListState.Loading -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            is CoinListState.Success -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                ) {
                    items(
                        count = state.coins.size,
                        key = { it }
                    ) { index ->
                        CoinListItem(
                            coin = state.coins[index],
                            onClick = onCoinClick
                        )

                        if (index < state.coins.size - 1) {
                            HorizontalDivider()
                        }
                    }
                }
            }

            is CoinListState.Error -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    when (state.uiMessage) {
                        is UiMessage.ResourceType -> {
                            Text(text = stringResource(id = state.uiMessage.message))
                        }

                        is UiMessage.StringType -> {
                            Text(text = state.uiMessage.message)
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun CoinListItem(
    modifier: Modifier = Modifier,
    coin: Coin,
    onClick: (Coin) -> Unit,
) {
    AnimatedVisibility(
        visible = true,
        enter = fadeIn(animationSpec = tween(500)),
        exit = fadeOut(animationSpec = tween(500))
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .clickable { onClick(coin) }
        ) {
            Text(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                text = coin.name,
                style = MaterialTheme.typography.headlineMedium
            )
        }
    }
}

@Preview
@Composable
private fun ScreenPreview() {
    ScreenContent(
        onCoinClick = {},
        refreshCoins = {},
        state = CoinListState.Loading
    )
}