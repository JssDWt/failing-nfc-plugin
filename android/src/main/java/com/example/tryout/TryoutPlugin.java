package com.example.tryout;

import android.app.Activity;
import android.app.Application;
import android.app.Application.ActivityLifecycleCallbacks;
import android.content.Intent;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.util.Log;

import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry.Registrar;

/** TryoutPlugin */
public class TryoutPlugin implements ActivityLifecycleCallbacks {
  static final String LOGNAME = "TryoutPlugin";
  private final MethodChannel methodChannel;
  private final Registrar registrar;

  /** Plugin registration. */
  public static void registerWith(Registrar registrar) {
    final MethodChannel channel = new MethodChannel(registrar.messenger(), "tryout");
    final TryoutPlugin plugin = new TryoutPlugin(channel, registrar);
    registrar.activity().getApplication()
      .registerActivityLifecycleCallbacks(plugin);
  }

  private TryoutPlugin(MethodChannel channel, Registrar registrar) {
    this.methodChannel = channel;
    this.registrar = registrar;
  }

  @Override
  public void onActivityCreated(
    Activity activity, 
    Bundle savedInstanceState) {}

  @Override
  public void onActivityStarted(Activity activity) {}

  @Override
  public void onActivityResumed(Activity activity) {
    Log.i(LOGNAME, "onActivityResumed: begin");
    Intent intent = registrar.activity().getIntent();

    if (intent == null) {
      Log.i(LOGNAME, "intent is null, returning.");
      return;
    }
    
    String message;
    if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(intent.getAction())) {
      message = "Hello from NFC adapter.";
    }
    else {
      message = "Hello from android";
    }

    // Call the Dart side. 
    methodChannel.invokeMethod("onMessage", message, new Result() {
      @Override
      public void success(Object o) {
        Log.i(LOGNAME, "methodchannel returned success.");
      }
    
      @Override
      public void error(String code, String message, Object o) {
        Log.e(
          LOGNAME, 
          String.format(
            "methodchannel returned error. code: '%s'. Message: '%s'", 
            code, 
            message));
      }
    
      @Override
      public void notImplemented() {
        Log.e(LOGNAME, "methodchannel returned notimplemented.");
      }
    });

    Log.i(LOGNAME, "onActivityResumed: end");
  }

  @Override
  public void onActivityPaused(Activity activity) {}

  @Override
  public void onActivityStopped(Activity activity) { }

  @Override
  public void onActivitySaveInstanceState(Activity activity, Bundle outState) {}

  @Override
  public void onActivityDestroyed(Activity activity) { }
}
