package com.algonquincollege.liu00415.androidlabs

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText

class LoginActivity : Activity() {
    val ACTIVITY_NAME = "LoginActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        Log.i(ACTIVITY_NAME, "In onCreate")

        val editText = findViewById<EditText>(R.id.loginName)

        val prefs = getSharedPreferences("SavedData", Context.MODE_PRIVATE)

        val userEmail = prefs.getString("UserEmail", "deafault@email.com")

        editText.setText(userEmail)

        val loginButton = findViewById(R.id.loginButton) as? Button
        loginButton?.setOnClickListener(View.OnClickListener {
            val newActivity = Intent(this, StartActivity::class.java)
            val typedString = editText.getText().toString()

            val prefs = prefs.edit()

            prefs.putString("UserEmail", typedString)
            prefs.commit()

            startActivity(newActivity)
        })
    }

    override fun onResume()
    {
        super.onResume()
        Log.i(ACTIVITY_NAME, "In onResume")
    }

    override fun onStart()
    {
        super.onStart()
        Log.i(ACTIVITY_NAME, "In onStart")
    }

    override fun onPause()
    {
        super.onPause()
        Log.i(ACTIVITY_NAME, "In onPause")
    }

    override fun onStop()
    {
        super.onStop()
        Log.i(ACTIVITY_NAME, "In onStop")
    }

    override fun onDestroy()
    {
        super.onDestroy()
        Log.i(ACTIVITY_NAME, "In onDestroy")
    }
}
