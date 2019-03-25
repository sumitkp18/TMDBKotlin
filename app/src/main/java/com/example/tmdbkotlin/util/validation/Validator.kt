package com.example.tmdbkotlin.util.validation

import java.util.*
import java.util.regex.Pattern


class Validator {


    fun validateName(name: String): ValidationResponse {
        //validation for first name
        for (ch in name.toCharArray()) {
            if (!Character.isLetter(ch)) {
                return ValidationResponse.NAME_NON_LETTER

            }
        }

        return if (name.isEmpty()) {
            ValidationResponse.NAME_EMPTY
        } else ValidationResponse.VALID


    }

    fun validateEmail(email: String): ValidationResponse {
        //validation for email using regex
        val p = Pattern.compile("[a-z0-9._%-]+@[a-z0-9.-]+\\.[a-z]{2,4}")
        val m = p.matcher(email)

        return if (m.find())
            ValidationResponse.VALID
        else
            ValidationResponse.EMAIL_INVALID

    }

    fun validatePassword(password: String): ValidationResponse {
        //validation for password

        //1) minimum length of 8
        if (password.length < 8)
            return ValidationResponse.PASSWORD_LENGTH

        //2) contains special characters
        var hasSplChars = false
        for (ch in password.toCharArray()) {
            if (!Character.isLetterOrDigit(ch)) {
                hasSplChars = true
                break
            }
        }

        return if (!hasSplChars) ValidationResponse.PASSWORD_SPL_CHAR else ValidationResponse.VALID


    }

    companion object {

        var validationMessage: Map<ValidationResponse, String> = object : HashMap<ValidationResponse, String>() {
            init {
                put(ValidationResponse.NAME_EMPTY, "Validation fail: name can't be empty")
                put(ValidationResponse.NAME_NON_LETTER, "Validation fail: name can have letters only")
                put(ValidationResponse.EMAIL_INVALID, "Validation fail: username should be valid email id")
                put(ValidationResponse.PASSWORD_LENGTH, "Validation fail: Password should be of minimum 8 characters")
                put(ValidationResponse.PASSWORD_SPL_CHAR, "Validation fail: Password must contain special characters")
                put(ValidationResponse.VALID, "Validation Successful")
            }
        }
    }


}
