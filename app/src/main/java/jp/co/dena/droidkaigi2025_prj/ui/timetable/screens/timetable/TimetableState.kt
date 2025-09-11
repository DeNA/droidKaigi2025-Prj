package jp.co.dena.droidkaigi2025_prj.ui.timetable.screens.timetable

import jp.co.dena.droidkaigi2025_prj.data.entity.TimeTable

sealed interface TimetableState {
    data object Loading : TimetableState
    data class Success(
        val timetable: TimeTable,
    ) : TimetableState

    data class Failed(
        val throwable: Throwable,
    ) : TimetableState
}