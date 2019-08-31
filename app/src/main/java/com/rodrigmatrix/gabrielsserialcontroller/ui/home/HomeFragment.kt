package com.rodrigmatrix.gabrielsserialcontroller.ui.home

import android.bluetooth.BluetoothDevice
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.Trace.isEnabled
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.snackbar.Snackbar
import com.rodrigmatrix.gabrielsserialcontroller.MainActivity
import com.rodrigmatrix.gabrielsserialcontroller.R
import com.rodrigmatrix.gabrielsserialcontroller.ui.bluetooth.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_home.*
import org.jetbrains.anko.support.v4.act
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance



class HomeFragment : Fragment(), KodeinAware {

    private lateinit var viewModel: HomeViewModel
    override val kodein by closestKodein()
    private val viewModelFactory: HomeViewModelFactory by instance()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(HomeViewModel::class.java)
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        picker.addSVBar(svbar)
        picker.addOpacityBar(opacitybar)
        picker.setOnColorChangedListener {
            val rgb = Color.valueOf(it)
            println(rgb)
        }
        val btService = viewModel.getBluetoothService()
        btService.changeActivity(activity)

        if(btService.isConnected){
            setView(true)
        }
        button_home.setOnClickListener {
            btService.connect()
        }
        btService.setDeviceCallbacks(object : DeviceCallbacks {
            override fun onConnected() {
                setView(true)
            }
            override fun onFailure() {
                setView(false)
                Snackbar
                    .make(view!!,
                        "Falha ao se conectar ao dispositivo. Tente novamente",
                        Snackbar.LENGTH_LONG).show()
            }
            override fun onDisconnected() {
                setView(false)
                Snackbar
                    .make(view!!,
                        "Dispositivo foi desconectado",
                        Snackbar.LENGTH_LONG).show()
            }
        })
    }

    private fun setView(isConnected: Boolean){
        if(isConnected){
            connect_view.isVisible = false
            color_pick_view.isVisible = true
        }
        else{
            connect_view.isVisible = true
            color_pick_view.isVisible = false
        }
    }
}
