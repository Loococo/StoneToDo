package app.loococo.todolist.di

import app.loococo.data.local.preferences.SharedPreferencesManager
import app.loococo.data.repository.PreferencesRepositoryImpl
import app.loococo.domain.repository.PreferencesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun providePreferencesRepository(
        preferencesManager: SharedPreferencesManager
    ): PreferencesRepository = PreferencesRepositoryImpl(preferencesManager)

}