package com.techtest.presentation.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.techtest.R
import com.techtest.domain.model.Coin
import com.techtest.utils.UiMessage

@Composable
fun CoinDetailScreen(
    coinId: String,
    goToBack: () -> Unit,
    viewModel: CoinDetailViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(coinId) {
        viewModel.load(coinId)
    }

    ScreenContent(
        goToBack = goToBack,
        state = state
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ScreenContent(
    goToBack: () -> Unit,
    state: CoinDetailState
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.coin_detail))
                },
                navigationIcon = {
                    IconButton(onClick = goToBack) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            when (state) {
                CoinDetailState.Loading -> {
                    CircularProgressIndicator()
                }

                is CoinDetailState.Success -> {
                    CoinDetail(coin = state.coin)
                }

                is CoinDetailState.Error -> {
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
private fun CoinDetail(
    modifier: Modifier = Modifier,
    coin: Coin
) {
    Column(
        modifier = modifier.padding(12.dp).fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        AsyncImage(
            model = coin.logo,
            contentDescription = null,
            error = painterResource(id = R.drawable.ic_launcher_background),
            modifier = Modifier
                .padding(top = 8.dp, bottom = 8.dp)
                .size(300.dp)
                .clip(RoundedCornerShape(8.dp))
        )

        Text(
            text = coin.name,
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Text(
            text = coin.description,
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(all = 24.dp)
        )

        coin.tags.forEach { tag ->
            Text(
                text = stringResource(id = R.string.hash).plus(tag.name),
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }
    }
}

@Preview
@Composable
private fun ScreenContentPreview() {

}