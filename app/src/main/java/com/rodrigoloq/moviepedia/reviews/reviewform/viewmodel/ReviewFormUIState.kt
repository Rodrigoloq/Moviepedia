package com.rodrigoloq.moviepedia.reviews.reviewform.viewmodel

data class ReviewFormUIState(
    val inProgress: Boolean = false,
    val successAddingReview: Boolean? = null,
    val successEditingReview: Boolean? = null
)
