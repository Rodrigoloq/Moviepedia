package com.rodrigoloq.moviepedia.feature.reviews.reviews.viewmodel

import com.rodrigoloq.moviepedia.data.retrofit.entities.MovieDetailResponse
import com.rodrigoloq.moviepedia.data.room.entities.Reviews
import com.rodrigoloq.moviepedia.data.room.entities.User

data class ReviewsUIState(
    val reviews: List<Reviews> = listOf(),
    val movieData: MovieDetailResponse = MovieDetailResponse(),
    val reviewsWithUsers: List<ReviewWithUser> = emptyList(),
    val inProgress: Boolean = false,
    val successDeletingReview: Boolean? = null
)
