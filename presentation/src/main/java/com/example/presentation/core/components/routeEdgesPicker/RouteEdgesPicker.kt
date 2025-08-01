package com.example.presentation.core.components.routeEdgesPicker

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.presentation.core.theme.Gray40
import com.example.presentation.core.theme.UniversityProjectTheme

@Composable
fun RouteEdgesPicker(
    modifier: Modifier = Modifier
){

    //TODO(): need to hoist it's state

    Row(
        modifier = modifier
            .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
            .background(Gray40)
            .padding(top = 24.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {

        PickEdgeButton(
            modifier = Modifier.size(160.dp, 40.dp),
            hint ="Откуда",
            selectedEdge = null,
            onClear = {},
            onClick = {}
        )

        PickEdgeButton(
            modifier = Modifier.size(160.dp, 40.dp),
            hint ="Куда",
            selectedEdge = null,
            onClear = {},
            onClick = {}
        )
        
        Button(
            modifier = Modifier.size(40.dp, 40.dp),
            onClick = {}
        ) {

        }

    }

}

@Preview
@Composable
fun RouteEdgesPickerPreview(){
    UniversityProjectTheme {

        Box(
            Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomCenter
        ) {

            RouteEdgesPicker(
                Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.15f),
            )

        }

    }
}
