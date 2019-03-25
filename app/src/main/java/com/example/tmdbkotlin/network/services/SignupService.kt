package com.example.tmdbkotlin.network.services

import com.example.tmdbkotlin.signup.model.SignupRequest
import com.example.tmdbkotlin.signup.model.SignupResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface SignupService {
    @POST("signup")
    fun getSignupResponse(@Body signupRequest: SignupRequest): Call<SignupResponse>
}
