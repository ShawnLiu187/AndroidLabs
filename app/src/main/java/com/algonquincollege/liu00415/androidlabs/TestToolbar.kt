package com.algonquincollege.liu00415.androidlabs

import android.app.Activity
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.design.widget.Snackbar
import android.support.v4.app.NotificationCompat
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast


class TestToolbar : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    var currentMessage = "You selected item 1"

    lateinit var snackbarButton: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_toolbar)

        var toolbar = findViewById<Toolbar>(R.id.lab8_toolbar)
        setSupportActionBar(toolbar)

        //add navigation to tool bar
        var drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
        var toggle = ActionBarDrawerToggle(this, drawer, toolbar, R.string.open, R.string.close)

        drawer.addDrawerListener(toggle)
        toggle.syncState()

        var navView = findViewById<NavigationView>(R.id.navigation_view)
        navView.setNavigationItemSelectedListener(this)

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

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId)
        {
            R.id.chat_item -> {
                //system notification
                var mBuilder = NotificationCompat.Builder(this, "Channel_name")
                        .setSmallIcon(R.drawable.city)
                        .setAutoCancel(true)
                        .setContentTitle("Let's chat")
                        .setContentText("Chat with me");

                var resultIntent = Intent(this, ChatWindow::class.java)

                var resultPendingIntent = PendingIntent.getActivity(this, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT)
                mBuilder.setContentIntent(resultPendingIntent)
                var mNotificationId = 1
                var mNotifyMgr = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                mNotifyMgr.notify(mNotificationId, mBuilder.build())
            }
            R.id.list_item -> {
                //system notification
                var mBuilder = NotificationCompat.Builder(this, "Channel_name")
                        .setSmallIcon(R.drawable.gas)
                        .setAutoCancel(true)
                        .setContentTitle("List Items")
                        .setContentText("List");

                var resultIntent = Intent(this, ListItemsActivity::class.java)

                var resultPendingIntent = PendingIntent.getActivity(this, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT)
                mBuilder.setContentIntent(resultPendingIntent)
                var mNotificationId = 2
                var mNotifyMgr = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                mNotifyMgr.notify(mNotificationId, mBuilder.build())
            }
            R.id.contact_item -> {
                //system notification
                var mBuilder = NotificationCompat.Builder(this, "Channel_name")
                        .setSmallIcon(R.drawable.ball)
                        .setAutoCancel(true)
                        .setContentTitle("How to contact")
                        .setContentText("choose one");

//                var resultIntent = Intent(this, ListItemsActivity::class.java)
                var emailIntent = Intent(Intent.ACTION_SENDTO)
                var resultPendingIntent = PendingIntent.getActivity(this, 0, emailIntent, PendingIntent.FLAG_UPDATE_CURRENT)
                mBuilder.addAction(R.drawable.ball, "Send email", resultPendingIntent)

                var smsIntent = Intent(Intent.ACTION_SEND)
                var resultSMSIntent = PendingIntent.getActivity(this, 0, smsIntent, PendingIntent.FLAG_UPDATE_CURRENT)
                mBuilder.addAction(R.drawable.ball, "Send email", resultSMSIntent)

                //mBuilder.setContentIntent(resultPendingIntent)
                var mNotificationId = 3
                var mNotifyMgr = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                mNotifyMgr.notify(mNotificationId, mBuilder.build())
            }
        }

        var drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
        drawer.closeDrawer(GravityCompat.START)
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
