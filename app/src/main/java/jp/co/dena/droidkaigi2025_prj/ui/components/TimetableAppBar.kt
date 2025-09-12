package jp.co.dena.droidkaigi2025_prj.ui.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimetableAppBar(
    title: String,
) {
    TopAppBar(title = { Text(title) })
}