package com.rodrigoloq.moviepedia.feature.account.model

import android.util.Log
import com.rodrigoloq.moviepedia.room.MoviepediaApp
import com.rodrigoloq.moviepedia.data.room.UserDAO
import com.rodrigoloq.moviepedia.data.room.entities.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AccountRepository @Inject constructor(
    private val userDAO: UserDAO
){
    suspend fun getUserInfo(id: Long, onResult: (User?) -> Unit) =
        withContext(Dispatchers.IO){
            try {
                val user = userDAO.getUserById(id)
                onResult(user)
            } catch (e: Exception) {
                onResult(null)
                Log.e("RLTAG", "getUserInfo: ${e.message}", )
            }
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