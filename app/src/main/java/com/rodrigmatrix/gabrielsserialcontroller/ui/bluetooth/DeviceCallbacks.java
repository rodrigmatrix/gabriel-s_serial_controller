package com.rodrigmatrix.gabrielsserialcontroller.ui.bluetooth;

public interface DeviceCallbacks {
    void onConnected();

    void onFailure();

    void onDisconnected();
}
