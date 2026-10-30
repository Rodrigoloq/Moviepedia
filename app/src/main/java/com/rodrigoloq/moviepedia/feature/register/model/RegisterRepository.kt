package com.rodrigoloq.moviepedia.feature.register.model

import com.rodrigoloq.moviepedia.room.MoviepediaApp
import com.rodrigoloq.moviepedia.room.UserDAO
import com.rodrigoloq.moviepedia.room.entities.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RegisterRepository @Inject constructor(
    private val userDAO: UserDAO
){
    suspend fun registerUser(user: User,
                             onResult: suspend (Boolean, String) -> Unit) =
        withContext(Dispatchers.IO) {
                if (userDAO.getUserByEmail(user.email) != null) {
                    onResult(false, "Este email ya ha sido registrado")
                } else {
                    val newId = userDAO.register(user)
                    onResult(newId > 0, "Error revise sus datos")
                }
        }
}