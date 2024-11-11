package com.roman.cryptotracker.crypto.presentation.coin.list

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.roman.cryptotracker.crypto.presentation.coin.CoinAction
import com.roman.cryptotracker.crypto.presentation.coin.CoinUiState
import com.roman.cryptotracker.crypto.presentation.coin.list.components.CoinListView
import com.roman.cryptotracker.crypto.presentation.components.LoadingView
import com.roman.cryptotracker.ui.preview.CoinUiStatePreviewParameterProvider
import com.roman.cryptotracker.ui.theme.CryptoTrackerTheme


@Composable
fun CoinListScreenView(
    uiState: CoinUiState,
    onAction: (CoinAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    if (uiState.isLoading) {
        LoadingView(modifier)
    } else {
        CoinListView(
            uiState = uiState,
            onAction = onAction,
            modifier = modifier
        )
    }
}

@PreviewLightDark
@Composable
fun CoinListScreenPreview(
    @PreviewParameter(CoinUiStatePreviewParameterProvider::class) uiState: CoinUiState
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