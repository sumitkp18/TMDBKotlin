package com.example.tmdbkotlin.signup.view

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import com.example.tmdbkotlin.R
import com.example.tmdbkotlin.login.view.LoginActivity
import com.example.tmdbkotlin.signup.presenter.SignupPresenter

class SignupActivity : AppCompatActivity(), ISignupActivity {

    private var signupSpinner: ProgressBar? = null

    /**
     * onCreate method initializes the member variable for
     * the UI element: signup progress bar (signupSpinner)
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        signupSpinner = findViewById(R.id.signup_progress_bar)
    }

    /**
     * signupHandler is the onClickListener method of the signup button.
     *
     * It extracts the user's signup form data from the UI and passes the
     * data to the signup method of the SignupPresenter class.
     */
    fun signupHandler(view: View) {
        signupSpinner!!.visibility = View.VISIBLE

        val firstName = (findViewById<EditText>(R.id.firstName)).text.toString()
        val lastName = (findViewById<EditText>(R.id.lastName)).text.toString()
        val username = (findViewById<EditText>(R.id.signupUsername)).text.toString()
        val password = (findViewById<EditText>(R.id.signupPassword)).text.toString()

        SignupPresenter(this).signUp(firstName, lastName, username, password)
    }

    /**
     * onSignupSuccess is a callback method, called from the SignupPresenter class
     * after the response from the signup api call, to make the necessary changes in the activity.
     *
     * It sets the visibility of the progress bar as GONE.
     * And, then switches to the LoginActivity.
     */
    override fun onSignupSuccess() {

        signupSpinner!!.visibility = View.GONE

        val intent = Intent(this@SignupActivity, LoginActivity::class.java)
        intent.putExtra(SIGNUP_MESSAGE, "Signup Successful")
        startActivity(intent)

    }

    /**
     * onSignupFailure is a callback method, called from the SignupPresenter class
     * after the response from the signup api call, to show the error message on
     * signup failure.
     *
     * @property errorMessage: String
     *              This parameter is passed from the LoginPresenter class on failure
     *              in login.
     *              This contains the error message to be displayed to the user.
     */
    override fun onSignupFailure(errorMessage: String) {

        signupSpinner!!.visibility = View.GONE

        val parent = findViewById<View>(R.id.signup_layout)
        Snackbar.make(parent, errorMessage, Snackbar.LENGTH_LONG)
            .setAction("Action", null).show()
    }

    companion object {
        val SIGNUP_MESSAGE = "com.example.myfirstapp.MESSAGE"
    }

}