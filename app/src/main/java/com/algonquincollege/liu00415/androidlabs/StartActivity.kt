package com.algonquincollege.liu00415.androidlabs

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_start.*

class StartActivity : Activity() {
    val ACTIVITY_NAME = "StartActivity"
    val REQUEST_CODE = 50

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)
        Log.i(ACTIVITY_NAME, "In onCreate")

        val startButton = findViewById<Button>(R.id.startButton)

        startButton.setOnClickListener{
            val newActivity2 = Intent(this, ListItemsActivity::class.java)
            startActivityForResult(newActivity2, REQUEST_CODE)
        }

        val chatButton = findViewById<Button>(R.id.chatButton)

        chatButton.setOnClickListener{
            Log.i(ACTIVITY_NAME, "User clicked Start Chat");

            val newActivityChat = Intent(this, ChatWindow::class.java)
            startActivity(newActivityChat)
        }

        val weatherButton = findViewById<Button>(R.id.weather)
        weatherButton.setOnClickListener {
            Log.i(ACTIVITY_NAME, "User clicked weather forecast")

            val newActivityWeather = Intent(this, weatherForecast::class.java)
            startActivity(newActivityWeather)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == 50){
            Log.i(ACTIVITY_NAME, "Returned to StartActivity.onActivityResult");
        }

        if(resultCode == Activity.RESULT_OK) {
            val messagePassed = data?.getStringExtra("Response")
            val duration = Toast.LENGTH_SHORT
            val toast = Toast.makeText(this, messagePassed, duration)

            toast.show()
        }
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
