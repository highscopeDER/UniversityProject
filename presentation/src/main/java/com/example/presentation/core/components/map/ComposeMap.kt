package com.example.presentation.core.components.map

import android.graphics.PointF
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.Matrix
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.VectorPainter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.toSize
import com.example.presentation.R
import com.example.presentation.core.components.map.models.ClickableAreasList
import kotlin.math.abs

@Composable
fun VectorComposeMap(
    modifier: Modifier = Modifier,
    img: ImageVector,
    clickableAreasList: ClickableAreasList
) {

    val screenSize = LocalWindowInfo.current.containerSize.toSize()

    val matrixSaver: Saver<Matrix, Any> = listSaver<Matrix, Float>(
        save = { it.values.toList() },
        restore = { Matrix(it.toFloatArray()) }
    )

    val offsetSaver: Saver<MutableState<Offset>, Any> = listSaver<MutableState<Offset>, Float>(
        save = { listOf(it.value.x, it.value.y) },
        restore = { mutableStateOf(Offset(it.first(), it.last())) }
    )

    val floorMap: VectorPainter = rememberVectorPainter(img)
    val floorCenter: Offset = floorMap.intrinsicSize.center

    val minScale = 0.5f
    val maxScale = 4f
    val initialTranslation = -floorCenter + screenSize.center

    var translation: Offset by rememberSaveable(saver = offsetSaver) {
        mutableStateOf(
            initialTranslation
        )
    }
    var rotation: Float by rememberSaveable { mutableFloatStateOf(0f) }
    var scale: Float by rememberSaveable { mutableFloatStateOf(minScale) }

    val composeMatrix = rememberSaveable(saver = matrixSaver) { Matrix() }

    val state =
        rememberTransformableState { zoomChange: Float, panChange: Offset, rotationChange: Float ->

            val rightBottomTranslation = initialTranslation + floorCenter * scale
            val leftTopTranslation = initialTranslation - floorCenter * scale

            val newTrans = translation + panChange

            translation = newTrans.adjustWithEdges(leftTopTranslation, rightBottomTranslation)

            if (scale * zoomChange in minScale..maxScale)
                scale *= zoomChange

            rotation += rotationChange * 0.7f

        }

    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .transformable(state = state)
            .pointerInput(1) {
                detectTapGestures {

                    composeMatrix.invert()
                    val objectOffset = composeMatrix.map(it)
                    println("object ---> x: ${objectOffset.x}")
                    println("object ---> y: ${objectOffset.y}")
                    composeMatrix.invert()
                    clickableAreasList.checkClick(objectOffset)
                }
            }
    ) {

        composeMatrix.resetToPivotedTransform(
            pivotX = floorCenter.x,
            pivotY = floorCenter.y,
            translationX = translation.x,
            translationY = translation.y,
            rotationZ = rotation,
            scaleX = scale,
            scaleY = scale,
        )

        withTransform({ transform(composeMatrix) }) {
            with(floorMap) {
                draw(floorMap.intrinsicSize)
            }
            with(clickableAreasList) {
                draw()
            }
        }

    }

}


@Preview
@Composable
fun MapPreview() {

    VectorComposeMap(
        img = ImageVector.vectorResource(R.drawable.a1),
        clickableAreasList = ClickableAreasList()
    )

}

fun Offset.toPointF(): PointF = PointF(this.x, this.y)

fun Offset.adjustWithEdges(leftTop: Offset, rightBottom: Offset): Offset {

    val xIsValid = this.x in leftTop.x..rightBottom.x
    val yIsValid = this.y in leftTop.y..rightBottom.y

    return when {
        xIsValid && yIsValid -> this

        xIsValid && !yIsValid -> this.copy(
            y = if (
                abs(this.y - leftTop.y) < abs(this.y - rightBottom.y)
            ) leftTop.y else rightBottom.y
        )

        !xIsValid && yIsValid -> this.copy(
            x = if (
                abs(this.x - leftTop.x) < abs(this.x - rightBottom.x)
            ) leftTop.x else rightBottom.x
        )

        else -> Offset(
            x = if (
                abs(this.x - leftTop.x) < abs(this.x - rightBottom.x)
            ) leftTop.x else rightBottom.x,
            y = if (
                abs(this.y - leftTop.y) < abs(this.y - rightBottom.y)
            ) leftTop.y else rightBottom.y
        )
    }


}