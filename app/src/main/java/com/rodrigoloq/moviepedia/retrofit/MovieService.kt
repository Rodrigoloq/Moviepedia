package com.rodrigoloq.moviepedia.retrofit

import com.rodrigoloq.moviepedia.BuildConfig
import com.rodrigoloq.moviepedia.retrofit.entities.AlternativeTitlesResponse
import com.rodrigoloq.moviepedia.retrofit.entities.CreditsResponse
import com.rodrigoloq.moviepedia.retrofit.entities.MovieDetailResponse
import com.rodrigoloq.moviepedia.retrofit.entities.MovieReleasesResponse
import com.rodrigoloq.moviepedia.retrofit.entities.MovieResponse
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query


interface MovieService {

    @Headers("Authorization: Bearer ${BuildConfig.API_KEY}",
        "accept: application/json")
    @GET("/3/movie/now_playing")
    suspend fun getListLastMovies(): MovieResponse

    @Headers("Authorization: Bearer ${BuildConfig.API_KEY}",
        "accept: application/json")
    @GET("/3/movie/popular")
    suspend fun getListPopularMovies(): MovieResponse

    @Headers("Authorization: Bearer ${BuildConfig.API_KEY}",
        "accept: application/json")
    @GET("/3/movie/top_rated")
    suspend fun getListTopRatedMovies(@Query("language") language: String = "es-ES",
                                      @Query("page") page: Int = 1): MovieResponse

    @Headers("Authorization: Bearer ${BuildConfig.API_KEY}",
        "accept: application/json")
    @GET("/3/search/movie")
    suspend fun getListSearchResult(@Query("query") movie: String,
                                    @Query("include_adult") includeAdult: Boolean = false,
                                    @Query("language") language: String = "es-ES",
                                    @Query("page") page: Int = 1): MovieResponse


    @Headers("Authorization: Bearer ${BuildConfig.API_KEY}",
        "accept: application/json")
    @GET("/3/movie/{movie_id}")
    suspend fun getMovieDetail(@Path("movie_id") movieId: Int): MovieDetailResponse

    @Headers("Authorization: Bearer ${BuildConfig.API_KEY}",
        "accept: application/json")
    @GET("/3/movie/{movie_id}/alternative_titles")
    suspend fun getMovieAlternativeTitles(@Path("movie_id") movieId: Int): AlternativeTitlesResponse

    @Headers("Authorization: Bearer ${BuildConfig.API_KEY}",
        "accept: application/json")
    @GET("/3/movie/{movie_id}/credits")
    suspend fun getMovieCredits(@Path("movie_id") movieId: Int): CreditsResponse

    @Headers("Authorization: Bearer ${BuildConfig.API_KEY}",
        "accept: application/json")
    @GET("/3/movie/{movie_id}/release_dates")
    suspend fun getMovieReleases(@Path("movie_id") movieId: Int): MovieReleasesResponse
}

