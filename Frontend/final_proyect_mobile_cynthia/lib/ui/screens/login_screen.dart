import 'package:final_proyect_mobile_cynthia/bloc/login_bloc/login_bloc.dart';
import 'package:final_proyect_mobile_cynthia/models/login_dto.dart';
import 'package:final_proyect_mobile_cynthia/models/login_response.dart';
import 'package:final_proyect_mobile_cynthia/repository/auth_repository/auth_repository.dart';
import 'package:final_proyect_mobile_cynthia/repository/auth_repository/auth_repository_impl.dart';
import 'package:final_proyect_mobile_cynthia/styles/styles.dart';
import 'package:final_proyect_mobile_cynthia/ui/screens/menu_screen.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:shared_preferences/shared_preferences.dart';

class LoginScreen extends StatefulWidget {
  const LoginScreen({Key? key}) : super(key: key);

  @override
  _LoginScreenState createState() => _LoginScreenState();
}

class _LoginScreenState extends State<LoginScreen> {
  late AuthRepository authRepository;
  final _formKey = GlobalKey<FormState>();
  TextEditingController emailController = TextEditingController();
  TextEditingController passwordController = TextEditingController();
  bool _passwordVisible = false;

  @override
  void initState() {
    SystemChrome.setPreferredOrientations([
      DeviceOrientation.portraitUp,
      DeviceOrientation.portraitDown,
    ]);
    authRepository = AuthRepositoryImpl();
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return BlocProvider(
        create: (context) {
          return LoginBloc(authRepository);
        },
        child: _createBody(context));
  }

  _createBody(BuildContext context) {
    return Scaffold(
      body: Center(
          child: Container(
        width: MediaQuery.of(context).size.width,
        height: MediaQuery.of(context).size.height,
        decoration: BoxDecoration(
            gradient: LinearGradient(
          begin: Alignment.topRight,
          end: Alignment.bottomLeft,
          colors: [
            bgPurple,
            bgPurplelight,
          ],
        )),
        child: Stack(
          children: <Widget>[
            Positioned(
                top: 0,
                child: Image(
                    width: MediaQuery.of(context).size.width,
                    fit: BoxFit.cover,
                    image: Image.asset('assets/images/vector-up.png').image)),
            Positioned(
                bottom: 0,
                child: Image(
                    fit: BoxFit.cover,
                    width: MediaQuery.of(context).size.width,
                    image: Image.asset('assets/images/vector-down.png').image)),
            BlocConsumer<LoginBloc, LoginState>(listenWhen: (context, state) {
              return state is LoginSuccessState || state is LoginErrorState;
            }, listener: (context, state) async {
              if (state is LoginSuccessState) {
                _loginSuccess(context, state.loginResponse);
              } else if (state is LoginErrorState) {
                _showSnackbar(context, state.message);
              }
            }, buildWhen: (context, state) {
              return state is LoginInitialState || state is LoginLoadingState;
            }, builder: (ctx, state) {
              if (state is LoginInitialState) {
                return _login(ctx);
              } else if (state is LoginLoadingState) {
                return const Center(child: CircularProgressIndicator());
              } else {
                return _login(ctx);
              }
            }),
          ],
        ),
      )),
    );
  }

  _loginSuccess(BuildContext context, LoginResponse late) async {
    final prefs = await SharedPreferences.getInstance();
    prefs.setString('token', late.token);
    prefs.setString('id', late.id);
    prefs.setString('avatar', late.avatar);
    prefs.setString('nick', late.nick);

    Navigator.push(
      context,
      MaterialPageRoute(builder: (context) => const MenuScreen()),
    );
  }

  void _showSnackbar(BuildContext context, String message) {
    final snackBar = SnackBar(
      content: Text(message),
    );
    ScaffoldMessenger.of(context).showSnackBar(snackBar);
  }

