package com.currencyconverter.app.data.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier

@Retention
@Qualifier
annotation class MainImmediateDispatcher // UI-related

@Retention
@Qualifier
annotation class IoDispatcher // I/O-related

@Retention
@Qualifier
annotation class DefaultDispatcher // CPU-related

@Retention
@Qualifier
annotation class MainImmediateScope // UI-related

@Retention
@Qualifier
annotation class IoScope // I/O-related

@Retention
@Qualifier
annotation class DefaultScope // CPU-related

@Module
@InstallIn(SingletonComponent::class)
object DataHiltModule {
}
