package com.example.presentation.screens.map.clickable

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.Matrix
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.drawscope.translate
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

    val composeMatrix = Matrix()

    val floorMap = rememberVectorPainter(img)
    val floorCenter = floorMap.intrinsicSize.center

    var zoom by rememberSaveable { mutableStateOf(1f) }
    var translationX by rememberSaveable { mutableStateOf(0f) }
    var translationY by rememberSaveable { mutableStateOf(0f) }
    var rotation by rememberSaveable { mutableStateOf(0f) }

    val state =
        rememberTransformableState { zoomChange: Float, panChange: Offset, rotationChange: Float ->
            zoom *= zoomChange
            translationX += panChange.x
            translationY += panChange.y
            rotation += rotationChange

            composeMatrix.reset()
            composeMatrix.translate(translationX, translationY)
            composeMatrix.scale(zoom)

        }

    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .transformable(state = state)
            .pointerInput(1) {
                detectTapGestures {
                    composeMatrix.invert()
                    val objectOffset = composeMatrix.map(it)
                    composeMatrix.invert()
                    println("tap detected:")
                    println("screen ---> x: ${it.x}")
                    println("screen ---> y: ${it.y}")
                    println("object ---> x: ${objectOffset.x}")
                    println("object ---> y: ${objectOffset.y}")
                    //TODO here i can get touch coordinates
                }
            }
    ) {
        scale(zoom, zoom) {
            translate(
                translationX,
                translationY
            ) {



                drawIntoCanvas { canvas ->
                    canvas.save()
                    canvas.rotate(
                        rotation,
                        floorCenter.x,
                        floorCenter.y
                    )

                    with(floorMap) {
                        draw(floorMap.intrinsicSize)
                    }
                    canvas.restore()
                }

            }
        }

    }

}

@Preview
@Composable
fun MapPreview() {


    VectorComposeMap(img = ImageVector.vectorResource(R.drawable.a1))

}