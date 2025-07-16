package com.example.presentation.screens.map.clickable

import android.graphics.Matrix
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.rotate
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.presentation.R

@Composable
fun VectorComposeMap(
    modifier: Modifier = Modifier,
    img: ImageVector
) {

    val composeMatrix by remember { mutableStateOf(androidx.compose.ui.graphics.Matrix()) }

    val floorMap = rememberVectorPainter(img)
    val floorCenter = floorMap.intrinsicSize.center

    val viewMatrix: Matrix = remember { Matrix() }

    var redraw: Boolean by remember { mutableStateOf(true) }

    val state =
        rememberTransformableState { zoomChange: Float, panChange: Offset, rotationChange: Float ->

//            println(" --- state transformation start --- ")

//            println("view matrix in: $viewMatrix")
            viewMatrix.preTranslate(panChange.x, panChange.y)
            val rotationPivot = viewMatrix.mapPoint(floorCenter)
            viewMatrix.preRotate(rotationChange, rotationPivot.x, rotationPivot.y)
            viewMatrix.preScale(zoomChange, zoomChange, rotationPivot.x, rotationPivot.y)
//            println("view matrix out: $viewMatrix")

            redraw = true
//            println(" --- state transformation end --- ")

        }

    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .transformable(state = state)
            .pointerInput(1) {
                detectTapGestures {


                    println("tap detected:")
                    println("screen ---> x: ${it.x}")
                    println("screen ---> y: ${it.y}")

                    val inverted = Matrix()
                    if (viewMatrix.invert(inverted)) {
                        val objectOffset = inverted.mapPoint(it)
                        println("object ---> x: ${objectOffset.x}")
                        println("object ---> y: ${objectOffset.y}")
                    }
                    //TODO here i can get touch coordinates
                }
            }
    ) {
        redraw = false
        redraw = true
        if (!redraw) return@Canvas
        drawIntoCanvas { canvas ->
            canvas.save()
            canvas.nativeCanvas.concat(viewMatrix)

            with(floorMap) {
                draw(floorMap.intrinsicSize)
            }
            canvas.restore()
        }
    }

}


@Preview
@Composable
fun MapPreview() {


    VectorComposeMap(img = ImageVector.vectorResource(R.drawable.a1))

}

fun Matrix.mapPoint(p: Offset): Offset {
    val t = floatArrayOf(p.x, p.y)
    this.mapPoints(t)
    return Offset(t.first(), t.last())
}