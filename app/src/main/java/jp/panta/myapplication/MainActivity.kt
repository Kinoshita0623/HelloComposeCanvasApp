package jp.panta.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import jp.panta.myapplication.ui.theme.HelloComposeCanvasAppTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.consumeAllChanges
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import kotlin.math.roundToInt

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            PaintInputTest(25.dp) {

                Text("hogehoge")
            }
        }
    }
}


@Composable
fun PaintInputTest(pointSize: Dp, block: @Composable BoxScope.()->Unit) {

    Box(modifier = Modifier.fillMaxSize()) {

        var offsetX by remember {
            mutableStateOf(0f)
        }
        var offsetY by remember {
            mutableStateOf(0f)
        }


        var offsetX2 by remember {
            mutableStateOf(100f)
        }

        var offsetY3 by remember {
            mutableStateOf(200f)
        }
        TransformableSample()


        block.invoke(this)

        Canvas(modifier = Modifier.matchParentSize()) {

            drawLine(
                color = Color.Blue,
                start = Offset(offsetX + pointSize.value + 3, offsetY + pointSize.value + 3),
                end = Offset(offsetX2 + pointSize.value + 3, offsetY + pointSize.value + 3),
                strokeWidth = 6f
            )

            drawLine(
                color = Color.Blue,
                start = Offset(offsetX + pointSize.value + 3, offsetY + pointSize.value + 3),
                end = Offset(offsetX + pointSize.value + 3, offsetY3 + pointSize.value + 3),
                strokeWidth = 6f
            )

            drawLine(
                color = Color.Blue,
                start = Offset(offsetX2 + pointSize.value + 3, offsetY + pointSize.value + 3),
                end = Offset(offsetX2 + pointSize.value + 3, offsetY3 + pointSize.value + 3),
                strokeWidth = 6f
            )

            drawLine(
                color = Color.Blue,
                start = Offset(offsetX + pointSize.value + 3, offsetY3 + pointSize.value + 3),
                end = Offset(offsetX2 + pointSize.value + 3, offsetY3 + pointSize.value + 3),
                strokeWidth = 6f
            )
        }

        CirclePoint(
            Modifier
                .offset {
                    IntOffset(offsetX.roundToInt(), offsetY.roundToInt())
                }
                .size(pointSize)
                .pointerInput(Unit) {
                    detectDragGestures { change, dragAmount ->
                        change.consumeAllChanges()
                        if (offsetX + 100 + dragAmount.x < offsetX2) {
                            offsetX += dragAmount.x
                        }
                        if (offsetY + dragAmount.y + 100 < offsetY3) {
                            offsetY += dragAmount.y
                        }

                    }
                }

        )

        CirclePoint(
            Modifier
                .offset {
                    IntOffset(offsetX2.roundToInt(), offsetY.roundToInt())
                }
                .size(pointSize)
                .pointerInput(Unit) {
                    detectDragGestures { change, dragAmount ->
                        change.consumeAllChanges()

                        if (offsetX2 + dragAmount.x > offsetX + 100) {
                            offsetX2 += dragAmount.x

                        }
                        if (offsetY + dragAmount.y + 100 < offsetY3) {
                            offsetY += dragAmount.y
                        }

                    }
                }

        )

        CirclePoint(
            Modifier
                .offset {
                    IntOffset(offsetX2.roundToInt(), offsetY3.roundToInt())
                }
                .size(pointSize)
                .pointerInput(Unit) {
                    detectDragGestures { change, dragAmount ->
                        change.consumeAllChanges()
                        if (offsetX2 + dragAmount.x > offsetX + 100) {
                            offsetX2 += dragAmount.x
                        }
                        if (offsetY3 + dragAmount.y > offsetY + 100) {
                            offsetY3 += dragAmount.y
                        }
                    }
                }

        )

        CirclePoint(
            Modifier
                .offset {
                    IntOffset(offsetX.roundToInt(), offsetY3.roundToInt())
                }
                .size(pointSize)
                .pointerInput(Unit) {
                    detectDragGestures { change, dragAmount ->
                        change.consumeAllChanges()
                        if (offsetX + dragAmount.x + 100 < offsetX2) {
                            offsetX += dragAmount.x
                        }
                        if (offsetY3 + dragAmount.y > offsetY + 100) {

                            offsetY3 += dragAmount.y
                        }
                    }
                }
        )



    }
}

@Composable
fun CirclePoint(modifier: Modifier) {
    Box(modifier) {
        Box(
            modifier = Modifier
                .matchParentSize()
                .clip(CircleShape)
                .background(Color.Red)
        )
    }

}

@Composable
fun TransformableSample() {
    // set up all transformation states
    var zoom by remember { mutableStateOf(1f) }
    var offsetX by remember { mutableStateOf(0f) }
    var offsetY by remember { mutableStateOf(0f) }

    Box(
        Modifier.fillMaxSize()
    ) {
        Box(
            Modifier
                .offset { IntOffset(offsetX.roundToInt(), offsetY.roundToInt()) }
                .graphicsLayer(
                    scaleX = zoom,
                    scaleY = zoom,
                )
                .background(Color.Green)
                .pointerInput(Unit) {
                    detectTransformGestures(
                        onGesture = { _, pan, gestureZoom, _ ->
                            zoom *= gestureZoom
                            offsetX += (pan.x * zoom)
                            offsetY += (pan.y * zoom)
                        }
                    )
                }
                .size(100.dp)
        )
    }

}