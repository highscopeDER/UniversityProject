package com.example.presentation.core.components.routeEdgesPicker

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
        modifier = modifier.background(Gray40).padding(top = 16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {

        PickEdgeButton(
            Modifier.fillMaxHeight(0.3f).aspectRatio(3f),
            "Откуда",
            null,
            {},
            {}
        )

        PickEdgeButton(
            Modifier.fillMaxHeight(0.3f).aspectRatio(3f),
            "Куда",
            null,
            {},
            {}
        )
        
        Button(
            modifier = Modifier.fillMaxHeight(0.3f).aspectRatio(1f),
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
                    .fillMaxHeight(0.2f),
            )

        }

    }
}