  _login(BuildContext context) {
    return SingleChildScrollView(
      child: SafeArea(
          child: Padding(
        padding: const EdgeInsets.fromLTRB(24.0, 40.0, 24.0, 0),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Image(
                image: Image.asset('assets/images/logo-horizontal.png').image),
            Container(
              margin: const EdgeInsets.only(top: 50, bottom: 20),
              child: Text(
                'LOGIN',
                style: textWhite16,
              ),
            ),
            Form(
              key: _formKey,
              child: Column(
                children: [
                  Container(
                    decoration: BoxDecoration(
                      borderRadius: BorderRadius.circular(14.0),
                    ),
                    child: TextFormField(
                      style: TextStyle(color: Colors.white),
                      validator: (String? value) {
                        return (value == null || !value.contains('@'))
                            ? 'Do not use the @ char.'
                            : null;
                      },
                      onSaved: (String? value) {},
                      controller: emailController,
                      decoration: InputDecoration(
                        focusedBorder: OutlineInputBorder(
                          borderRadius: BorderRadius.circular(30.0),
                          borderSide: BorderSide(color: pink, width: 1.0),
                        ),
                        labelText: 'Email',
                        labelStyle: textWhite18,
                        hintText: 'Introduce your email',
                        hintStyle: textWhite16,
                        enabledBorder: OutlineInputBorder(
                          borderRadius: BorderRadius.circular(30.0),
                          borderSide: BorderSide(color: pink, width: 1.0),
                        ),
                      ),
                    ),
                  ),
                  Container(
                    margin: const EdgeInsets.only(top: 40, bottom: 80),
                    decoration: BoxDecoration(
                      borderRadius: BorderRadius.circular(14.0),
                    ),
                    child: TextFormField(
                        style: TextStyle(color: Colors.white),
                        controller: passwordController,
                        obscureText: !_passwordVisible,
                        decoration: InputDecoration(
                          focusedBorder: OutlineInputBorder(
                            borderRadius: BorderRadius.circular(30.0),
                            borderSide: BorderSide(color: pink, width: 1.0),
                          ),
                          labelText: 'Password',
                          labelStyle: textWhite18,
                          hintText: 'Introduce your password',
                          suffixIcon: IconButton(
                            icon: Icon(
                              _passwordVisible
                                  ? Icons.visibility
                                  : Icons.visibility_off,
                              color: Theme.of(context).primaryColorLight,
                            ),
                            onPressed: () {
                              setState(() {
                                _passwordVisible = !_passwordVisible;
                              });
                            },
                          ),
                          hintStyle: textWhite16,
                          enabledBorder: OutlineInputBorder(
                            borderRadius: BorderRadius.circular(30.0),
                            borderSide: BorderSide(color: pink, width: 1.0),
                          ),
                        ),
                        onSaved: (String? value) {
                          // This optional block of code can be used to run
                          // code when the user saves the form.
                        },
                        validator: (value) {
                          return (value == null || value.isEmpty)
                              ? 'Write a password'
                              : null;
                        }),
                  )
                ],
              ),
            ),
            Center(
              child: SizedBox(
                width: 200,
                height: 50,
                child: ElevatedButton(
                  style: ButtonStyle(
                    backgroundColor: MaterialStateProperty.all(pink),
                    overlayColor: MaterialStateProperty.all(bgPurple),
                    textStyle: MaterialStateProperty.all(textWhite18),
                  ),
                  onPressed: () {
                    if (_formKey.currentState!.validate()) {
                      final loginDto = LoginDto(
                          email: emailController.text,
                          password: passwordController.text);
                      BlocProvider.of<LoginBloc>(context)
                          .add(DoLoginEvent(loginDto));
                    }
                  },
                  child: const Text('Login'),
                ),
              ),
            ),
            Container(
              margin: const EdgeInsets.only(top: 30),
              child: Row(
                mainAxisAlignment: MainAxisAlignment.center,
                children: [
                  Text(
                    "Don't have an account? ",
                    style: textWhite16,
                  ),
                  GestureDetector(
                    onTap: () {
                      Navigator.pushNamed(context, '/register');
                    },
                    child: Text('Create account', style: textPink16),
                  ),
                ],
              ),
            ),
          ],
        ),
      )),
    );
  }
}
