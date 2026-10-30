package com.rodrigoloq.moviepedia.feature.reviews.userreviews.viewmodel

import com.rodrigoloq.moviepedia.retrofit.entities.MovieDetailResponse
import com.rodrigoloq.moviepedia.room.entities.Reviews

data class ReviewWithMovie(val review: Reviews,
                           val movie: MovieDetailResponse)
