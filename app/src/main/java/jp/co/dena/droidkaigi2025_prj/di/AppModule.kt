package jp.co.dena.droidkaigi2025_prj.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jp.co.dena.droidkaigi2025_prj.data.IUserRepository
import jp.co.dena.droidkaigi2025_prj.data.TimetableRepository
import jp.co.dena.droidkaigi2025_prj.data.TimetableRepositoryImpl
import jp.co.dena.droidkaigi2025_prj.data.UserRepository
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
interface AppModule {
    @Binds
    fun timetableRepository(repository: TimetableRepositoryImpl): TimetableRepository

    @Binds
    fun provideUserRepository(userRepository: UserRepository): IUserRepository
}
