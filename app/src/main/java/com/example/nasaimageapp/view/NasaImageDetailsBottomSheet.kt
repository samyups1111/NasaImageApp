package com.example.nasaimageapp.view

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage

@OptIn(ExperimentalMaterial3Api::class, ExperimentalGlideComposeApi::class)
@Composable
fun NasaImageDetailsBottomSheet(
    title: String,
    url: String,
    photographer: String,
    location: String,
    description: String,
    onBack: () -> Unit,
) {
    ModalBottomSheet(
        onDismissRequest = onBack
    ) {
        Text(text = title)
        GlideImage(
            model = url,
            contentDescription = "nasa image",
            modifier = Modifier
                .size(75.dp)
        )
        Row {
            Text(text = photographer)
            Text(text = location)
        }
        Text(text = description)
    }
}