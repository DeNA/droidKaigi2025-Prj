package jp.co.dena.droidkaigi2025_prj.ui.timetable.screens.timetable

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.co.dena.droidkaigi2025_prj.data.IUserRepository
import jp.co.dena.droidkaigi2025_prj.data.Languages
import jp.co.dena.droidkaigi2025_prj.data.TimetableRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
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

    init {
        _userLang.update {
            userRepo.getLanguage()
        }
    }

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

    fun handleLanguageClick(lang: Languages) {
        viewModelScope.launch {
            userRepo.changeLanguage(lang)
            _userLang.update { userRepo.getLanguage() }
        }
    }
}