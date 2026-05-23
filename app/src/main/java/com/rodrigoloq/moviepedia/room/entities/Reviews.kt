package com.rodrigoloq.moviepedia.room.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "reviews",
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["id"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("userId")]
)
data class Reviews(@PrimaryKey(autoGenerate = true) val id: Long = 0,
                   val rating: Int,
                   val userId: Long,
                   val movieId: Int,
                   val review: String,
                   val postedAt: Long = System.currentTimeMillis(),
                   val editedAt: Long = 0)
