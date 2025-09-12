package jp.co.dena.droidkaigi2025_prj.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimetableAppBar(
    title: String,
    onClickBackButton: (() -> Unit)? = null,
) {
    if (onClickBackButton != null) {
        TopAppBar(
            title = { Text(title) },
            navigationIcon = {
                IconButton(
                    onClick = onClickBackButton,
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Default.ArrowBack,
                        contentDescription = "back",
                    )
                }
            }
        )
    } else {
        TopAppBar(
            title = { Text(title) },
        )
    }
}
