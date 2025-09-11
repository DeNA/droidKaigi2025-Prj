package jp.co.dena.droidkaigi2025_prj.ui.timetable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import jp.co.dena.droidkaigi2025_prj.data.entity.Session
import jp.co.dena.droidkaigi2025_prj.data.entity.TimeTable

@Composable
fun TimetableDetailScreen (
    viewModel: TimetableDetailViewModel = hiltViewModel(),
    navController: NavController,
    sessionId: String,
){
    LaunchedEffect(Unit) {
        viewModel.loadSessionById(sessionId)
    }
    val session by viewModel.session.collectAsStateWithLifecycle()
    Scaffold { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
        ) {
            Text(sessionId)
            Text(session?.title?.ja ?: "")
        }
    }
}