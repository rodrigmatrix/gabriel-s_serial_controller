package com.rodrigmatrix.gabrielsserialcontroller.ui.bluetooth;


public interface Printer {

  boolean isAvailable();

  boolean isEnabled();

  boolean isConnected();

  void setDeviceCallbacks(DeviceCallbacks callbacks);

}
