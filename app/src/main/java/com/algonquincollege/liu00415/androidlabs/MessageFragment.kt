package com.algonquincollege.liu00415.androidlabs


import android.app.Activity
import android.os.Bundle
import android.app.Fragment
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import org.w3c.dom.Text


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class MessageFragment : Fragment() {
    var amITablet = false

    lateinit var parentDocument: ChatWindow

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        var dataPassed = arguments // get your data back

        var string = dataPassed.getString("Message")
        var id = dataPassed.getLong("ID")

        var screen = inflater.inflate(R.layout.fragment_message, container, false)
        var textView = screen.findViewById<TextView>(R.id.message_text)
        textView.setText(string)
        var idTextView = screen.findViewById<TextView>(R.id.id_text)
        idTextView.setText(id.toString())

        var delButton = screen.findViewById<Button>(R.id.deleteButton)
        delButton.setOnClickListener {
            if(amITablet){
                parentDocument.deleteMessage(id)
                parentDocument.fragmentManager?.beginTransaction()?.remove(this)?.commit()
            }else{
                var dataBack = Intent()
                dataBack.putExtra("ID", id)
                activity.setResult(Activity.RESULT_OK, dataBack)
                activity.finish()
            }

        }

        return screen
    }

    override fun onAttach(context: Activity?) {
        super.onAttach(context)

        if(amITablet){
            parentDocument = context as ChatWindow// need parent for later removing fragment
        }

    }


}
