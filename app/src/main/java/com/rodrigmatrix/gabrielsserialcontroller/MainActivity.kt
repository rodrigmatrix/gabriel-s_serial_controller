package com.rodrigmatrix.gabrielsserialcontroller

import android.content.SharedPreferences
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate.*
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.preference.PreferenceManager

class MainActivity : AppCompatActivity() {

    private lateinit var preferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        preferences = PreferenceManager.getDefaultSharedPreferences(applicationContext)
        setTheme(preferences.getString("THEME", null))
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        navView.setupWithNavController(navController)


    }

    private fun setTheme(theme: String?){
        when(theme){
            "LIGHT" -> setDefaultNightMode(MODE_NIGHT_NO)
            "DARK" -> setDefaultNightMode(MODE_NIGHT_YES)
            "BATTERY_SAVER" -> setDefaultNightMode(MODE_NIGHT_AUTO_BATTERY)
            "SYSTEM_DEFAULT" -> setDefaultNightMode(MODE_NIGHT_FOLLOW_SYSTEM)
        }
    }
}
