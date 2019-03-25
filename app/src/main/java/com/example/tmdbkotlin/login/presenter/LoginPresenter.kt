package com.example.tmdbkotlin.login.presenter

import android.util.Log
import com.example.tmdbkotlin.login.model.LoginRequest
import com.example.tmdbkotlin.login.model.LoginResponse
import com.example.tmdbkotlin.login.view.LoginActivity
import com.example.tmdbkotlin.network.RetrofitClient
import com.example.tmdbkotlin.network.services.LoginService
import com.example.tmdbkotlin.util.database.UserDbUtil
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginPresenter(private val loginView: LoginActivity) : Callback<LoginResponse> {

    private val AUTHENTICATION_URL = "http://demo1113188.mockable.io/"
    private val TAG = LoginPresenter::class.java.simpleName

    fun login(username: String, password: String) {
        val loginClient = RetrofitClient().getRetrofitClient(AUTHENTICATION_URL)
        val loginService = loginClient.create(LoginService::class.java)
        val personalInfo = LoginRequest.PersonalInfo(username,password)
        val loginRequest = LoginRequest(personalInfo)
        val call = loginService.getLoginResponse(loginRequest)

        call.enqueue(this)
    }

    override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {

        if (response.code() == 200) {
            Log.d(TAG, "LOGIN SUCCESSFUL!!!")
            Log.d(TAG, "Response: $response")
            Log.d(TAG, "full name:  " + response.body()!!.personalInfo!!.fullName)
            Log.d(TAG, "username:  " + response.body()!!.personalInfo!!.userName)
            Log.d(TAG, "id:  " + response.body()!!.personalInfo!!.id)
            Log.d(TAG, "userId:  " + response.body()!!.personalInfo!!.userId)

            val username = response.body()!!.personalInfo!!.userName;

            val dbUtil = UserDbUtil(loginView)
            dbUtil.getReadDB()

            val dbResponse = dbUtil.read(username!!)

            Log.d(TAG, "dbResponse: $dbResponse")

            if (!dbResponse.isNullOrBlank() && dbResponse == username) {
                Log.i(TAG, "DB Response : \n username: $dbResponse")
                loginView.onLoginSuccess()
            }

            else {
                Log.i(TAG, "dbResponse is empty")
                loginView.onLoginFailure("Wrong credentials")
            }

        }

        else {
            Log.i(TAG, "Error response code: " + response.code())
            loginView.onLoginFailure("Error response")
        }
    }

    override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
        Log.d(TAG, t.toString())
        loginView.onLoginFailure("Network error")
    }

}
