package com.roman.cryptotracker.crypto.presentation.coin_list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.roman.cryptotracker.crypto.presentation.coin_list.views.CoinListItemView
import com.roman.cryptotracker.ui.preview.CoinListUiStatePreviewParameterProvider
import com.roman.cryptotracker.ui.theme.CryptoTrackerTheme


@Composable
fun CoinListScreenView(
    uiState: CoinListUiState,
    onAction: (CoinListAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    if (uiState.isLoading) {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        LazyColumn(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            items(uiState.coins) { coinUiModel ->
                CoinListItemView(
                    coinUiModel = coinUiModel,
                    onClick = {
                        onAction(CoinListAction.OnCoinClick(coinUiModel))
                    },
                    modifier = Modifier.fillMaxWidth()
                )
                HorizontalDivider()
            }
        }
    }
}

@PreviewLightDark
@Composable
fun CoinListScreenPreview(
    @PreviewParameter(CoinListUiStatePreviewParameterProvider::class) uiState: CoinListUiState
) {
    CryptoTrackerTheme {
        Surface {
            CoinListScreenView(
                uiState = uiState,
                onAction = {}
            )
        }
    }
}