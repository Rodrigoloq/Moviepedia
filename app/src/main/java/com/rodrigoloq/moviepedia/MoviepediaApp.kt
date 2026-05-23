package com.rodrigoloq.moviepedia.room

import android.app.Application
import androidx.room.Room
import com.rodrigoloq.moviepedia.room.entities.UserAuth
import com.rodrigoloq.moviepedia.session.SessionManager
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MoviepediaApp: Application() {

    companion object{
        lateinit var ldb: AppDatabase
        lateinit var rdb: Retrofit
        lateinit var auth: UserAuth
        lateinit var sessionManager: SessionManager
    }

    override fun onCreate() {
        super.onCreate()

        ldb = Room.databaseBuilder(this,
            AppDatabase::class.java,
            "db_moviepedia").build()
        rdb = Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        sessionManager = SessionManager(applicationContext)
    }
}