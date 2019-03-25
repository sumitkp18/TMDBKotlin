package com.example.tmdbkotlin.signup.model

data class SignupResponse(val personalInfo: PersonalInfo,
                          val errors: Array<String>,
                          val success: Boolean) {

    data class PersonalInfo(
        val id: Int,
        val fullName: String,
        val userName: String,
        val userId: Int
    )
}