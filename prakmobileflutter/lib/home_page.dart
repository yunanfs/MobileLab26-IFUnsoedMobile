
import 'package:flutter/material.dart';
import 'package:shared_preferences/shared_preferences.dart';
import 'sidemenu.dart';

class HomePage extends StatefulWidget {
  const HomePage({super.key});

  @override
  State<HomePage> createState() => _HomePageState();
}

class _HomePageState extends State<HomePage> {

  String username = '';

  void _loadUsername() async {
    SharedPreferences prefs =
    await SharedPreferences.getInstance();

    String? savedUsername = prefs.getString('username');

    setState(() {
      username = savedUsername ?? '';
    });
  }

  @override
  void initState() {
    super.initState();
    _loadUsername();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Home Page'),
      ),
      body: Center(
        child: Text(
          'Ini adalah halaman User $username',
        ),
      ),
      drawer: const SideMenu(),
    );
  }
}
  