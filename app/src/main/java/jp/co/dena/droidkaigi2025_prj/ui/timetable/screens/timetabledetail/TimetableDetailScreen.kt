package jp.co.dena.droidkaigi2025_prj.ui.timetable.screens.timetabledetail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import jp.co.dena.droidkaigi2025_prj.data.entity.Session

@Composable
fun TimetableDetailScreen(
    viewModel: TimetableDetailViewModel = hiltViewModel(),
    sessionId: String,
) {
    val screenState by viewModel.screenState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.initSession(sessionId)
    }

    Scaffold { innerPadding ->


        when (val state = screenState) {
            is TimetableDetailState.Loading -> {

                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    CircularProgressIndicator()
                }
            }

            is TimetableDetailState.Success -> {
                val session = state.session
                Column(
                    modifier = Modifier.padding(innerPadding)
                ) {
                    Text(session.id)
                    Text(session.title.ja)
                }

            }

            is TimetableDetailState.Failed -> {

                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {

                    Text(state.throwable.message ?: "Error!")
                }
            }
        }
    }
}