package jp.co.dena.droidkaigi2025_prj.ui.timetable.screens.timetable

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.co.dena.droidkaigi2025_prj.data.IUserRepository
import jp.co.dena.droidkaigi2025_prj.data.Languages
import jp.co.dena.droidkaigi2025_prj.data.TimetableRepository
import jp.co.dena.droidkaigi2025_prj.data.entity.Date
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import javax.inject.Inject

@HiltViewModel
class TimetableViewModel @Inject constructor(
    private val timetableRepository: TimetableRepository,
    private val userRepo: IUserRepository,
) : ViewModel() {

    private var _screenState = MutableStateFlow<TimetableState>(TimetableState.Loading)
    val screenState = _screenState.asStateFlow()

    private var _userLang = MutableStateFlow<Languages>(Languages.JAPANESE)
    val userLang = _userLang.asStateFlow()

    private var _currentDate = MutableStateFlow<Date>(Date.Day1)
    val currentDate = _currentDate.asStateFlow()

//    init {
//        userLang.onEach {
//            fetchTimetable()
//        }.launchIn(viewModelScope)
//    }

    fun fetchTimetable() {
        if (screenState is TimetableState.Success) {
            return
        }

        viewModelScope.launch {
            _screenState.update {
                TimetableState.Loading
            }

            // Simulating network
            delay(1000L)

            _screenState.update {
                runCatching {
                    val timetable = timetableRepository.loadTimetable()
                    val language = userRepo.getLanguage()
                    Pair(timetable, language)
                }.fold(
                    onSuccess = { (timetable, language) ->
                        val filteredByDateTimetable = timetable.copy(
                            sessions = timetable.sessions.filter {
                                it.startsAt.startsWith(
                                    when (currentDate.value) {
                                        Date.Day1 -> "2025-09-11"
                                        Date.Day2 -> "2025-09-12"
                                    }
                                )
                            }.sortedBy {
                                it.startsAt
                            }
                        )
                        TimetableState.Success(
                            timetable = filteredByDateTimetable,
                            selectedLanguage = language,
                        )
                    },
                    onFailure = { t ->
                        TimetableState.Failed(t)
                    }
                )
            }
        }
    }

    fun handleLanguageClick(lang: Languages) {
        viewModelScope.launch {
            userRepo.changeLanguage(lang)
            _userLang.update { userRepo.getLanguage() }
            fetchTimetable()
        }
    }

    fun handleDateClick(date: Date) {
        viewModelScope.launch {
            // TODO 日付が切り替わった場合にタイムテーブルを取得し直す
            _currentDate.update { date }
        }
    }
}