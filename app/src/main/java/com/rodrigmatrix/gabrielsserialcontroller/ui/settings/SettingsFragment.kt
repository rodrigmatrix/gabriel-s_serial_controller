package com.rodrigmatrix.gabrielsserialcontroller.ui.settings

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatDelegate.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import com.rodrigmatrix.gabrielsserialcontroller.R

class SettingsFragment : PreferenceFragmentCompat() {

    private lateinit var preferences: SharedPreferences
    private lateinit var prefListener: SharedPreferences.OnSharedPreferenceChangeListener

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.preferences)
        preferences = PreferenceManager.getDefaultSharedPreferences(context)
        prefListener = SharedPreferences.OnSharedPreferenceChangeListener { pref, _ ->
            setTheme(pref.getString("THEME", null))
        }
        preferences.registerOnSharedPreferenceChangeListener(prefListener)

    }

    private fun setTheme(theme: String?){
        println("theme: $theme")
        when(theme){
            "LIGHT" -> setDefaultNightMode(MODE_NIGHT_NO)
            "DARK" -> setDefaultNightMode(MODE_NIGHT_YES)
            "BATTERY_SAVER" -> setDefaultNightMode(MODE_NIGHT_AUTO_BATTERY)
            "SYSTEM_DEFAULT" -> setDefaultNightMode(MODE_NIGHT_FOLLOW_SYSTEM)
        }
        preferences.registerOnSharedPreferenceChangeListener(prefListener)
    }
}
