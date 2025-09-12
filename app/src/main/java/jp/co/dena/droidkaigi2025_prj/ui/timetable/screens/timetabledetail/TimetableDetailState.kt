package jp.co.dena.droidkaigi2025_prj.ui.timetable.screens.timetabledetail

import jp.co.dena.droidkaigi2025_prj.RoomColor
import jp.co.dena.droidkaigi2025_prj.data.entity.Room
import jp.co.dena.droidkaigi2025_prj.data.entity.Session
import jp.co.dena.droidkaigi2025_prj.data.entity.Speaker
import jp.co.dena.droidkaigi2025_prj.data.entity.TimeTable

sealed interface TimetableDetailState {
    data object Loading : TimetableDetailState
    data class Success(
        val session: Session,
        val speakers: List<Speaker>,
        val room: RoomEntity
    ) : TimetableDetailState {
        data class RoomEntity(val room: Room, val roomColor: RoomColor)
    }

    data class Failed(val throwable: Throwable) : TimetableDetailState
}