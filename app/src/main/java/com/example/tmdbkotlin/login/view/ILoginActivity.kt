package com.example.tmdbkotlin.login.view

interface ILoginActivity {
    fun onLoginSuccess()
    fun onLoginFailure(errorMessage: String)
}
