package com.algonquincollege.liu00415.androidlabs

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.*
import kotlinx.android.synthetic.main.activity_list_items.*

class ListItemsActivity : Activity() {
    val ACTIVITY_NAME = "ListItemsActivity"
    val REQUEST_IMAGE_CAPTURE = 1

    private fun dispatchTakePictureIntent() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (takePictureIntent.resolveActivity(packageManager) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
        }
    }

    private fun setOnCheckedChange() {
        Log.i(ACTIVITY_NAME, "testing Switch")
        if(switchButton?.isChecked() == true){
            val text = "Switch is On" // "Switch is Off"

            val duration = Toast.LENGTH_SHORT //= Toast.LENGTH_LONG if Off
            val toast = Toast.makeText(this , text, duration) //this is the ListActivity

                    toast.show() //display your message box
        }else{
            val text = "Switch is OFF" // "Switch is Off"

            val duration = Toast.LENGTH_LONG  //= Toast.LENGTH_LONG if Off
            val toast = Toast.makeText(this , text, duration) //this is the ListActivity

            toast.show() //display your message box
        }

    }

    private fun onCheckChanged() {
        Log.i(ACTIVITY_NAME, "testing Check")
        val builder = AlertDialog.Builder(this)
        builder.setMessage(R.string.dialog_message)
                .setTitle(R.string.dialog_title)
                .setPositiveButton(R.string.ok, { dialog, id ->
                    val resultIntent = Intent()

                    val toastMessage = getString(R.string.toastMessage)

                    resultIntent.putExtra("Response", toastMessage)

                    setResult(Activity.RESULT_OK, resultIntent)

                    finish()
                })
                .setNegativeButton(R.string.cancel, {dialog, id ->

                })
                .show()

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            val imageBitmap = data.extras.get("data") as Bitmap
            imageButton.setImageBitmap(imageBitmap)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_items)
        Log.i(ACTIVITY_NAME, "In onCreate")

        val imageButton = findViewById(R.id.imageButton) as? ImageButton
        imageButton?.setOnClickListener{


            Log.i(ACTIVITY_NAME,"testing camera")

            dispatchTakePictureIntent()
        }

        val switchButton = findViewById(R.id.switchButton) as? Switch
        switchButton?.setOnClickListener{
            setOnCheckedChange()
        }

        val checkButton = findViewById(R.id.checkBox) as? CheckBox
        checkButton?.setOnClickListener{
            onCheckChanged()
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



