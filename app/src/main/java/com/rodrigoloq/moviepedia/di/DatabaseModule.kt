package com.rodrigoloq.moviepedia.di

import android.content.Context
import androidx.room.Room
import com.rodrigoloq.moviepedia.room.AppDatabase
import com.rodrigoloq.moviepedia.room.FavoritesDAO
import com.rodrigoloq.moviepedia.room.ReviewDAO
import com.rodrigoloq.moviepedia.room.UserDAO
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "db_moviepedia"
        ).build()
    }

    @Provides
    fun provideFavoritesDao(
        database: AppDatabase
    ): FavoritesDAO {
        return database.FavoritesDAO()
    }

    @Provides
    fun provideReviewDao(
        database: AppDatabase
    ): ReviewDAO {
        return database.ReviewDAO()
    }

    @Provides
    fun provideUserDao(
        database: AppDatabase
    ): UserDAO {
        return database.userDAO()
    }
}