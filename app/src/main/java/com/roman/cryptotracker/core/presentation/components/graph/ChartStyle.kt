package com.roman.cryptotracker.core.presentation.components.graph

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit

data class ChartStyle(
    val chartLineColor: Color,
    val unSelectedColor: Color,
    val selectedColor: Color,
    val helperLinesThicknessPx: Float,
    val axisLineThicknessPx: Float,
    val labelFontSize: TextUnit,
    val minYLabelSpacing: Dp,
    val minXLabelSpacing: Dp,
    val verticalPadding: Dp,
    val horizontalPadding: Dp,
)
