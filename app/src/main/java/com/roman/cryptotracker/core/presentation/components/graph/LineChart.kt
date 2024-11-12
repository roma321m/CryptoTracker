package com.roman.cryptotracker.core.presentation.components.graph

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.roman.cryptotracker.crypto.domain.CoinPrice
import com.roman.cryptotracker.ui.theme.CryptoTrackerTheme
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import kotlin.math.roundToInt
import kotlin.random.Random

@Composable
fun LineChart(
    dataPoints: List<DataPoint>,
    style: ChartStyle,
    visibleDataPointIndices: IntRange,
    unit: String,
    modifier: Modifier = Modifier,
    selectedDataPoint: DataPoint? = null,
    onSelectedDataPointChange: (DataPoint) -> Unit = {},
    onXLabelWidthChange: (Float) -> Unit = {},
    showHelperLines: Boolean = true
) {
    val textStyle = LocalTextStyle.current.copy(
        fontSize = style.labelFontSize
    )

    val visibleDataPoints = remember(dataPoints, visibleDataPointIndices) {
        dataPoints.slice(visibleDataPointIndices)
    }

    val maxYValue = remember(visibleDataPoints) {
        visibleDataPoints.maxOfOrNull { it.y } ?: 0f
    }
    val minYValue = remember(visibleDataPoints) {
        visibleDataPoints.minOfOrNull { it.y } ?: 0f
    }

    val measurer = rememberTextMeasurer()

    var xLabelWidth by remember { mutableFloatStateOf(0f) }
    LaunchedEffect(xLabelWidth) {
        onXLabelWidthChange(xLabelWidth)
    }

    val selectedDataPointIndex = remember(selectedDataPoint) {
        dataPoints.indexOf(selectedDataPoint)
    }
    var drawPoints by remember {
        mutableStateOf(listOf<DataPoint>())
    }
    var isShowingDataPoints by remember {
        mutableStateOf(selectedDataPoint != null)
    }

    Canvas(
        modifier = modifier
            .fillMaxSize()
            .pointerInput(drawPoints, xLabelWidth) {
                detectHorizontalDragGestures { change, _ ->
                    val newSelectedDataPointIndex = getSelectedDataPointIndex(
                        touchOffsetX = change.position.x,
                        triggerWidth = xLabelWidth,
                        drawPoints = drawPoints
                    )
                    isShowingDataPoints =
                        (newSelectedDataPointIndex + visibleDataPointIndices.first) in visibleDataPointIndices
                    if (isShowingDataPoints) {
                        onSelectedDataPointChange(dataPoints[newSelectedDataPointIndex])
                    }
                }
            }
    ) {
        val minLabelSpacingYPx = style.minYLabelSpacing.toPx()
        val minLabelSpacingXPx = style.minXLabelSpacing.toPx()
        val verticalPaddingPx = style.verticalPadding.toPx()
        val horizontalPaddingPx = style.horizontalPadding.toPx()


        // X labels calculations
        val xLabelTextLayoutResult = visibleDataPoints.map {
            measurer.measure(
                text = it.xLabel,
                style = textStyle.copy(textAlign = TextAlign.Center)
            )
        }
        val xMaxLabelWidth = xLabelTextLayoutResult.maxOfOrNull { it.size.width } ?: 0
        val xMaxLabelHeight = xLabelTextLayoutResult.maxOfOrNull { it.size.height } ?: 0
        val xMaxLabelLineCount = xLabelTextLayoutResult.maxOfOrNull { it.lineCount } ?: 0
        val xLabelLineHeight = if (xMaxLabelLineCount != 0) {
            xMaxLabelHeight / xMaxLabelLineCount
        } else {
            0
        }


        // Viewport height based on X labels calculations
        val viewPortHeightPx = size.height -
                (xMaxLabelHeight + 2 * verticalPaddingPx + xLabelLineHeight + minLabelSpacingXPx)


        // Y labels calculations
        val labelViewPortHeightPx = viewPortHeightPx + xLabelLineHeight
        val labelCount = (labelViewPortHeightPx / (xLabelLineHeight + minLabelSpacingYPx)).toInt()
        val valueIncrement = (maxYValue - minYValue) / labelCount
        val yLabels = (0..labelCount).map {
            ValueLabel(
                value = maxYValue - (valueIncrement * it),
                unit = unit
            )
        }
        val yLabelTextLayoutResult = yLabels.map {
            measurer.measure(
                text = it.formatted(),
                style = textStyle
            )
        }
        val maxYLabelWidth = yLabelTextLayoutResult.maxOfOrNull { it.size.width } ?: 0

        // Viewport calculations
        val viewPortTopY = verticalPaddingPx + xLabelLineHeight + 10f
        val viewPortRightX = size.width - horizontalPaddingPx
        val viewPortBottomY = viewPortTopY + viewPortHeightPx
        val viewPortLeftX = horizontalPaddingPx * 2f + maxYLabelWidth


        xLabelWidth = xMaxLabelWidth + minLabelSpacingXPx
        xLabelTextLayoutResult.forEachIndexed { index, result ->
            val x = viewPortLeftX + minLabelSpacingXPx + xLabelWidth * index

            drawText(
                textLayoutResult = result,
                topLeft = Offset(
                    x = x,
                    y = viewPortBottomY + minLabelSpacingXPx
                ),
                color = if (index == selectedDataPointIndex) {
                    style.selectedColor
                } else {
                    style.unSelectedColor
                }
            )

            if (showHelperLines) {
                drawLine(
                    color = if (index == selectedDataPointIndex) {
                        style.selectedColor
                    } else {
                        style.unSelectedColor
                    },
                    start = Offset(
                        x = x + result.size.width / 2f,
                        y = viewPortBottomY
                    ),
                    end = Offset(
                        x = x + result.size.width / 2f,
                        y = viewPortTopY
                    ),
                    strokeWidth = if (index == selectedDataPointIndex) {
                        style.helperLinesThicknessPx * 1.5f
                    } else {
                        style.helperLinesThicknessPx
                    }
                )
            }

            if (selectedDataPointIndex == index) {
                val valueLabel = ValueLabel(
                    value = visibleDataPoints[index].y,
                    unit = unit
                )
                val valueLabelTextLayoutResult = measurer.measure(
                    text = valueLabel.formatted(),
                    style = textStyle.copy(
                        color = style.selectedColor
                    ),
                    maxLines = 1
                )
                val textPositionX = if (selectedDataPointIndex == visibleDataPointIndices.last) {
                    x - result.size.width
                } else {
                    x - result.size.width / 2f
                } + result.size.width / 2f
                val isTextInVisibleRange =
                    (size.width - textPositionX).roundToInt() in 0..size.width.roundToInt()
                if (isTextInVisibleRange) {
                    drawText(
                        textLayoutResult = valueLabelTextLayoutResult,
                        topLeft = Offset(
                            x = textPositionX,
                            y = viewPortTopY - valueLabelTextLayoutResult.size.height - 10f
                        )
                    )
                }
            }
        }

        val heightRequiredForLabels = xLabelLineHeight * (labelCount + 1)
        val remainingHeightForLabels = labelViewPortHeightPx - heightRequiredForLabels
        val spaceBetweenLabels = remainingHeightForLabels / labelCount

        yLabelTextLayoutResult.forEachIndexed { index, result ->
            val x = horizontalPaddingPx + maxYLabelWidth - result.size.width.toFloat()
            val y = viewPortTopY +
                    index * (xLabelLineHeight + spaceBetweenLabels) -
                    xLabelLineHeight / 2f
            drawText(
                textLayoutResult = result,
                topLeft = Offset(
                    x = x,
                    y = y
                ),
                color = style.unSelectedColor
            )

            if (showHelperLines) {
                drawLine(
                    color = style.unSelectedColor,
                    start = Offset(
                        x = viewPortLeftX,
                        y = y + result.size.height.toFloat() / 2f
                    ),
                    end = Offset(
                        x = viewPortRightX,
                        y = y + result.size.height.toFloat() / 2f
                    ),
                    strokeWidth = style.helperLinesThicknessPx
                )
            }
        }


        val conPoints1 = mutableListOf<DataPoint>()
        val conPoints2 = mutableListOf<DataPoint>()
        for (i in 1 until drawPoints.size) {
            val p0 = drawPoints[i - 1]
            val p1 = drawPoints[i]

            val x = (p1.x + p0.x) / 2f
            val y1 = p0.y
            val y2 = p1.y

            conPoints1.add(DataPoint(x, y1, ""))
            conPoints2.add(DataPoint(x, y2, ""))
        }

        val linePath = Path().apply {
            if (drawPoints.isNotEmpty()) {
                moveTo(drawPoints.first().x, drawPoints.first().y)

                for (i in 1 until drawPoints.size) {
                    cubicTo(
                        x1 = conPoints1[i - 1].x,
                        y1 = conPoints1[i - 1].y,
                        x2 = conPoints2[i - 1].x,
                        y2 = conPoints2[i - 1].y,
                        x3 = drawPoints[i].x,
                        y3 = drawPoints[i].y
                    )
                }
            }
        }
        drawPath(
            path = linePath,
            color = style.chartLineColor,
            style = Stroke(
                width = 3f
            )
        )

        drawPoints = visibleDataPointIndices.map {
            val x =
                viewPortLeftX + (it - visibleDataPointIndices.first) * xLabelWidth + xLabelWidth / 2f
            val ratio = (dataPoints[it].y - minYValue) / (maxYValue - minYValue)
            val y = viewPortBottomY - ratio * viewPortHeightPx
            DataPoint(
                x = x,
                y = y,
                xLabel = dataPoints[it].xLabel,
            )
        }

        if (isShowingDataPoints) {
            drawPoints.forEachIndexed { index, dataPoint ->
                val circleOffset = Offset(
                    x = dataPoint.x,
                    y = dataPoint.y
                )
                drawCircle(
                    color = style.unSelectedColor,
                    radius = 10f,
                    center = circleOffset
                )
                if (index == selectedDataPointIndex) {
                    drawCircle(
                        color = Color.White,
                        radius = 15f,
                        center = circleOffset
                    )
                    drawCircle(
                        color = style.selectedColor,
                        radius = 15f,
                        center = circleOffset,
                        style = Stroke(width = 3f)
                    )
                }
            }
        }
    }
}


