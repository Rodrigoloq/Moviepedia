package com.rodrigoloq.moviepedia.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.rodrigoloq.moviepedia.room.entities.Favorites

@Dao
interface FavoritesDAO {
    @Insert
    suspend fun addFavorite(favorite: Favorites): Long

    @Query("SELECT movieId FROM favorites WHERE userId = :userId")
    suspend fun getFavoritesByUser(userId: Long): List<Int>

    @Query("DELETE FROM favorites WHERE userId = :userId AND movieId = :movieId")
    suspend fun removeFavorite(userId: Long, movieId: Int): Int

    @Query("SELECT EXISTS(SELECT 1 FROM favorites WHERE userId = :userId AND movieId = :movieId)")
    suspend fun isMovieFavorite(userId: Long, movieId: Int): Boolean
}