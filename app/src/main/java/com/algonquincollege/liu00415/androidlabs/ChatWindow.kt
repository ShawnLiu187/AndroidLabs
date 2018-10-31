package com.algonquincollege.liu00415.androidlabs

import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.*

class ChatWindow : Activity() {

    var messages = ArrayList<String>()
    val ACTIVITY_NAME = "ChatWindow"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_window)

        val dbHelper = ChatDatabaseHelper()  //get a helper object
        val db = dbHelper.writableDatabase

        val results = db.query(TABLE_NAME, arrayOf("_id", KEY_MESSAGES), null, null, null, null, null, null )

        Log.i(ACTIVITY_NAME, "Cursor's column count = " + results.getColumnCount())

        for( i in 0..results.getColumnCount()-1)
        {
            Log.i(ACTIVITY_NAME, "Column " + i + " Name: " +results.getColumnName(i))
        }

        val numRows = results.count
        results.moveToFirst() // point to first row of results
        val idIndex = results.getColumnIndex("_id") //find the index of _id column
        val messagesIndex = results.getColumnIndex(KEY_MESSAGES) // find the index of Messages column

        while(!results.isAfterLast()) // while you are not done with reading data
        {
            var thisID = results.getInt(idIndex)
            var thisMessage = results.getString(messagesIndex)

            Log.i("ChatWindow ", "Cursor's row count = " + thisID);
            Log.i("ChatWindow ", "SQL Message: " + thisMessage);

            messages.add(thisMessage)
            results.moveToNext()
        }

        var listView = findViewById<ListView>(R.id.listView)
        var sendButton = findViewById<Button>(R.id.sendButton)
        var editText = findViewById<EditText>(R.id.editText)

        var context = this
        var myAdapter = MyAdapter(this)

        sendButton.setOnClickListener{
            var userInput = editText.getText().toString()
            messages.add(userInput)

            //write to database
            val newRow = ContentValues()
            newRow.put(KEY_MESSAGES, userInput)

            db.insert(TABLE_NAME, "", newRow)

            editText.setText("");

            val imm = this.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(sendButton.getWindowToken(), 0)

            myAdapter.notifyDataSetChanged()//reload
        }
        listView?.setAdapter(myAdapter) ///Initially load list

    }
    inner class MyAdapter(ctx: Context) : ArrayAdapter<String>(ctx, 0 ) {
           override fun getCount(): Int {
               return messages.size
           }

           override fun getItem(position: Int): String? {
               return messages.get(position)
           }

        override fun getView(position : Int, convertView: View?, parent : ViewGroup): View {
            var inflater = LayoutInflater.from(parent.getContext())
            var result = null as View?

            if(position%2 == 0) {

                result = inflater.inflate(R.layout.chat_row_incoming, null)
                val thisText = result.findViewById(R.id.message_text_incoming) as TextView
                thisText.setText(getItem(position))

            }else{

            result = inflater.inflate(R.layout.chat_row_outgoing, null)
                val thisText2 = result.findViewById(R.id.message_text) as TextView
                thisText2.setText(getItem(position))
            }




            return result
        }

        override fun getItemId(position: Int): Long{
            return 0
        }
    }

    val DATABASE_NAME = "MyDatabaseFile"
    val VERSION_NUM = 1
    val TABLE_NAME = "Messages"
    val KEY_MESSAGES = "Messages"


    inner class ChatDatabaseHelper : SQLiteOpenHelper(this@ChatWindow, DATABASE_NAME, null, VERSION_NUM){
        override fun onCreate(db: SQLiteDatabase) {
            Log.i("ChatDatabaseHelper", "Calling onCreate");
//            db.execSQL("CREATE TABLE " + TABLE_NAME +
//                    " ( _id INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_MESSAGES + " TEXT) ") //create the table
            db.execSQL("CREATE TABLE $TABLE_NAME (_id INTEGER PRIMARY KEY AUTOINCREMENT, $KEY_MESSAGES TEXT)")
        }



        override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME)

            Log.i("ChatDatabaseHelper", "Calling onUpgrade, oldVersion=" + oldVersion + " newVersion=" + newVersion);


            //create new table
            onCreate(db)
        }
    }

}
