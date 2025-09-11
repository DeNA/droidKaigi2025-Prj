package jp.co.dena.droidkaigi2025_prj.ui.timetable.screens.timetable

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.co.dena.droidkaigi2025_prj.data.TimetableRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TimetableViewModel @Inject constructor(
    private val timetableRepository: TimetableRepository
) : ViewModel() {

    private var _screenState = MutableStateFlow<TimetableState>(TimetableState.Loading)
    val screenState = _screenState.asStateFlow()

    fun fetchTimetable() {
        if (screenState is TimetableState.Success) {
            return
        }

        viewModelScope.launch {
            _screenState.update {
                TimetableState.Loading
            }

            // Simulating network
            delay(3000L)

            _screenState.update {
                runCatching {
                    timetableRepository.loadTimetable()
                }.fold(
                    onSuccess = { timetable ->
                        TimetableState.Success(timetable = timetable)
                    },
                    onFailure = { t ->
                        TimetableState.Failed(t)
                    }
                )
            }
        }
    }
}