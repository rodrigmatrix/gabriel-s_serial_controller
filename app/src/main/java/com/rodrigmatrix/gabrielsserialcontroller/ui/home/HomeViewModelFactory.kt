package com.rodrigmatrix.gabrielsserialcontroller.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rodrigmatrix.gabrielsserialcontroller.ui.bluetooth.PosPrinter60mm

class HomeViewModelFactory(
    private val bluetoothService: PosPrinter60mm
): ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return HomeViewModel(bluetoothService) as T
    }
}