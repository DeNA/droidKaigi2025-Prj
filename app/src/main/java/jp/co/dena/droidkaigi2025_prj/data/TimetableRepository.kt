package jp.co.dena.droidkaigi2025_prj.data

import jp.co.dena.droidkaigi2025_prj.data.entity.TimeTable

interface TimetableRepository {
    fun loadTimetable(): TimeTable
}

class TimetableRepositoryImpl : TimetableRepository {
    override fun loadTimetable(): TimeTable {
        TODO("Not yet implemented")
    }
}