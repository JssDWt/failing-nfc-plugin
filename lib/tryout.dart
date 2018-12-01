import 'dart:async';

import 'package:flutter/services.dart';

typedef Future<dynamic> MessageHandler(String message);

class Tryout {
  factory Tryout() => _instance;

  Tryout.private(MethodChannel channel) 
    : _channel = channel;

  static final Tryout _instance = Tryout.private(
    const MethodChannel('tryout'));

  final MethodChannel _channel;
  MessageHandler _onMessage;

  void configure({
    MessageHandler onMessage
  }) {
    _onMessage = onMessage;
    _channel.setMethodCallHandler(_handleMethod);
    // _channel.invokeMethod('configure');
  }

  Future<dynamic> _handleMethod(MethodCall call) async {
    switch (call.method) {
      case "onMessage":
        return _onMessage(call.arguments as String);
      default:
        print("method '${call.method}' is not implemented by _handleMethod.");
    }
  }
}
