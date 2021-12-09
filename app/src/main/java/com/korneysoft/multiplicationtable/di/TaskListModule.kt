package com.korneysoft.multiplicationtable.di

import com.korneysoft.multiplicationtable.domain.data.RatingRepository
import com.korneysoft.multiplicationtable.domain.data.TaskList
import com.korneysoft.multiplicationtable.domain.data.implementation.RatingRepositoryImpl
import com.korneysoft.multiplicationtable.domain.entities.TaskListImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class TaskListModule {
    @Binds
    abstract fun providesRatingRepository(implementer: TaskListImpl): TaskList
}
