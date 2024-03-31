package com.bsoftware.myapplication.dialogalert

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.bsoftware.myapplication.dialogalert.ui.theme.MyApplicationTheme

class CustomDialog : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AlertDialogCustome()
                }
            }
        }
    }
}

@Composable
fun AlertDialogCustome(
    title : String = "Warning",
    message : String = "Example Message In Here",
    onDismiss : (Boolean) -> Unit = {},
    onAgreeClickButton : () -> Unit = {},
    modifier: Modifier = Modifier,
    colorBackgroundDialog : Color = Color.White,
    colorTitleDialog : Color = Color.Black,
    colorMessageDialog : Color = Color.Black,
    colorButtonDialog : Color = Color.Black
) {
    Dialog(onDismissRequest = {onDismiss(false)}) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = colorBackgroundDialog
        ) {
            Box(contentAlignment = Alignment.Center) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text(
                        text = title,
                        color = colorTitleDialog,
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentWidth(Alignment.CenterHorizontally),
                        style = TextStyle(
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )

                    Spacer(modifier = Modifier.padding(top = 15.dp))
                    
                    Text(
                        text = message,
                        color = Color.Black,
                        style = TextStyle(
                            fontSize = 15.sp
                        )
                    )
                    
                    Spacer(modifier = Modifier.padding(top = 15.dp))
                    
                    Row {
                        ClickableText(
                            text = AnnotatedString("Cancel"),
                            onClick = {onDismiss(true)}
                        )

                        ClickableText(
                            text = AnnotatedString("Ok"),
                            onClick = {
                                onAgreeClickButton()
                                onDismiss(true)
                            }
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CustomDialogPreview() {
    MyApplicationTheme {
        AlertDialogCustome()
    }
}