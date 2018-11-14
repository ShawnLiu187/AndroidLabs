package com.algonquincollege.liu00415.androidlabs

import android.app.Activity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast


class TestToolbar : AppCompatActivity() {

    var currentMessage = "You selected item 1"

    lateinit var snackbarButton: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_toolbar)

        var toolbar = findViewById<Toolbar>(R.id.lab8_toolbar)

        setSupportActionBar(toolbar)

        snackbarButton = findViewById<Button>(R.id.startSnackbar)
        snackbarButton.setOnClickListener{
            Snackbar.make(snackbarButton, "I'm a snackbar", Snackbar.LENGTH_LONG)
                    .setAction("Undo", {
                        e -> Toast.makeText(this@TestToolbar, "Undone", Toast.LENGTH_LONG).show()
                    })
                    .show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId)
        {
            R.id.action1 -> {
                Snackbar.make(snackbarButton, currentMessage, Snackbar.LENGTH_LONG).show()
            }
            R.id.action2 -> {
                var builder = AlertDialog.Builder(this);
                builder.setTitle("Do you want to go back")
// Add the buttons
                builder.setPositiveButton("Ok", {dialog, id ->
                    //user clicked OK button
                    finish()
                })
                builder.setNegativeButton("Cancel", {dialog, id ->
                    //user cancelled diaalog
                })
// Create the AlertDialog
                var dialog = builder.create()
                dialog.show()
            }
            R.id.action3 -> {
                var dialogStuff = layoutInflater.inflate(R.layout.dialog_stuff, null)
                var editText = dialogStuff.findViewById<EditText>(R.id.newMessageField)

                var builder = AlertDialog.Builder(this);
                builder.setTitle("Question")
// Add the buttons
                builder.setView(dialogStuff)

                builder.setPositiveButton("Ok", {dialog, id ->
                    //user clicked OK button
                    currentMessage = editText.text.toString()
                })
                builder.setNegativeButton("Cancel", {dialog, id ->
                    //user cancelled diaalog
                })
// Create the AlertDialog
                var dialog = builder.create()
                dialog.show()
            }
            R.id.action4 -> {
                Toast.makeText(this, "Version 1.0, by ShawnBoxiaoLiu", Toast.LENGTH_LONG).show()
            }
        }
        return true
    }
}
