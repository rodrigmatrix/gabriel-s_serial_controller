package com.rodrigmatrix.gabrielsserialcontroller.ui.bluetooth;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;

import java.util.List;

/**
 * Copyright 2015 LeeryBit
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
public abstract class PosPrinter implements Printer {
  private Activity activity;

  private BTService btService;
  private OnStateChangedListener onStateChangedListener;

  int charsOnLine;
  String charsetName = "ASCII";

  PosPrinter(Activity activity) {
    this.activity = activity;
    this.charsOnLine = getCharsOnLine();
  }

  public void changeActivity(Activity ac){
    if(activity == null){
      this.activity = ac;
      btService = new BTService(activity, new Handler() {
        @Override
        public void handleMessage(Message msg) {
          super.handleMessage(msg);
          if (onStateChangedListener != null) {
            onStateChangedListener.onChanged(msg.arg1, msg);
          }
        }
      });
      btService.start();
    }
  }

  abstract int getCharsOnLine();

  @Override
  public boolean isAvailable() {
    return btService.isAvailable();
  }

  @Override
  public boolean isEnabled() {
    return btService.isEnabled();
  }

  @Override
  public boolean isConnected() {
    return btService.getState() == BTService.STATE_CONNECTED;
  }

  @Override
  public void setDeviceCallbacks(DeviceCallbacks callbacks) {
    btService.setDeviceCallbacks(callbacks);
  }

  public void setStateChangedListener(OnStateChangedListener listener) {
    onStateChangedListener = listener;
  }

  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    btService.onActivityResult(requestCode, resultCode, data);
  }

  public void send(byte[] b){
    btService.send(b);
  }

  public void connect() {
    if (!isEnabled()) {
      btService.requestBTActivation(activity, new BTService.BTCallback() {
        @Override
        public void onEnableBTResult(boolean isEnabled) {
          if (isEnabled) connect();
        }
      });

      return;
    }

    new SearchPrinterDialog(activity, btService) {

      @Override
      public void onCancelled() {
      }
    };
  }

  public void disconnect() {
    btService.stop();
  }

  public void setCharsetName(String charsetName) {
    this.charsetName = charsetName;
  }

  public interface OnStateChangedListener {
    void onChanged(int state, Message msg);
  }
}
