package com.roman.cryptotracker.crypto.presentation.coin.details.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.roman.cryptotracker.R
import com.roman.cryptotracker.ui.theme.CryptoTrackerTheme

@Composable
fun CoinDetailInfoCardView(
    title: String,
    formattedText: String,
    icon: ImageVector,
    modifier: Modifier = Modifier,
    iconTint: Color = MaterialTheme.colorScheme.primary,
) {
    Card(
        modifier = modifier
            .padding(8.dp)
            .shadow(
                elevation = 15.dp,
                shape = RectangleShape,
                ambientColor = MaterialTheme.colorScheme.primary,
                spotColor = MaterialTheme.colorScheme.primary,
            ),
        shape = RectangleShape,
        border = BorderStroke(
            width = 1.dp,
            color = MaterialTheme.colorScheme.primary
        ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer,
        )
    ) {
        AnimatedContent(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            targetState = icon,
            label = "Animation Coin Detail Info Card Icon"
        ) { icon ->
            Icon(
                modifier = Modifier
                    .size(75.dp)
                    .padding(top = 16.dp),
                imageVector = icon,
                tint = iconTint,
                contentDescription = null
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        AnimatedContent(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            targetState = formattedText,
            label = "Animation Coin Detail Info Card FormattedText"
        ) { formattedText ->
            Text(
                modifier = Modifier.padding(horizontal = 16.dp),
                text = formattedText,
                style = LocalTextStyle.current.copy(
                    textAlign = TextAlign.Center,
                    fontSize = 18.sp,
                )
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 16.dp)
                .padding(horizontal = 16.dp),
            text = title,
            textAlign = TextAlign.Center,
            fontSize = 12.sp,
            fontWeight = FontWeight.Light,
        )
    }
}

@PreviewLightDark
@Composable
fun CoinDetailInfoCardPreview() {
    CryptoTrackerTheme {
        Surface {
            CoinDetailInfoCardView(
                title = "Price",
                formattedText = "$ 75,157.44",
                icon = ImageVector.vectorResource(id = R.drawable.dollar)
            )
        }
    }
}