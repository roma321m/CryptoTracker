package com.roman.cryptotracker.crypto.presentation.coin.details.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.roman.cryptotracker.R
import com.roman.cryptotracker.core.presentation.components.graph.ChartStyle
import com.roman.cryptotracker.core.presentation.components.graph.DataPoint
import com.roman.cryptotracker.core.presentation.components.graph.LineChart
import com.roman.cryptotracker.crypto.presentation.coin.CoinUiState
import com.roman.cryptotracker.crypto.presentation.models.CoinUiModel
import com.roman.cryptotracker.crypto.presentation.models.toDisplayableNumber
import com.roman.cryptotracker.ui.theme.greenBackground


@Composable
fun CoinDetailView(
    uiState: CoinUiState,
    modifier: Modifier = Modifier,
) {
    if (uiState.selectedCoin != null) {
        CoinDetailAvailableView(
            coin = uiState.selectedCoin,
            modifier = modifier
        )
    } else {
        CoinDetailNotAvailableView(
            modifier = modifier
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun CoinDetailAvailableView(
    coin: CoinUiModel,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Icon(
            modifier = Modifier.size(100.dp),
            tint = MaterialTheme.colorScheme.primary,
            imageVector = ImageVector.vectorResource(coin.iconRes),
            contentDescription = null
        )
        Text(
            text = coin.name,
            fontSize = 40.sp,
            fontWeight = FontWeight.Black,
            textAlign = TextAlign.Center
        )
        Text(
            text = coin.symbol,
            fontSize = 20.sp,
            fontWeight = FontWeight.Light,
            textAlign = TextAlign.Center
        )
        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            CoinDetailInfoCardView(
                title = stringResource(R.string.coin_detail_market_cap_title),
                formattedText = "$ ${coin.marketCapUsd.formattedValue}",
                icon = ImageVector.vectorResource(R.drawable.stock)
            )
            CoinDetailInfoCardView(
                title = stringResource(R.string.coin_detail_price_title),
                formattedText = "$ ${coin.priceUsd.formattedValue}",
                icon = ImageVector.vectorResource(R.drawable.dollar)
            )

            val absoluteChangeFormatted =
                (coin.priceUsd.value * (coin.changePercent24Hr.value / 100))
                    .toDisplayableNumber()
            val isPositive = coin.changePercent24Hr.value > 0.0
            CoinDetailInfoCardView(
                title = stringResource(R.string.coin_detail_change_last_24h_title),
                formattedText = "$ ${absoluteChangeFormatted.formattedValue}",
                icon = if (isPositive) {
                    ImageVector.vectorResource(R.drawable.trending)
                } else {
                    ImageVector.vectorResource(R.drawable.trending_down)
                },
                iconTint = if (isPositive) {
                    if (isSystemInDarkTheme()) Color.Green else greenBackground
                } else {
                    Color.Red
                }
            )
        }
        AnimatedVisibility(
            visible = coin.coinPriceHistory.isNotEmpty()
        ) {
            var selectedDataPoint by remember {
                mutableStateOf<DataPoint?>(null)
            }
            var totalChartWidth by remember {
                mutableFloatStateOf(0f)
            }
            var labelWidth by remember {
                mutableFloatStateOf(0f)
            }
            val amountOfVisibleDataPoints = if (labelWidth > 0) {
                ((totalChartWidth - 2.5 * labelWidth) / labelWidth).toInt()
            } else {
                0
            }
            val startIndex = (coin.coinPriceHistory.lastIndex - amountOfVisibleDataPoints)
                .coerceAtLeast(0)

            LineChart(
                dataPoints = coin.coinPriceHistory,
                style = ChartStyle(
                    chartLineColor = MaterialTheme.colorScheme.primary,
                    unSelectedColor = MaterialTheme.colorScheme.secondary,
                    selectedColor = MaterialTheme.colorScheme.primary,
                    helperLinesThicknessPx = 1f,
                    axisLineThicknessPx = 1f,
                    labelFontSize = 14.sp,
                    minYLabelSpacing = 25.dp,
                    minXLabelSpacing = 8.dp,
                    verticalPadding = 8.dp,
                    horizontalPadding = 8.dp
                ),
                visibleDataPointIndices = startIndex..coin.coinPriceHistory.lastIndex,
                unit = "$",
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(16 / 9f)
                    .onSizeChanged {
                        totalChartWidth = it.width.toFloat()
                    },
                selectedDataPoint = selectedDataPoint,
                onSelectedDataPointChange = {
                    selectedDataPoint = it
                },
                onXLabelWidthChange = {
                    labelWidth = it
                }
            )
        }
    }
}

@Composable
private fun CoinDetailNotAvailableView(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = stringResource(R.string.coin_detail_screen_not_coin_selected_title),
            fontSize = 24.sp,
            fontWeight = FontWeight.Normal,
            textAlign = TextAlign.Center
        )
    }
}
