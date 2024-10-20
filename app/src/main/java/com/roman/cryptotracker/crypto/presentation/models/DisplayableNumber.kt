package com.roman.cryptotracker.crypto.presentation.models

import java.text.NumberFormat
import java.util.Locale

data class DisplayableNumber(
    val value: Double,
    val formattedValue: String,
)

fun Double.toDisplayableNumber(): DisplayableNumber {
    val formatter = NumberFormat.getNumberInstance(Locale.getDefault()).apply {
        minimumFractionDigits = 2
        maximumFractionDigits = 2
    }
    return DisplayableNumber(
        value = this,
        formattedValue = formatter.format(this)
    )
}