package com.rodrigoloq.moviepedia.di

import android.content.Context
import com.rodrigoloq.moviepedia.session.SessionManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SessionManagerModule {

    @Provides
    @Singleton
    fun providesSessionManager(
        @ApplicationContext context: Context): SessionManager{
        return SessionManager(context)
    }
}