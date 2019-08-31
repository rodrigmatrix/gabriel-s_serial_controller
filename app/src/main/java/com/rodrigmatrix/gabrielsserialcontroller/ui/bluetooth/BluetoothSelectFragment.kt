package com.rodrigmatrix.gabrielsserialcontroller.ui.bluetooth

import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.Intent
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.DialogFragment

import com.rodrigmatrix.gabrielsserialcontroller.R
import kotlinx.android.synthetic.main.bluetooth_select_fragment.*
import org.jetbrains.anko.support.v4.toast

class BluetoothSelectFragment : DialogFragment() {

    private var m_bluetoothAdapter: BluetoothAdapter? = null
    private lateinit var m_pairedDevices: Set<BluetoothDevice>
    private val REQUEST_ENABLE_BLUETOOTH = 1

    companion object {
        val EXTRA_ADDRESS: String = "Device_address"
    }

    private lateinit var viewModel: BluetoothSelectViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.bluetooth_select_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(BluetoothSelectViewModel::class.java)
        m_bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
        if(m_bluetoothAdapter == null) {
            toast("this device doesn't support bluetooth")
            return
        }
        if(!m_bluetoothAdapter!!.isEnabled) {
            val enableBluetoothIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            startActivityForResult(enableBluetoothIntent, REQUEST_ENABLE_BLUETOOTH)
        }
        else{
            //pairedDeviceList()
        }
    }

//    private fun pairedDeviceList() {
//        m_pairedDevices = m_bluetoothAdapter!!.bondedDevices
//        val list: ArrayList<BluetoothDevice> = ArrayList()
//
//        if (m_pairedDevices.isNotEmpty()) {
//            for (device: BluetoothDevice in m_pairedDevices) {
//                list.add(device)
//                Log.i("device", "" + device)
//            }
//        } else {
//            toast("no paired bluetooth devices found")
//        }
//
//        val adapter = ArrayAdapter(context!!, android.R.layout.simple_list_item_1, list)
//        select_device_list.adapter = adapter
//        select_device_list.onItemClickListener =
//            AdapterView.OnItemClickListener { _, _, position, _ ->
//                val device: BluetoothDevice = list[position]
//                val address: String = device.address
//
////                val intent = Intent(this, ControlActivity::class.java)
////                intent.putExtra(EXTRA_ADDRESS, address)
////                startActivity(intent)
//            }
//    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_ENABLE_BLUETOOTH) {
            if (resultCode == Activity.RESULT_OK) {
                if (m_bluetoothAdapter!!.isEnabled) {
                    toast("Bluetooth has been enabled")
                } else {
                    toast("Bluetooth has been disabled")
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                toast("Bluetooth enabling has been canceled")
            }
        }
    }
}
