package com.rodrigoloq.moviepedia.reviews.userreviews.viewmodel

import com.rodrigoloq.moviepedia.room.entities.Reviews

data class UserReviewsUIState(
    val reviews: List<Reviews> = listOf(),
    val reviewsWithMovies: List<ReviewWithMovie> = listOf(),
    val inProgress: Boolean = false,
    val successDeletingReview: Boolean? = null,
)
