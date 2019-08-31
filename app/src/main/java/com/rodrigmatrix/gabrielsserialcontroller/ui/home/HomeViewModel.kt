package com.rodrigmatrix.gabrielsserialcontroller.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rodrigmatrix.gabrielsserialcontroller.ui.bluetooth.PosPrinter60mm

class HomeViewModel(
    private val bluetoothService: PosPrinter60mm
) : ViewModel() {

    fun getBluetoothService(): PosPrinter60mm{
        return bluetoothService
    }

}