package com.rodrigoloq.moviepedia.account.model

import android.util.Log
import com.rodrigoloq.moviepedia.room.MoviepediaApp
import com.rodrigoloq.moviepedia.room.UserDAO
import com.rodrigoloq.moviepedia.room.entities.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AccountRepository {
    private val userDAO: UserDAO by lazy { MoviepediaApp.ldb.userDAO() }

    suspend fun getUserInfo(id: Long, onResult: (User?) -> Unit) =
        withContext(Dispatchers.IO){
            val user = userDAO.getUserById(id)
            onResult(user)
        }

    suspend fun editUsernameEmailPhoto(userId: Long,
                                     userName: String,
                                     email: String,
                                     imgLocation: String,
                                     onResult: (Boolean) -> Unit) =
        withContext(Dispatchers.IO){
            try {
                val result = userDAO.editUsernameEmailPhoto(userId,userName,email,imgLocation)
                onResult(result > 0)
            } catch (e: Exception) {
                onResult(false)
                Log.i("RLTAG", "editUsernameAndEmail: ${e.message}")
            }
        }
}