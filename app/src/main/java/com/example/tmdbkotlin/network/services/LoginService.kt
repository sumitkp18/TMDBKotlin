package com.example.tmdbkotlin.network.services

import com.example.tmdbkotlin.login.model.LoginRequest
import com.example.tmdbkotlin.login.model.LoginResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginService {
    @POST("login")
    fun getLoginResponse(@Body loginRequest: LoginRequest): Call<LoginResponse>
}
