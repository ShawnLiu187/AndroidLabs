package com.algonquincollege.liu00415.androidlabs

import android.app.Activity
import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.net.HttpURLConnection
import java.net.URL
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import kotlinx.android.synthetic.main.activity_start.*
import java.io.FileInputStream
import java.io.FileNotFoundException


class weatherForecast : Activity() {

    lateinit var weatherImage: ImageView
    lateinit var tempCurrent: TextView
    lateinit var tempMin: TextView
    lateinit var tempMax: TextView
    lateinit var windSpeed: TextView
    lateinit var progressBar: ProgressBar

    lateinit var imageBitmap: Bitmap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather_forecast)

        weatherImage = findViewById<ImageView>(R.id.weatherImage)
        tempCurrent = findViewById<TextView>(R.id.tempCurrent)
        tempMin = findViewById<TextView>(R.id.tempMin)
        tempMax = findViewById<TextView>(R.id.tempMax)
        windSpeed = findViewById<TextView>(R.id.windSpeed)
        progressBar = findViewById<ProgressBar>(R.id.progress)

        progressBar.visibility = View.VISIBLE

        var myQuery = ForecastQuery()
        myQuery.execute() // runs the thread

    }

    inner class ForecastQuery : AsyncTask<String, Integer, String>()
    {
        var wind: String? = null
        var current: String? = null
        var min: String? = null
        var max: String? = null
        var iconName: String? = null
        var progress = 0


        override fun doInBackground(vararg params: String?): String {
            val url = URL("http://api.openweathermap.org/data/2.5/weather?q=ottawa,ca&APPID=d99666875e0e51521f0040a3d97d0f6a&mode=xml&units=metric")

            var connection = url.openConnection() as HttpURLConnection //goes to the server
            var response = connection.getInputStream()

            val factory = XmlPullParserFactory.newInstance()
            factory.setNamespaceAware(false)
            val xpp = factory.newPullParser()
            xpp.setInput(response, "UTF-8")

            while (xpp.eventType != XmlPullParser.END_DOCUMENT)
            {
//                var name = xpp.name;
                when(xpp.eventType){
                    XmlPullParser.START_TAG -> {
                        if(xpp.name.equals("speed"))
                        {
                            wind = xpp.getAttributeValue(null, "value")
                            wind = "Wind Speed: " + wind
                            progress += 20
//                            var text = xpp.getAttributeValue(null, "value")
                        }else if(xpp.name.equals("temperature"))
                        {
                            current = xpp.getAttributeValue(null, "value")
                            current = "Current Temperature: " + current
                            min = xpp.getAttributeValue(null, "min")
                            min = "Min Temperature: " + min
                            max = xpp.getAttributeValue(null, "max")
                            max = "Max Temperature: " + max
                            progress += 60

                        }
                        if(xpp.name.equals("weather")){
                            iconName = xpp.getAttributeValue(null, "icon")

                            if(fileExistance("$iconName.png")){
                                var fis: FileInputStream? = null
                                try {    fis = openFileInput("$iconName.png")   }
                                catch (e: FileNotFoundException) {    e.printStackTrace()  }
                                imageBitmap = BitmapFactory.decodeStream(fis)
                                progress += 20

                            }else{
                                var weatherUrl = "http://openweathermap.org/img/w/$iconName.png"
                                imageBitmap = getImage(weatherUrl)!!
                                val outputStream = openFileOutput( "$iconName.png", Context.MODE_PRIVATE)
                                imageBitmap?.compress(Bitmap.CompressFormat.PNG, 80, outputStream)
                                outputStream.flush()
                                outputStream.close()
                                progress += 20
                            }
                        }

                        publishProgress()
                    }
                    XmlPullParser.TEXT -> {}
                }

                xpp.next()
            }

            return "Done"
        }

        override fun onProgressUpdate(vararg values: Integer?) {
//            weatherImage = findViewById<ImageView>(R.id.weatherImage)
            tempCurrent.setText(current)
            tempMin.setText(min)
            tempMax.setText(max)
            windSpeed.setText(wind)

            progressBar.setProgress(progress)

        }

        override fun onPostExecute(result: String?) { //run when thread is done and going away
            weatherImage.setImageBitmap(imageBitmap)
            progressBar.visibility = View.INVISIBLE
        }

        fun getImage(url: URL): Bitmap? {
            var connection: HttpURLConnection? = null
            try {
                connection = url.openConnection() as HttpURLConnection
                connection.connect()
                val responseCode = connection.responseCode
                return if (responseCode == 200) {
                    BitmapFactory.decodeStream(connection.inputStream)
                } else
                    null
            } catch (e: Exception) {
                return null
            } finally {
                connection?.disconnect()
            }
        }

        fun getImage(urlString: String): Bitmap? {
            try {
                val url = URL(urlString)
                return getImage(url)
            } catch (e: Exception) {
                return null
            }

        }
        fun fileExistance(fname : String):Boolean{
            val file = getBaseContext().getFileStreamPath(fname)
            return file.exists()   }
    }
}
