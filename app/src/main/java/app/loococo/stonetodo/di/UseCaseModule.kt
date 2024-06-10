package app.loococo.stonetodo.di

import app.loococo.domain.repository.PreferencesRepository
import app.loococo.domain.repository.TodoRepository
import app.loococo.domain.usecase.PreferencesUseCase
import app.loococo.domain.usecase.TodoUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {
    @Provides
    @Singleton
    fun providePreferencesRepository(preferencesRepository: PreferencesRepository): PreferencesUseCase =
        PreferencesUseCase(preferencesRepository)

    @Provides
    @Singleton
    fun provideTodoRepository(todoRepository: TodoRepository): TodoUseCase =
        TodoUseCase(todoRepository)
}