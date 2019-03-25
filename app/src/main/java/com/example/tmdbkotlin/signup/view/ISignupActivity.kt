package com.example.tmdbkotlin.signup.view

interface ISignupActivity {
    fun onSignupSuccess()
    fun onSignupFailure(errorMessage: String)
}
