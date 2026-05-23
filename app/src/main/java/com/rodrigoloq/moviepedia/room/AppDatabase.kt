package com.rodrigoloq.moviepedia.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.rodrigoloq.moviepedia.room.entities.Favorites
import com.rodrigoloq.moviepedia.room.entities.Reviews
import com.rodrigoloq.moviepedia.room.entities.User

@Database(
    entities = [User::class, Favorites::class, Reviews::class],
    version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDAO(): UserDAO
    abstract fun FavoritesDAO(): FavoritesDAO
    abstract fun ReviewDAO(): ReviewDAO
}