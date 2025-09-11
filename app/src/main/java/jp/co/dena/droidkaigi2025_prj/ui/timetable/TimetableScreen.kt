package jp.co.dena.droidkaigi2025_prj.ui.timetable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.displayCutout
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import jp.co.dena.droidkaigi2025_prj.TableItem
import jp.co.dena.droidkaigi2025_prj.data.entity.TimeTable

@Composable
fun TimeTableScreen(
    viewModel: TimetableViewModel = hiltViewModel()
) {
    val timeTable: TimeTable? by viewModel.timeTable.collectAsStateWithLifecycle()
    timeTable?.let {
        TimeTableScreen(
            decodedTimetable = it
        )
    }
}

@Composable
fun TimeTableScreen(decodedTimetable: TimeTable) {
    val top = with(LocalDensity.current) {
        WindowInsets.displayCutout.getTop(LocalDensity.current).toDp()
    }
    Scaffold(
        topBar = {
            Box(
                modifier = Modifier.padding(horizontal = 24.dp).padding(top = top)
            ) {
                Text(
                    "Time Table",
                    fontSize = 40.sp,
                    fontWeight = FontWeight.Black,
                    color = Color.Blue
                )
            }
        },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .padding(12.dp)
        ) {

            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(
                    decodedTimetable.sessions
                ) {
                    TableItem(it)
                }
            }
        }

    }
}
