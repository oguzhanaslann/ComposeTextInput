package com.oguzhanaslann.compose.autofill

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.autofill.ContentType
import androidx.compose.ui.platform.LocalAutofillManager
import androidx.compose.ui.semantics.contentType
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.oguzhanaslann.compose.ui.theme.ComposeTextInputTheme

@Composable
fun AutofillTextField() {
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
                    contentType = ContentType.NewUsername
                },
            placeholder = { Text("Autofill Field") },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Text
            )
        )

        Spacer(modifier = Modifier.padding(4.dp))

        Button(
            modifier = Modifier.weight(0.5f),
            onClick = {
                Log.d("TAG", "AutofillTextField: $autofill")
                autofill?.commit()
            }
        ) {
            Text("Autofill")
        }
    }
}

@Composable
fun ExplicitCredentialSaveView(modifier: Modifier = Modifier) {
    val autofillManager = LocalAutofillManager.current
    val textFieldValue = remember { mutableStateOf<String>("") }
    Column {
        TextField(
            value = textFieldValue.value,
            onValueChange = { textFieldValue.value = it },
            placeholder = {
                Text("Username")
            },
            modifier = Modifier.semantics { contentType = ContentType.NewUsername }
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = textFieldValue.value,
            onValueChange = { textFieldValue.value = it },
            modifier = Modifier.semantics { contentType = ContentType.NewPassword }
        )
    }
}

@Preview
@Composable
private fun previewAutofillTextField() {
    ComposeTextInputTheme {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            AutofillTextField()
            Spacer(modifier = Modifier.size(12.dp))
            ExplicitCredentialSaveView()
        }
    }
}