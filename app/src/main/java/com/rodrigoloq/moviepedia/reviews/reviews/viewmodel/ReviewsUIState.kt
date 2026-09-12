package com.rodrigoloq.moviepedia.reviews.reviews.viewmodel

import com.rodrigoloq.moviepedia.retrofit.entities.MovieDetailResponse
import com.rodrigoloq.moviepedia.room.entities.Reviews
import com.rodrigoloq.moviepedia.room.entities.User

data class ReviewsUIState(
    val reviews: List<Reviews> = listOf(),
    val movieData: MovieDetailResponse = MovieDetailResponse(),
    val reviewsWithUsers: List<ReviewWithUser> = emptyList(),
    val inProgress: Boolean = false,
    val successDeletingReview: Boolean? = null
)
