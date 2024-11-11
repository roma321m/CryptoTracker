package com.roman.cryptotracker.crypto.presentation.coin.list.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.roman.cryptotracker.crypto.presentation.models.CoinUiModel
import com.roman.cryptotracker.ui.preview.CoinUiModelPreviewParameterProvider
import com.roman.cryptotracker.ui.theme.CryptoTrackerTheme

@Composable
fun CoinListItemView(
    coinUiModel: CoinUiModel,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .clickable(onClick = onClick)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Icon(
            modifier = Modifier.size(85.dp),
            imageVector = ImageVector.vectorResource(id = coinUiModel.iconRes),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary
        )
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = coinUiModel.symbol,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = coinUiModel.name,
                fontSize = 14.sp,
                fontWeight = FontWeight.Light
            )
        }
        Column(
            horizontalAlignment = Alignment.End
        ) {
            Text(
                text = "$ ${coinUiModel.priceUsd.formattedValue}",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(8.dp))
            PriceChangeView(
                change = coinUiModel.changePercent24Hr
            )
        }
    }
}


@PreviewLightDark
@Composable
fun CoinListItemPreview(
    @PreviewParameter(CoinUiModelPreviewParameterProvider::class) coinUiModel: CoinUiModel
) {
    CryptoTrackerTheme {
        Surface {
            CoinListItemView(
                coinUiModel = coinUiModel,
                onClick = {}
            )
        }
    }
}