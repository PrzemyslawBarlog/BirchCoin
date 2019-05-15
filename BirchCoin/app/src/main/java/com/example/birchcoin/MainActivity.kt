package com.example.birchcoin

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import android.widget.Button


class MainActivity : AppCompatActivity(), SensorEventListener {

    var running = false
    var sensorManager:SensorManager? = null
    lateinit var mAdView : AdView
    var temp: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        MobileAds.initialize(this, "ca-app-pub-4002528247773089~7509761805")
        mAdView = findViewById(R.id.adView)
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
       val button3 = findViewById<Button>(R.id.button3)

        button3.setOnClickListener {

            temp = (stepsValue.toString()).toInt()
            stepsValue.setText("" + (stepsValue.toString()).toInt().minus(temp))
            progressBar.progress = (stepsValue.toString()).toInt().minus(temp)
        }
    }

    override fun onResume() {
        super.onResume()
        running = true
        var stepsSensor = sensorManager?.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)

        if (stepsSensor == null) {
            Toast.makeText(this, "No Step Counter Sensor !", Toast.LENGTH_SHORT).show()
        } else {
            sensorManager?.registerListener(this, stepsSensor, SensorManager.SENSOR_DELAY_UI)
        }
    }

    override fun onPause() {
        super.onPause()
        running = false
        sensorManager?.unregisterListener(this)
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
    }

    override fun onSensorChanged(event: SensorEvent) {
        if (running) {
            stepsValue.setText("" + (event.values[0]).toInt())
            progressBar.progress = (event.values[0]).toInt()
        }
    }
}