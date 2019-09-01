package com.rodrigmatrix.gabrielsserialcontroller.ui.home

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.snackbar.Snackbar
import com.rodrigmatrix.gabrielsserialcontroller.R
import com.rodrigmatrix.gabrielsserialcontroller.ui.bluetooth.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance
import kotlin.coroutines.CoroutineContext




class HomeFragment : Fragment(), KodeinAware, CoroutineScope {

    private lateinit var viewModel: HomeViewModel
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main
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

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val btService = viewModel.getBluetoothService()
        btService.changeActivity(activity)
        var r = 100
        var g = 100
        var b = 100
        picker.setOnColorChangedListener {
            r = Color.red(it)
            g = Color.green(it)
            b = Color.blue(it)
        }
        piscar_button.setOnClickListener {
            btService.send("{".toByteArray())
        }
        pick_button.setOnClickListener {
            btService.send("r $r".toByteArray())
            btService.send("g $g".toByteArray())
            btService.send("b $b".toByteArray())
        }
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
