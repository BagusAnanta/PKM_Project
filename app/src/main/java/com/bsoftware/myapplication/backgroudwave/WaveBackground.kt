package com.bsoftware.myapplication.backgroudwave

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.tooling.preview.Preview
import com.bsoftware.myapplication.backgroudwave.ui.theme.MyApplicationTheme

class WaveBackground : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                   WaveBackgroundFunction(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun WaveBackgroundFunction(modifier : Modifier = Modifier) {
    Canvas(modifier = Modifier.fillMaxSize()) {
        val width = size.width
        val height = size.height

        // Wave di bagian atas
        val topWavePath = Path().apply {
            moveTo(0f, height * 0.1f) // Start dari sisi kiri layar
            cubicTo(
                width * 0.28f, height * 0.08f,
                width * 0.80f, height * 0.20f,
                width, height * 0.2f
            )
            lineTo(width, 0f) // Garis dari kanan ke atas layar
            lineTo(0f, 0f) // Garis dari kiri ke atas layar
            close() // Tutup path
        }

        drawPath(
            path = topWavePath,
            color = Color.Magenta,
            style = Fill
        )

        // Wave di bagian bawah
        val bottomWavePath = Path().apply {
            moveTo(0f, height * 0.9f)
            cubicTo(
                width * 0.25f, height * 0.95f,
                width * 0.75f, height * 0.85f,
                width, height * 0.8f
            )
            lineTo(width, height)
            lineTo(0f, height)
            close()
        }

        drawPath(
            path = bottomWavePath,
            color = Color.Blue,
            style = Fill
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview2() {
    MyApplicationTheme {
        WaveBackgroundFunction()
    }
}