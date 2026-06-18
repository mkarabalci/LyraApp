package com.turkcell.lyraapp.di

import com.turkcell.lyraapp.data.theme.DataStoreThemeRepository
import com.turkcell.lyraapp.data.theme.ThemeRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ThemeModule {

    @Binds
    @Singleton
    abstract fun bindThemeRepository(impl: DataStoreThemeRepository): ThemeRepository
}
