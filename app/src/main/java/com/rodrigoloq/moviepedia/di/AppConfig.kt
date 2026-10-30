package com.rodrigoloq.moviepedia.di

import com.rodrigoloq.moviepedia.data.room.entities.UserAuth

class AppConfig {
    var auth: UserAuth = UserAuth(0,"usuario")
}