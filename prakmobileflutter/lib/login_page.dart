
import 'package:flutter/material.dart';
import 'package:shared_preferences/shared_preferences.dart';
import 'home_page.dart';

class LoginPage extends StatefulWidget {
  const LoginPage({super.key});

  @override
  State<LoginPage> createState() => _LoginPageState();
}

class _LoginPageState extends State<LoginPage> {

  final TextEditingController _usernameController =
  TextEditingController();

  final TextEditingController _passwordController =
  TextEditingController();

  // Menyimpan username ke Local Storage
  Future<void> _saveUsername() async {
    SharedPreferences prefs =
    await SharedPreferences.getInstance();

    await prefs.setString(
      'username',
      _usernameController.text,
    );
  }

  // Menampilkan input username dan password
  Widget _showInput(
      TextEditingController controller,
      String placeholder,
      bool isPassword,
      ) {
    return TextField(
      controller: controller,
      obscureText: isPassword,
      decoration: InputDecoration(
        hintText: placeholder,
        border: const UnderlineInputBorder(),
      ),
    );
  }

  // Menampilkan dialog
  void _showDialog(String pesan, bool loginBerhasil) {
    showDialog(
      context: context,
      builder: (dialogContext) {
        return AlertDialog(
          title: Text(pesan),
          actions: [
            TextButton(
              child: const Text('OK'),
              onPressed: () {
                Navigator.pop(dialogContext);

                if (loginBerhasil) {
                  Navigator.pushReplacement(
                    context,
                    MaterialPageRoute(
                      builder: (context) => const HomePage(),
                    ),
                  );
                }
              },
            ),
          ],
        );
      },
    );
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(

      // AppBar berwarna biru
      appBar: AppBar(
        title: const Text('Login'),
        backgroundColor: Colors.blue,
        foregroundColor: Colors.white,
      ),

      // Tampilan form login
      body: Container(
        color: Colors.white,
        padding: const EdgeInsets.all(20),

        child: Center(
          child: SingleChildScrollView(
            child: Column(
              mainAxisAlignment: MainAxisAlignment.center,
              children: [

                // Input Username
                _showInput(
                  _usernameController,
                  'Masukkan Username',
                  false,
                ),

                const SizedBox(height: 10),

                // Input Password
                _showInput(
                  _passwordController,
                  'Masukkan Password',
                  true,
                ),

                const SizedBox(height: 15),

                // Tombol Login
                ElevatedButton(
                  style: ElevatedButton.styleFrom(
                    backgroundColor: Colors.blue,
                    foregroundColor: Colors.white,
                  ),

                  child: const Text('Login'),

                  onPressed: () async {

                    if (_usernameController.text == 'admin' &&
                        _passwordController.text == 'admin') {

                      // Simpan username
                      await _saveUsername();

                      if (!mounted) return;

                      // Login berhasil
                      _showDialog(
                        'Anda Berhasil Login',
                        true,
                      );

                    } else {

                      // Login gagal
                      _showDialog(
                        'Username dan Password Salah',
                        false,
                      );

                    }
                  },
                ),

              ],
            ),
          ),
        ),
      ),
    );
  }

  @override
  void dispose() {
    _usernameController.dispose();
    _passwordController.dispose();
    super.dispose();
  }
}
