package jp.co.dena.droidkaigi2025_prj.ui.timetable.screens.timetable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.displayCutout
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import jp.co.dena.droidkaigi2025_prj.Route
import jp.co.dena.droidkaigi2025_prj.TableItem
import jp.co.dena.droidkaigi2025_prj.data.entity.Session
import jp.co.dena.droidkaigi2025_prj.data.entity.TimeTable

@Composable
fun TimeTableScreen(
    viewModel: TimetableViewModel = hiltViewModel(),
    onSessionClick: (Session) -> Unit
) {
    val screenState by viewModel.screenState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.fetchTimetable()
    }

    when (val state = screenState) {
        is TimetableState.Loading -> {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                CircularProgressIndicator()
            }
        }

        is TimetableState.Success -> {
            TimeTableScreen(
                decodedTimetable = state.timetable,
                onSessionClick = onSessionClick
            )
        }

        is TimetableState.Failed -> {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text("Error!")
            }
        }
    }
}

@Composable
fun TimeTableScreen(
    decodedTimetable: TimeTable,
    onSessionClick: (Session) -> Unit,
) {
    val top = with(LocalDensity.current) {
        WindowInsets.displayCutout.getTop(LocalDensity.current).toDp()
    }
    Scaffold(
        topBar = {
            Box(
                modifier = Modifier
                    .padding(horizontal = 24.dp)
                    .padding(top = top)
            ) {
                Text(
                    "Time Table",
                    fontSize = 40.sp,
                    fontWeight = FontWeight.Black,
                    color = Color(0xFFFFD700)
                )
            }
        },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(
                    top = innerPadding.calculateTopPadding(),
                    start = innerPadding.calculateStartPadding(
                        LayoutDirection.Ltr
                    ),
                    end = innerPadding.calculateEndPadding(LayoutDirection.Ltr),
                )
                .padding(horizontal = 12.dp)
        ) {

            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(
                    decodedTimetable.sessions
                ) { item ->
                    TableItem(
                        session = item,
                        onClick = { onSessionClick(item) }
                    )
                }
            }
        }

    }
}
