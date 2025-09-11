package jp.co.dena.droidkaigi2025_prj.ui.timetable

import android.content.Context
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import jp.co.dena.droidkaigi2025_prj.data.entity.TimeTable
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import javax.inject.Inject

@OptIn(ExperimentalSerializationApi::class)
@HiltViewModel
class TimetableViewModel @Inject constructor(
    @field:ApplicationContext private val context: Context
) : ViewModel() {
    private val _timeTable: MutableStateFlow<TimeTable?> = MutableStateFlow(null)
    val timeTable: StateFlow<TimeTable?> = _timeTable.asStateFlow()

    init {
        val timetable = context.assets.open("timetable.json").buffered()
        val decodedTimetable = Json.decodeFromStream<TimeTable>(timetable)
        _timeTable.update { decodedTimetable }
    }
}