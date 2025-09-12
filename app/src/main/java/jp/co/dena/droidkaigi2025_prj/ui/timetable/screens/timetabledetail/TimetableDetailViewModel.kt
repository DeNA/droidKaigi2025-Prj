package jp.co.dena.droidkaigi2025_prj.ui.timetable.screens.timetabledetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.co.dena.droidkaigi2025_prj.RoomColor
import jp.co.dena.droidkaigi2025_prj.data.TimetableRepository
import jp.co.dena.droidkaigi2025_prj.data.entity.Session
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.ExperimentalSerializationApi
import javax.inject.Inject

@OptIn(ExperimentalSerializationApi::class)
@HiltViewModel
class TimetableDetailViewModel @Inject constructor(
    private val timetableRepository: TimetableRepository
) : ViewModel() {

    private var _screenState = MutableStateFlow<TimetableDetailState>(TimetableDetailState.Loading)
    val screenState = _screenState.asStateFlow()

    private val timeTable by lazy {
        timetableRepository.loadTimetable()
    }

    fun initSession(sessionId: String) {
        viewModelScope.launch {
            _screenState.update { TimetableDetailState.Loading }

            // simulate network
            delay(1000L)

            _screenState.update {
                timetableRepository.loadTimetable()
                runCatching {
                    timetableRepository.loadSession(sessionId)
                }.fold(
                    onSuccess = { session ->
                        val allSpeakers = timeTable.speakers
                        TimetableDetailState.Success(session = session, speakers = allSpeakers.filter {
                            session.speakers.contains(it.id)
                        }, room = TimetableDetailState.Success.RoomEntity(
                            room = timeTable.rooms.first { it.id == session.roomId },
                            roomColor = RoomColor.entries.first { it.id == session.roomId }
                        ))
                    },
                    onFailure = {
                        TimetableDetailState.Failed(it)
                    },
                )
            }
        }
    }
}