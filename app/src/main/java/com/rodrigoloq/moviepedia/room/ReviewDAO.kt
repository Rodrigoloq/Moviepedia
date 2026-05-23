package com.rodrigoloq.moviepedia.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.rodrigoloq.moviepedia.room.entities.Reviews

@Dao
interface ReviewDAO {
    @Insert
    suspend fun addReview(review: Reviews): Long

    @Query("DELETE FROM reviews WHERE userId = :userId AND movieId = :movieId")
    suspend fun deleteReview(userId: Long, movieId: Int): Int

    @Query("UPDATE reviews SET review = :newReview, rating = :newRating WHERE userId = :userId AND movieId = :movieId")
    suspend fun updateReview(userId: Long, movieId: Int, newReview: String, newRating: Int): Int

    @Query("SELECT * FROM reviews WHERE movieId = :movieId")
    suspend fun getReviewsByMovie(movieId: Int): List<Reviews>

    @Query("SELECT * FROM reviews WHERE userId = :userId")
    suspend fun getReviewsByUser(userId: Long): List<Reviews>

    @Query("SELECT EXISTS(SELECT 1 FROM reviews WHERE userId = :userId AND movieId = :movieId)")
    suspend fun isMovieReviewed(userId: Long, movieId: Int) : Boolean
}