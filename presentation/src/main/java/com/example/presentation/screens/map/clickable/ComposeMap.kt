package com.example.presentation.screens.map.clickable

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
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.presentation.R

@Composable
fun VectorComposeMap(
    modifier: Modifier = Modifier,
    img: ImageVector,
    clickableAreasList: ClickableAreasList
) {

    val matrixSaver: Saver<Matrix, Any> = listSaver<Matrix, Float>(
        save = { it.values.toList() },
        restore = { Matrix(it.toFloatArray()) }
    )

    val offsetSaver: Saver<MutableState<Offset>, Any> = listSaver<MutableState<Offset>, Float>(
        save = { listOf(it.value.x, it.value.y) },
        restore = { mutableStateOf(Offset(it.first(), it.last())) }
    )


    val floorMap = rememberVectorPainter(img)
    val floorCenter = floorMap.intrinsicSize.center

    var translation: Offset by rememberSaveable(saver = offsetSaver) { mutableStateOf(Offset.Zero) }
    var rotation: Float by rememberSaveable { mutableFloatStateOf(0f) }
    var scale: Float by rememberSaveable { mutableFloatStateOf(1f) }

    val composeMatrix = rememberSaveable(saver = matrixSaver) { Matrix() }

    val state =
        rememberTransformableState { zoomChange: Float, panChange: Offset, rotationChange: Float ->

            translation += panChange
            scale *= zoomChange
            rotation += rotationChange

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
                    clickableAreasList.checkClick(objectOffset.toPointF())
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
            with(clickableAreasList){
                draw(drawContext.canvas.nativeCanvas)
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

