package com.rodrigoloq.moviepedia.feature.reviews.userreviews.viewmodel

import com.rodrigoloq.moviepedia.data.room.entities.Reviews

data class UserReviewsUIState(
    val reviews: List<Reviews> = listOf(),
    val reviewsWithMovies: List<ReviewWithMovie> = listOf(),
    val inProgress: Boolean = false,
    val successDeletingReview: Boolean? = null,
)
