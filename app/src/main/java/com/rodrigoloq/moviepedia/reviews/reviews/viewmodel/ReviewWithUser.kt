package com.rodrigoloq.moviepedia.reviews.reviews.viewmodel

import com.rodrigoloq.moviepedia.room.entities.Reviews
import com.rodrigoloq.moviepedia.room.entities.User

data class ReviewWithUser(val review: Reviews,
                          val user: User)
