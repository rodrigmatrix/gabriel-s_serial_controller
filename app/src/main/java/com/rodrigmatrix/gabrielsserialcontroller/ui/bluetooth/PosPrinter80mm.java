package com.rodrigmatrix.gabrielsserialcontroller.ui.bluetooth;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;

import java.util.List;

public class PosPrinter80mm extends PosPrinter {

  public PosPrinter80mm(Activity activity) {
    super(activity);
  }

  @Override
  int getCharsOnLine() {
    return 48;
  }
}
