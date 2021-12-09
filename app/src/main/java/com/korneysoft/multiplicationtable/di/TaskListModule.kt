package com.korneysoft.multiplicationtable.di

import com.korneysoft.multiplicationtable.domain.data.TaskList
import com.korneysoft.multiplicationtable.domain.entities.TaskListImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class TaskListModule {
    @Binds
    abstract fun providesTaskList(implementer: TaskListImpl): TaskList
}
