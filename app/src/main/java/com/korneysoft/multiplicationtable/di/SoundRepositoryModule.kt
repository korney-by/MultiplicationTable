package com.korneysoft.multiplicationtable.di

import com.korneysoft.multiplicationtable.domain.data.SoundRepository
import com.korneysoft.multiplicationtable.domain.data.implementation.SoundRepositoryAssets
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class SoundRepositoryModule {
    @Binds
    abstract fun providesSoundRepository(implementer: SoundRepositoryAssets): SoundRepository
}
