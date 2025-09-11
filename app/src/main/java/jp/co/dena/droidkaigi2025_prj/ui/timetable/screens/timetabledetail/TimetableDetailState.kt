package jp.co.dena.droidkaigi2025_prj.ui.timetable.screens.timetabledetail

import jp.co.dena.droidkaigi2025_prj.data.entity.Session

sealed interface TimetableDetailState {
    data object Loading : TimetableDetailState
    data class Success(
        val session: Session
    ) : TimetableDetailState

    data class Failed(val throwable: Throwable) : TimetableDetailState
}