package com.rodrigoloq.moviepedia.feature.reviews.reviewform.viewmodel

data class ReviewFormUIState(
    val inProgress: Boolean = false,
    val successAddingReview: Boolean? = null,
    val successEditingReview: Boolean? = null
)
