package com.rodrigmatrix.gabrielsserialcontroller

import android.app.Application
import androidx.preference.PreferenceManager
import com.rodrigmatrix.gabrielsserialcontroller.ui.bluetooth.PosPrinter60mm
import com.rodrigmatrix.gabrielsserialcontroller.ui.home.HomeViewModel
import com.rodrigmatrix.gabrielsserialcontroller.ui.home.HomeViewModelFactory
import org.jetbrains.anko.activityManager
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class SerialApplication: Application(), KodeinAware {
    override val kodein = Kodein.lazy {
        import(androidXModule(this@SerialApplication))
        bind<PosPrinter60mm>() with singleton {
            PosPrinter60mm(null)
        }
        bind() from provider {
            HomeViewModelFactory(bluetoothService = instance())
        }
    }

    override fun onCreate() {
        super.onCreate()
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false)
    }
}