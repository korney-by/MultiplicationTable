package com.korneysoft.multiplicationtable.di

import com.korneysoft.multiplicationtable.data.SoundRepository
import com.korneysoft.multiplicationtable.data.SoundRepositoryAssets
import dagger.Binds
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
abstract class SoundRepositoryModule {
    @Binds
    abstract fun providesSoundRepository (impl: SoundRepositoryAssets):SoundRepository
}