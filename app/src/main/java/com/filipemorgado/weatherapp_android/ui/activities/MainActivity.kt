package com.filipemorgado.weatherapp_android.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.filipemorgado.weatherapp_android.R
import com.filipemorgado.weatherapp_android.ui.fragments.MainFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // Run gitignore
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()
        }
    }
}