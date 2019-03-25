package com.example.tmdbkotlin.login.model

data class LoginRequest(val personalInfo: PersonalInfo?) {

    data class PersonalInfo(val userName: String, val password: String)

}
