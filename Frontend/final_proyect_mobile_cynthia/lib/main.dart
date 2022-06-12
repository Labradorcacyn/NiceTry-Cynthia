import 'package:final_proyect_mobile_cynthia/ui/screens/home_screen.dart';
import 'package:final_proyect_mobile_cynthia/ui/screens/post_composition.dart';
import 'package:final_proyect_mobile_cynthia/ui/screens/users_screen.dart';
import 'package:flutter/material.dart';
import 'ui/screens/login_screen.dart';
import 'ui/screens/menu_screen.dart';
import 'ui/screens/register_screen.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      debugShowCheckedModeBanner: false,
      title: 'NiceTry',
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ),
      initialRoute: '/login',
      routes: {
        '/menu': (context) => const MenuScreen(),
        '/home': (context) => const HomeScreen(),
        '/login': (context) => const LoginScreen(),
        '/register': (context) => const RegisterScreen(),
        '/create-composition': (context) => const PostCompositionScreen(),
        '/users': (context) => const UsersScreen(),
      },
    );
  }
}
