package com.roman.cryptotracker.crypto.presentation.coin.list.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.roman.cryptotracker.crypto.presentation.coin.CoinAction
import com.roman.cryptotracker.crypto.presentation.coin.CoinUiState

@Composable
fun CoinListView(
    uiState: CoinUiState,
    onAction: (CoinAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(uiState.coins) { coinUiModel ->
            CoinListItemView(
                coinUiModel = coinUiModel,
                onClick = {
                    onAction(CoinAction.OnCoinClick(coinUiModel))
                },
                modifier = Modifier.fillMaxWidth()
            )
            HorizontalDivider()
        }
    }
}