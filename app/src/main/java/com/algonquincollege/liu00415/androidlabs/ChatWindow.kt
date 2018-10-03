package com.algonquincollege.liu00415.androidlabs

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.*

class ChatWindow : Activity() {

    var messages = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_window)

        var listView = findViewById<ListView>(R.id.listView)
        var sendButton = findViewById<Button>(R.id.sendButton)
        var editText = findViewById<EditText>(R.id.editText)

var context = this
        var myAdapter = MyAdapter(this)

        sendButton.setOnClickListener{
            var userInput = editText.getText().toString()
            messages.add(userInput)
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

}
