package id.kirara.kmovie.ui.scene.webviewscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import id.kirara.kmovie.core.WebView
import id.kirara.kmovie.ui.components.TransparentIconHolder

@Composable
fun WebViewContent(url: String, onBackPressed: () -> Unit) {
    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier.height(70.dp)
                .fillMaxWidth()
                .background(color = Color.Black.copy(alpha = 0.6f)),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TransparentIconHolder(icon = Icons.AutoMirrored.Rounded.ArrowBack, onClick = onBackPressed)
        }
        WebView(modifier = Modifier.fillMaxSize(), url)
    }
}