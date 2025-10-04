package com.oguzhanaslann.compose

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.foundation.text.input.InputTransformation.Companion.transformInput
import androidx.compose.foundation.text.input.TextFieldBuffer
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextObfuscationMode
import androidx.compose.foundation.text.input.delete
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedSecureTextField
import androidx.compose.material3.SecureTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.autofill.ContentType
import androidx.compose.ui.platform.LocalAutofillManager
import androidx.compose.ui.semantics.contentType
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.oguzhanaslann.compose.ui.OtpTextField
import com.oguzhanaslann.compose.ui.RichContentTextField
import com.oguzhanaslann.compose.ui.theme.ComposeTextInputTheme
import kotlin.math.max
import kotlin.math.min

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ComposeTextInputTheme {
                MainScreen()
            }
        }
    }
}

@Composable
fun MainScreen() {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(state = scrollState),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        LegacyField()
        NewStateField()
        TransformationsField()
        PasswordField()
        OutlinedPasswordField()
        AssistiveTextField()
        AutofillTextField()
        TextObfuscationTextField()
        OtpTextField()
        RichContentTextField()
        AutoResizingTextField()
    }
}

@Composable
private fun LegacyField() {
    // not deprecated. (yet)
    val textState = remember { mutableStateOf("") }
    TextField(
        value = textState.value,
        onValueChange = { textState.value = it },
        placeholder = { Text("Legacy Field") }
    )
}

@Composable
private fun NewStateField() {
    // added in material 3 version 1.4.0
    val user = rememberTextFieldState()
    TextField(
        state = user,
        placeholder = { Text("New State Field") }
    )
}


@Composable
fun TransformationsField() {
    val user = rememberTextFieldState()
    TextField(
        state = user,
        inputTransformation = { filterAstrixReverting() },
        outputTransformation = { transformToUppercase() },
        lineLimits = TextFieldLineLimits.SingleLine,
        placeholder = { Text("Transformations Field") },
    )
}

private fun TextFieldBuffer.filterAstrixReverting() {
    val text = originalText.toString()
    val indicesOfAstrix = text.indexOf('*')
    if (indicesOfAstrix != -1) {
        delete(
            start = max(0, indicesOfAstrix),
            end = min(indicesOfAstrix + 1,length)
        )
    }
}

private fun TextFieldBuffer.transformToUppercase() {
    val text = originalText.toString()
    replace(0, text.length, text.uppercase())
}


@Composable
private fun PasswordField() {
    val secureState = rememberTextFieldState()
    SecureTextField(
        state = secureState,
        placeholder = { Text("Password Field") }
    )
}

@Composable
private fun OutlinedPasswordField() {
    val secureState = rememberTextFieldState()
    OutlinedSecureTextField(
        state = secureState,
        placeholder = { Text("Password Field") }
    )
}


@Composable
private fun AssistiveTextField() {
    // added in material 3 version 1.4.0
    val user = rememberTextFieldState()
    TextField(
        state = user,
        placeholder = { Text("Assistive State Field") },
        supportingText = { Text("This is supporting text") },
        isError = user.text.length > 3
    )
}

@Composable
private fun AutofillTextField() {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        val autofill = LocalAutofillManager.current
        val user = rememberTextFieldState()
        TextField(
            state = user,
            modifier = Modifier
                .weight(0.5f)
                .semantics {
                    contentType = ContentType.EmailAddress
                },
            placeholder = { Text("Autofill Field") }
        )

        Spacer(modifier = Modifier.padding(4.dp))

        Button(
            modifier = Modifier.weight(0.5f),
            onClick = { autofill?.commit() }
        ) {
            Text("Autofill")
        }
    }
}

@Composable
private fun TextObfuscationTextField() {
    var isObfuscated by remember { mutableStateOf(false) }
    val user = rememberTextFieldState()
    SecureTextField(
        state = user,
        placeholder = { Text("Text Obfuscation Field") },
        trailingIcon = {
            IconButton(onClick = { isObfuscated = !isObfuscated }) {
                Icon(
                    imageVector = if (isObfuscated) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                    contentDescription = if (isObfuscated) "Hide password" else "Show password"
                )
            }
        },
        textObfuscationMode = if (isObfuscated) TextObfuscationMode.Visible else TextObfuscationMode.Hidden
    )
}

@Composable
fun AutoResizingTextField() {
    val state = rememberTextFieldState("Auto Resizing Field")
    BasicTextField(
        state = state,
        modifier = Modifier.size(120.dp, 64.dp), // not a best practise 
        decorator = { _ ->
            BasicText(
                text = state.text.toString(),
                style = TextStyle(fontSize = 24.sp),
                autoSize = TextAutoSize.StepBased()
            )
        }
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ComposeTextInputTheme {
        MainScreen()
    }
}
