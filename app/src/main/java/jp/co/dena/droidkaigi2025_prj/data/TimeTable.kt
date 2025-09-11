package jp.co.dena.droidkaigi2025_prj.data

data class TimeTable(
    val categories: List<Category>,
    val questions: List<Question>,
    val rooms: List<Room>,
    val sessions: List<Session>,
    val speakers: List<Speaker>,
    val status: String
)