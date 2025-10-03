package com.oguzhanaslann.compose.ui

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.InputTransformation
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.maxLength
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.autofill.ContentType
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.contentType
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.oguzhanaslann.compose.ui.theme.ComposeTextInputTheme
import com.oguzhanaslann.compose.ui.theme.SquircleShape

@Composable
fun OtpTextField() {
    Column {
        Text("OTP Field")
        Spacer(modifier = Modifier.height(8.dp))
        val state = rememberTextFieldState(initialText = "1")
        BasicTextField(
            modifier = Modifier.semantics {
                contentType = ContentType.SmsOtpCode // for autofill
            },
            state = state,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number
            ),
            lineLimits = TextFieldLineLimits.SingleLine,
            inputTransformation = InputTransformation.maxLength(6),
            decorator = { innerTextField ->
                val text = state.text
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    repeat(6) {
                        Digit(
                            number = text.getOrNull(it) ?: ' ',
                            showCursor = it == text.length
                        )
                    }
                }
            }
        )
    }
}

@Composable
fun Digit(
    number: Char,
    showCursor: Boolean
) {
    Box(
        Modifier
            .size(48.dp)
            .border(1.5.dp, Color.Gray, SquircleShape)
            .background(Color(0x72E91E63), SquircleShape)
    ) {
        Text(
            text = number.toString(),
            fontSize = 24.sp,
            modifier = Modifier.align(Alignment.Center)
        )

        if (showCursor) {
            val infiniteTransition = rememberInfiniteTransition(label = "Cursor Animation")
            val alpha by infiniteTransition.animateFloat(
                initialValue = 0f,
                targetValue = 1f,
                animationSpec = infiniteRepeatable(
                    animation = tween(500),
                    repeatMode = RepeatMode.Reverse
                ),
                label = "Cursor Alpha"
            )

            Column(
                modifier = Modifier.align(Alignment.BottomCenter)
            ) {
                Box(
                    Modifier
                        .width(16.dp)
                        .height(1.dp)
                        .background(Color.Black.copy(alpha = alpha))
                )
                Spacer(modifier = Modifier.padding(4.dp))
            }
        }
    }
}

@Preview
@Composable
private fun prevOtpField() {
    ComposeTextInputTheme {
        OtpTextField()
    }
}
