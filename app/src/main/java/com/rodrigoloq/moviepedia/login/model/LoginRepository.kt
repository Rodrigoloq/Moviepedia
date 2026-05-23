package com.rodrigoloq.moviepedia.login.model

import com.rodrigoloq.moviepedia.room.MoviepediaApp
import com.rodrigoloq.moviepedia.room.UserDAO
import com.rodrigoloq.moviepedia.room.entities.UserAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LoginRepository {
    private val userDAO: UserDAO by lazy { MoviepediaApp.ldb.userDAO() }

    suspend fun loginUser(email: String,
                          password: String,
                          onResult: suspend (UserAuth?) -> Unit) =
        withContext(Dispatchers.IO){
            val user = userDAO.login(email,password)
            onResult(user)
    }
}