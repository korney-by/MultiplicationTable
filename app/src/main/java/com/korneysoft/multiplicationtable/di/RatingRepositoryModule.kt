package com.korneysoft.multiplicationtable.di

import com.korneysoft.multiplicationtable.domain.data.RatingRepository
import com.korneysoft.multiplicationtable.domain.data.implementation.RatingRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RatingRepositoryModule {
    @Binds
    abstract fun providesRatingRepository(implementer: RatingRepositoryImpl): RatingRepository
}
