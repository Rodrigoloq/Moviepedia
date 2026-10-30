package com.rodrigoloq.moviepedia.feature.login.model

import com.rodrigoloq.moviepedia.room.MoviepediaApp
import com.rodrigoloq.moviepedia.data.room.UserDAO
import com.rodrigoloq.moviepedia.data.room.entities.UserAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LoginRepository @Inject constructor(
    private val userDAO: UserDAO
) {
    suspend fun loginUser(email: String,
                          password: String,
                          onResult: suspend (UserAuth?) -> Unit) =
        withContext(Dispatchers.IO){
            val user = userDAO.login(email,password)
            onResult(user)
    }
}