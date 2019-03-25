package com.example.tmdbkotlin.signup.model

data class SignupRequest(val personalInfo: PersonalInfo) {

    data class PersonalInfo(val id: Int,
                            val fullName: String,
                            val userName: String,
                            val userId: Int,
                            val password: String)
}

