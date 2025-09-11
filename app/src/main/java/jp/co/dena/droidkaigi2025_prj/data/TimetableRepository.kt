package jp.co.dena.droidkaigi2025_prj.data

interface TimetableRepository {
    fun loadTimetable(): TimeTable
}

class TimetableRepositoryImpl : TimetableRepository {
    override fun loadTimetable(): TimeTable {
        TODO("Not yet implemented")
    }
}