package jp.co.dena.droidkaigi2025_prj.data

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import jp.co.dena.droidkaigi2025_prj.data.entity.TimeTable
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import javax.inject.Inject
import javax.inject.Singleton

interface TimetableRepository {
    fun loadTimetable(): TimeTable
}

@Singleton
class TimetableRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : TimetableRepository {
    @OptIn(ExperimentalSerializationApi::class)
    override fun loadTimetable(): TimeTable {
        val timetable = context.assets.open("timetable.json").buffered()
        val decodedTimetable = Json.decodeFromStream<TimeTable>(timetable)
        return decodedTimetable
    }
}

