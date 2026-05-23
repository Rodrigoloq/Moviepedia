package com.rodrigoloq.moviepedia.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.rodrigoloq.moviepedia.room.entities.User
import com.rodrigoloq.moviepedia.room.entities.UserAuth

@Dao
interface UserDAO{
    @Insert
    suspend fun register(user: User): Long

    @Query("SELECT * FROM users WHERE email = :email AND password = :password LIMIT 1")
    suspend fun login(email: String, password: String): UserAuth?

    @Query("SELECT * FROM users WHERE id = :id")
    suspend fun getUserById(id: Long): User?

    @Query("SELECT * FROM users WHERE email = :email")
    suspend fun getUserByEmail(email: String): UserAuth?

    @Query("UPDATE users SET username = :userName, email = :email, imgLocation = :imgLocation WHERE id = :userId")
    suspend fun editUsernameEmailPhoto(userId: Long,
                                       userName: String,
                                       email: String, imgLocation: String): Int
}