package com.rodrigoloq.moviepedia.feature.reviews.userreviews.viewmodel

import com.rodrigoloq.moviepedia.data.retrofit.entities.MovieDetailResponse
import com.rodrigoloq.moviepedia.data.room.entities.Reviews

data class ReviewWithMovie(val review: Reviews,
                           val movie: MovieDetailResponse)
