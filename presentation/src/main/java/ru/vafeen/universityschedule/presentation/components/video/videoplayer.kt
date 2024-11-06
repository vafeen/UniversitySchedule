package ru.vafeen.universityschedule.presentation.components.video

import android.net.Uri
import android.widget.ImageView
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.viewinterop.AndroidView
import com.bumptech.glide.Glide

@Composable
internal fun GifPlayer(
    size: Dp,
    modifier: Modifier = Modifier,
    imageUri: Uri
) {
    AndroidView(
        modifier = modifier.size(size),
        factory = { context ->
            ImageView(context).apply {
                // Используем Glide для загрузки изображения из Uri
                Glide.with(context)
                    .asGif() // Указываем, что это GIF
                    .load(imageUri)
                    .into(this)
            }
        })
}
