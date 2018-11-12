package com.algonquincollege.liu00415.androidlabs

import android.app.Activity
import android.os.Bundle

class MessageDetails : Activity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_message_details)

        var dataToPass = intent.extras //get our bundle back

        var newFragment = MessageFragment()
        newFragment.arguments = dataToPass // bundle goes to fragment

        var transition = getFragmentManager().beginTransaction() //how to load fragment
        transition.replace(R.id.fragment_location, newFragment) //where to load, what to load

        transition.commit() // make it run
    }
}
