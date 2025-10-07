package com.oguzhanaslann.compose.autofill

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.autofill.ContentType
import androidx.compose.ui.platform.LocalAutofillManager
import androidx.compose.ui.semantics.contentType
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

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
                    contentType = ContentType.EmailAddress
                },
            placeholder = { Text("Autofill Field") },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Email
            )
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