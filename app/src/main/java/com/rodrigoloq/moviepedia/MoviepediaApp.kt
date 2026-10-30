package com.rodrigoloq.moviepedia.room

import android.app.Application
import androidx.room.Room
import com.rodrigoloq.moviepedia.data.room.entities.UserAuth
import com.rodrigoloq.moviepedia.session.SessionManager
import dagger.hilt.android.HiltAndroidApp
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@HiltAndroidApp
class MoviepediaApp: Application()
