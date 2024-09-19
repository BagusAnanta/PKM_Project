package com.bsoftware.myapplication.bcrypt

import android.util.Log
import org.mindrot.jbcrypt.BCrypt

class BcryptHashPassword {
    private val salt : String = BCrypt.gensalt(12)

    fun hashPassword(password: String): String {
        return BCrypt.hashpw(password, salt)
    }

    fun checkPassword(password: String, hashPassword : String) : Boolean{
        return try {
            BCrypt.checkpw(password, hashPassword)
        } catch (e: StringIndexOutOfBoundsException) {
            Log.e("BcryptHashPassword", e.toString())
            false
        }
    }

}