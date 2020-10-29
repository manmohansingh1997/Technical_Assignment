package com.sonuproject.pago_assignment.Activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.sonuproject.pago_assignment.R
import com.yoovis.yooquiz.DBUsers.DBHelper
import com.yoovis.yooquiz.DBUsers.User

class login : AppCompatActivity() {

    lateinit var pref: SharedPreferences

    // Editor for Shared preferences
    lateinit var editor: Editor
    private val PREF_NAME = "Test"
    val ISLOGIN = "isLogin"


    lateinit var etmobileno: EditText
    lateinit var etPassword: EditText
    lateinit var btnlogin: Button
    lateinit var txtforgot: TextView
    lateinit var txtregister: TextView


    lateinit var usersDB: DBHelper


    lateinit var context: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)



        usersDB = DBHelper(this)
        context = this
        pref = context.getSharedPreferences(PREF_NAME, 0);
        editor = pref.edit();

        if (isLogin()) {
            Toast.makeText(this, "welcome", Toast.LENGTH_LONG).show()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }


        usersDB.addUser(User("123456", "sonu"))
        usersDB.addUser(User("8866158789", "Manmohan"))
        usersDB.addUser(User("9998887778", "singh"))
        usersDB.addUser(User("9033018080", "abcd@123"))
        usersDB.addUser(User("9409431676", "Abc@12"))


        setContentView(R.layout.activity_login)



        title = "Log In"

        etmobileno = findViewById(R.id.etmobileno)
        etPassword = findViewById(R.id.etPassword)
        btnlogin = findViewById(R.id.btnlogin)
        txtforgot = findViewById(R.id.txtForgot)
        txtregister = findViewById(R.id.txt_register)






        btnlogin.setOnClickListener {


            if (etmobileno.text.isBlank()) {
                etmobileno.setError("Mobile Number Missing")

            } else {
                if (etPassword.text.isBlank()) {
                    etPassword.setError("Missing Password")

                } else {
                    loginUserFun()
                }
            }


        }
    }


    fun getPrefValue(key: String?): String? {
        return pref.getString(key, "")
    }

    fun setPrefValue(key: String?, `val`: String?) {
        editor.putString(key, `val`).commit()
    }

    fun setPrefValue(key: String?, `val`: Boolean?) {
        editor.putBoolean(key, `val`!!).commit()
    }

    fun getPrefBooleanValue(key: String?): Boolean {
        return pref.getBoolean(key, false)
    }

    fun isLogin(): Boolean {
        return getPrefBooleanValue(ISLOGIN)
    }

    override fun onPause() {
        super.onPause()
        finish()
    }

    fun loginUserFun() {
        var validUser = usersDB.readUser(etmobileno.text.toString())

        if (validUser.usermobile != "" && validUser.password == etPassword.text.toString()) {


            setPrefValue(ISLOGIN, true)
            Toast.makeText(this, "welcome", Toast.LENGTH_LONG).show()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)


        } else {

            Toast.makeText(
                this, "Mobile Number or Password is wrong", Toast.LENGTH_LONG
            ).show()
        }

    }


}


