package com.example.tmdbkotlin.login.view

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.example.tmdbkotlin.R
import com.example.tmdbkotlin.login.presenter.LoginPresenter
import com.example.tmdbkotlin.signup.view.SignupActivity
import com.example.tmdbkotlin.tmdb.view.MovieSearchView

class LoginActivity : AppCompatActivity(),ILoginActivity {

    private var loginSpinner: ProgressBar? = null

    /**
     * onCreate method handles the intent passed from the SignupActivity on successful signup
     * and shows a Toast.
     *
     * Initialises the variables for the UI elements:
     *      login progress bar,
     *      signup button,
     *      login button.
     *  and sets the onClickListeners.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val intent : Intent = getIntent()
        val signupMessage = intent.getStringExtra(SignupActivity.SIGNUP_MESSAGE)

        if (signupMessage != null) {
            val toast = Toast.makeText(
                applicationContext,
                signupMessage,
                Toast.LENGTH_SHORT
            )

            toast.show()
        }

        loginSpinner = findViewById(R.id.login_progress_bar)

        val signupFab = findViewById<View>(R.id.signup_fab)
        signupFab.setOnClickListener {
            val intent = Intent(this@LoginActivity, SignupActivity::class.java)
            startActivity(intent)
        }

        val loginButton =findViewById<View>(R.id.loginButton)
        loginButton.setOnClickListener{view:View -> loginButtonHandler()
        }

    }

    /**
     * loginButtonHandler is the onClickListener method for the login button.
     * It sets the visibility of the progress bar as VISIBLE.
     *
     * Extracts the username and password from the UI and passes
     * them to the login method of LoginPresenter class.
     *
     */
    private fun loginButtonHandler() {
        loginSpinner!!.visibility = View.VISIBLE

        val loginTextBox = findViewById<EditText>(R.id.loginUsername)
        val username = loginTextBox.getText().toString()

        val passwordTextBox = findViewById<EditText>(R.id.loginPassword)
        val password = loginTextBox.getText().toString()

        //Hide Keyboard
        passwordTextBox.onEditorAction(EditorInfo.IME_ACTION_DONE)

        LoginPresenter(this).login(username, password)

    }

    /**
     * onLoginFailure is a callback method, called from the LoginPresenter class
     * after the response from the login api call, to show the error message on
     * login failure.
     *
     * @property errorMessage: String
     *              This parameter is passed from the LoginPresenter class on failure
     *              in login.
     *              This contains the error message to be displayed to the user.
     *
     */
    override fun onLoginFailure(errorMessage: String) {
        loginSpinner!!.visibility = View.GONE

        Toast.makeText(
            applicationContext,
            errorMessage,
            Toast.LENGTH_SHORT
        ).show()
    }

    /**
     * onLoginSuccess is a callback method, called from the LoginPresenter class
     * after the response from the login api call, to make the necessary changes in the activity.
     *
     * It sets the visibility of the progress bar as GONE.
     * And, then switches to the MovieSearchView activity.
     */
    override fun onLoginSuccess() {
        loginSpinner!!.visibility = View.GONE

        val intent = Intent(this@LoginActivity, MovieSearchView::class.java)
        startActivity(intent)
    }


}
