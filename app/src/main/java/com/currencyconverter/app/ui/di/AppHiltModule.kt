package com.currencyconverter.app.ui.di

import com.currencyconverter.app.data.di.DefaultDispatcher
import com.currencyconverter.app.data.di.DefaultScope
import com.currencyconverter.app.data.di.IoDispatcher
import com.currencyconverter.app.data.di.IoScope
import com.currencyconverter.app.data.di.MainImmediateDispatcher
import com.currencyconverter.app.data.di.MainImmediateScope
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppHiltModule {

    @MainImmediateScope
    @Singleton
    @Provides
    fun provideMainImmediateScope(
        @MainImmediateDispatcher mainImmediateDispatcher: CoroutineDispatcher,
    ): CoroutineScope = CoroutineScope(SupervisorJob() + mainImmediateDispatcher)

    @MainImmediateDispatcher
    @Singleton
    @Provides
    fun provideMainImmediateDispatcher(): CoroutineDispatcher = Dispatchers.Main.immediate

    @DefaultDispatcher
    @Singleton
    @Provides
    fun provideDefaultDispatcher(): CoroutineDispatcher = Dispatchers.Default

    @IoDispatcher
    @Singleton
    @Provides
    fun provideIoDispatcher(): CoroutineDispatcher = Dispatchers.IO

    @IoScope
    @Singleton
    @Provides
    fun provideIoScope(
        @IoDispatcher ioDispatcher: CoroutineDispatcher,
    ): CoroutineScope = CoroutineScope(SupervisorJob() + ioDispatcher)

    @DefaultScope
    @Singleton
    @Provides
    fun provideDefaultScope(
        @DefaultDispatcher defaultDispatcher: CoroutineDispatcher,
    ): CoroutineScope = CoroutineScope(SupervisorJob() + defaultDispatcher)
}
