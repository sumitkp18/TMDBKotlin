package com.example.tmdbkotlin.login.model

data class LoginResponse(val personalInfo: PersonalInfo) {

    data class PersonalInfo(val id: Int,
                            val fullName: String,
                            val userName: String,
                            val userId: Int)
}
