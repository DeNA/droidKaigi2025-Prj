package jp.co.dena.droidkaigi2025_prj.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jp.co.dena.droidkaigi2025_prj.data.TimetableRepository
import jp.co.dena.droidkaigi2025_prj.data.TimetableRepositoryImpl

@InstallIn(SingletonComponent::class)
@Module
interface AppModule {
    @Binds
    fun timetableRepository(repository: TimetableRepositoryImpl): TimetableRepository
}
