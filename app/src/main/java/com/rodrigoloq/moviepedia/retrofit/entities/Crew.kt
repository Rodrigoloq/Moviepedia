package com.rodrigoloq.moviepedia.retrofit.entities

data class Crew(val adult: Boolean = false,
                val gender: Int = 0,
                val id: Int = 0,
                val known_for_department: String = "",
                val name: String = "",
                val original_name: String = "",
                val popularity: Float = 0f,
                val profile_path: String = "",
                val credit_id: String = "",
                val department: String = "",
                val job: String = ""){

}
