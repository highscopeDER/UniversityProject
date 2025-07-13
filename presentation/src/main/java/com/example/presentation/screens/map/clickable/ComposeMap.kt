package com.example.presentation.screens.map.clickable

import android.graphics.drawable.VectorDrawable
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.currentCompositionLocalContext
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.drawscope.draw
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.scaleMatrix
import com.example.presentation.R
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun VectorComposeMap(
    modifier: Modifier = Modifier,
    img: ImageVector
) {

    val floorMap = rememberVectorPainter(img)

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
        }

    var floorCenter = floorMap.intrinsicSize.center

    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .transformable(state = state)
            .pointerInput(1) {
                detectTapGestures {
                    //TODO here i can get touch coordinates
                }
            }
    ) {
        with(floorMap) {
            withTransform(
                {
                    rotate(rotation)
                    scale(zoom, zoom)
                    translate(
                        translationX,
                        translationY
                    )

                }
            ) {
                draw(floorMap.intrinsicSize)
            }
        }
    }

}

@Preview
@Composable
fun MapPreview() {


    VectorComposeMap(img = ImageVector.vectorResource(R.drawable.a1))

}