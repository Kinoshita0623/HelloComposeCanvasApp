package jp.panta.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import jp.panta.myapplication.ui.theme.HelloComposeCanvasAppTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.consumeAllChanges
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import kotlin.math.roundToInt

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            PaintInputTest()
        }
    }
}


@Composable
fun PaintInputTest() {
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



        Box(
            Modifier
                .offset {
                    IntOffset(offsetX.roundToInt(), offsetY.roundToInt())
                }
                .background(Color.Blue)
                .size(50.dp)
                .pointerInput(Unit) {
                    detectDragGestures { change, dragAmount ->
                        change.consumeAllChanges()
                        if(offsetX + 100 + dragAmount.x < offsetX2) {
                            offsetX += dragAmount.x
                        }
                        if(offsetY + dragAmount.y + 100 < offsetY3) {
                            offsetY += dragAmount.y
                        }
                    }
                }
        )

        Box(
            Modifier
                .offset {
                    IntOffset(offsetX2.roundToInt(), offsetY.roundToInt())
                }
                .background(Color.Blue)
                .size(50.dp)
                .pointerInput(Unit) {
                    detectDragGestures { change, dragAmount ->
                        change.consumeAllChanges()

                        if(offsetX2 + dragAmount.x > offsetX + 100) {
                            offsetX2 += dragAmount.x

                        }
                        if(offsetY + dragAmount.y + 100 < offsetY3) {
                            offsetY += dragAmount.y
                        }

                    }
                }

        )

        Box(
            Modifier
                .offset {
                    IntOffset(offsetX2.roundToInt(), offsetY3.roundToInt())
                }
                .background(Color.Blue)
                .size(50.dp)
                .pointerInput(Unit) {
                    detectDragGestures { change, dragAmount ->
                        change.consumeAllChanges()
                        if(offsetX2 + dragAmount.x > offsetX + 100) {
                            offsetX2 += dragAmount.x
                        }
                        if(offsetY3 + dragAmount.y > offsetY + 100 ) {
                            offsetY3 += dragAmount.y
                        }
                    }
                }

        )

        Box(
            Modifier
                .offset {
                    IntOffset(offsetX.roundToInt(), offsetY3.roundToInt())
                }
                .background(Color.Blue)
                .size(50.dp)
                .pointerInput(Unit) {
                    detectDragGestures { change, dragAmount ->
                        change.consumeAllChanges()
                        if(offsetX + dragAmount.x + 100 < offsetX2) {
                            offsetX += dragAmount.x
                        }
                        if(offsetY3 + dragAmount.y  > offsetY + 100) {

                            offsetY3 += dragAmount.y
                        }
                    }
                }
        )

    }
}
@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    HelloComposeCanvasAppTheme {
        Greeting("Android")
    }
}