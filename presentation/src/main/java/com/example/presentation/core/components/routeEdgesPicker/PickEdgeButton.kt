package com.example.presentation.core.components.routeEdgesPicker

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.presentation.core.theme.Gray100
import com.example.presentation.core.theme.Gray20
import com.example.presentation.core.theme.UniversityProjectTheme

@Composable
fun PickEdgeButton(
    modifier: Modifier = Modifier,
    hint: String,
    selectedEdge: String? = null,
    onClear: () -> Unit,
    onClick: () -> Unit
) {

    Button(
        modifier = modifier.fillMaxSize(),
        shape = RoundedCornerShape(
            corner = CornerSize(16.dp)
        ),
        colors = ButtonColors(
            containerColor = Gray20,
            contentColor = Gray100,
            disabledContainerColor = Color.White,
            disabledContentColor = Color.White
        ),
        onClick = onClick
    ) {

        Row (
            modifier = Modifier.fillMaxSize(),
        ) {

           Spacer(
               Modifier
                   .fillMaxWidth(0.1f)
                   .aspectRatio(1f)
                   .align(Alignment.CenterVertically)
                   .background(
                       Gray100,
                       CircleShape
                   )
           )

            Spacer(Modifier
                .fillMaxHeight()
                .width(4.dp)
            )

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterVertically),
                text = selectedEdge ?: hint,
                textAlign = TextAlign.Start,

            )

            if (selectedEdge != null) {

                Button(
                    modifier = Modifier
                        .fillMaxHeight(0.5f)
                        .aspectRatio(1f)
                        .align(Alignment.CenterVertically)
                    ,
                    onClick = onClear
                ) {
                    Spacer(
                        Modifier
                            .fillMaxSize()
                            .background(color = Color.White)
                    )
                }

            }

        }

    }

}

@Preview
@Composable
fun PickEdgeButtonPreview() {
    UniversityProjectTheme {
        PickEdgeButton(
            Modifier.size(
                width = 240.dp,
                height = 120.dp
            ),
            "Откуда",
            null,
            {}
        ) {}
    }
}