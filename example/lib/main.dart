import 'package:flutter/material.dart';
import 'package:tryout/tryout.dart';

void main() => runApp(MyApp());


class MyApp extends StatefulWidget {
  @override
  _MyAppState createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  final Tryout _tryout = Tryout();
  String currentMessage = "No message yet...";

  @override
  void initState() {
    super.initState();
    _tryout.configure(
      onMessage: (String message) async {
        print("onMessage: $message");
        _showMessage(message);
      }
    );
  }

  void _showMessage(String message) {
    setState(() {
          currentMessage = message;
        });
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Tryout app'),
        ),
        body: Center(
          child: Text(currentMessage),
        ),
      ),
    );
  }
}
