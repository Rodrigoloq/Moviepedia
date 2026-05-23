package com.rodrigoloq.moviepedia.room.entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "users", indices = [Index(value = ["email"], unique = true)])
data class User(@PrimaryKey(autoGenerate = true) val id: Long = 0,
                val username: String,
                val email: String,
                val password: String,
                var imgLocation: String = "",
                val createdAt: Long = System.currentTimeMillis()) {
}