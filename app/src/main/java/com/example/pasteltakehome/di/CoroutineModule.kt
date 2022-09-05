package com.example.pasteltakehome.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers


@Module
@InstallIn(SingletonComponent::class)
object CoroutineModule {

    @Dispatcher(ResourceDispatcher.DEFAULT)
    @Provides
    fun provideDefaultDispatcher() : CoroutineDispatcher = Dispatchers.Default

    @Dispatcher(ResourceDispatcher.IO)
    @Provides
    fun provideIODispatcher() : CoroutineDispatcher = Dispatchers.IO

    @Dispatcher(ResourceDispatcher.MAIN)
    @Provides
    fun provideMainDispatcher() : CoroutineDispatcher = Dispatchers.Main

    @Dispatcher(ResourceDispatcher.UNCONFINED)
    @Provides
    fun provideUnconfinedDispatcher() : CoroutineDispatcher = Dispatchers.Unconfined

}