private fun getSelectedDataPointIndex(
    touchOffsetX: Float,
    triggerWidth: Float,
    drawPoints: List<DataPoint>
): Int {
    val triggerRangeLeft = touchOffsetX - triggerWidth / 2f
    val triggerRangeRight = touchOffsetX + triggerWidth / 2f
    return drawPoints.indexOfFirst {
        it.x in triggerRangeLeft..triggerRangeRight
    }
}

@Preview(widthDp = 1000)
@Composable
fun LineChartPreview() {
    CryptoTrackerTheme {
        Surface {
            val randomized = remember {
                (1..20).map {
                    CoinPrice(
                        Random.nextFloat() * 1_000.0,
                        ZonedDateTime.now().plusHours(it.toLong())
                    )
                }
            }
            val chartStyle = ChartStyle(
                chartLineColor = Color.Black,
                unSelectedColor = Color(0xFF7C7C7C),
                selectedColor = Color.Red,
                helperLinesThicknessPx = 1f,
                axisLineThicknessPx = 1f,
                labelFontSize = 14.sp,
                minYLabelSpacing = 25.dp,
                minXLabelSpacing = 8.dp,
                verticalPadding = 8.dp,
                horizontalPadding = 8.dp,
            )
            val dataPoints = remember {
                randomized.map {
                    DataPoint(
                        x = it.dateTime.hour.toFloat(),
                        y = it.priceUsd.toFloat(),
                        xLabel = DateTimeFormatter.ofPattern("ha\nM/d").format(it.dateTime),
                    )
                }
            }
            LineChart(
                dataPoints = dataPoints,
                style = chartStyle,
                visibleDataPointIndices = 10..19,
                unit = "$",
                modifier = Modifier
                    .width(700.dp)
                    .height(300.dp),
                selectedDataPoint = dataPoints[1],
            )
        }
    }
}