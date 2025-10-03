package com.oguzhanaslann.compose.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.border
import androidx.compose.foundation.content.contentReceiver
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.foundation.text.input.setTextAndPlaceCursorAtEnd
import androidx.compose.material3.MaterialExpressiveTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.oguzhanaslann.compose.ui.theme.ComposeTextInputTheme

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun RichContentTextField(
    richContentViewModel: RichContentViewModel = viewModel()
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, color = androidx.compose.ui.graphics.Color.Black)
            .contentReceiver(richContentViewModel::handleContent)
    ) {
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            items(richContentViewModel.selectedImages) { imageUri ->
                AsyncImage(
                    model = imageUri,
                    contentDescription = null,
                    modifier = Modifier
                        .size(64.dp)
                        .padding(end = 4.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop
                )
            }
        }
        val user = rememberTextFieldState()
        LaunchedEffect(richContentViewModel.selectedImages) {
            user.setTextAndPlaceCursorAtEnd(richContentViewModel.selectedImages.size.toString())
        }
        TextField(
            state = user,
            placeholder = { Text("Rich Content Field") },
        )

    }
}

@Preview
@Composable
private fun previewRichContentTextField() {
    ComposeTextInputTheme {
        RichContentTextField()
    }

}
