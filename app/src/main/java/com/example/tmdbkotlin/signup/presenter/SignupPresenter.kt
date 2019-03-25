package com.example.tmdbkotlin.signup.presenter

import android.util.Log
import com.example.tmdbkotlin.network.RetrofitClient
import com.example.tmdbkotlin.network.services.SignupService
import com.example.tmdbkotlin.signup.model.SignupRequest
import com.example.tmdbkotlin.signup.model.SignupResponse
import com.example.tmdbkotlin.signup.view.SignupActivity
import com.example.tmdbkotlin.util.database.UserDbUtil
import com.example.tmdbkotlin.util.validation.ValidationResponse
import com.example.tmdbkotlin.util.validation.Validator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignupPresenter(private val signupView: SignupActivity) : Callback<SignupResponse> {

    private val AUTHENTICATION_URL = "http://demo1113188.mockable.io/"
    private val TAG = SignupPresenter::class.java.simpleName

    fun signUp(firstName: String, lastName: String, username: String, password: String) {

        //Temporary
        val id = 123
        val userId = 656

        val validator = Validator()


        if (validator.validateName(firstName) === ValidationResponse.NAME_NON_LETTER) {

            signupView.onSignupFailure(Validator.validationMessage.get(ValidationResponse.NAME_NON_LETTER)!!)
            return
        }


        if (validator.validateName(lastName) === ValidationResponse.NAME_NON_LETTER) {

            signupView.onSignupFailure(Validator.validationMessage.get(ValidationResponse.NAME_NON_LETTER)!!)
            return

        }

        if (validator.validateName(firstName) === ValidationResponse.NAME_EMPTY) {

            signupView.onSignupFailure(Validator.validationMessage.get(ValidationResponse.NAME_EMPTY)!!)
            return

        }

        if (validator.validateName(lastName) === ValidationResponse.NAME_EMPTY) {

            signupView.onSignupFailure(Validator.validationMessage.get(ValidationResponse.NAME_EMPTY)!!)
            return

        }

        if (validator.validateEmail(username) === ValidationResponse.EMAIL_INVALID) {

            signupView.onSignupFailure(Validator.validationMessage.get(ValidationResponse.EMAIL_INVALID)!!)
            return

        }

        if (validator.validatePassword(password) === ValidationResponse.PASSWORD_LENGTH) {

            signupView.onSignupFailure(Validator.validationMessage.get(ValidationResponse.PASSWORD_LENGTH)!!)
            return

        }

        if (validator.validatePassword(password) === ValidationResponse.PASSWORD_SPL_CHAR) {

            signupView.onSignupFailure(Validator.validationMessage.get(ValidationResponse.PASSWORD_SPL_CHAR)!!)
            return
        }


        Log.d(TAG, "Validation successful")


        val signupClient = RetrofitClient().getRetrofitClient(AUTHENTICATION_URL)
        val signupService = signupClient.create(SignupService::class.java!!)

        val fullName = "$firstName $lastName"

        val personalInfo = SignupRequest.PersonalInfo(id,fullName,username,userId,password)
        val signupRequest = SignupRequest(personalInfo)

        val call = signupService.getSignupResponse(signupRequest)
        call.enqueue(this)


    }


    override fun onResponse(call: Call<SignupResponse>, response: Response<SignupResponse>) {

        if (response.code() == 200) {
            Log.d(TAG, "\n\nSIGNUP SUCCESSFUL!!!")

            val fullName = response.body()!!.personalInfo.fullName
            val username = response.body()!!.personalInfo.userName
            val userId = response.body()!!.personalInfo.userId

            Log.d(TAG, "full name:  $fullName")
            Log.d(TAG, "username:  $username")
            Log.d(TAG, "id:  " + response.body()!!.personalInfo.id)
            Log.d(TAG, "userId:  $userId")
            Log.d(TAG, "success:  " + response.body()!!.success)


            //DB operations
            val dbUtil = UserDbUtil(signupView)
            dbUtil.getWriteDB()

            val rowId = dbUtil.insert(fullName, username, userId)

            Log.i(TAG, "rowid: $rowId")

            signupView.onSignupSuccess()

        } else {

            signupView.onSignupFailure("Error Response Code")

        }

    }

    override fun onFailure(call: Call<SignupResponse>, t: Throwable) {

        Log.d("SignupPresenter error: ",t.toString())
        signupView.onSignupFailure("Network Error")

    }

}
