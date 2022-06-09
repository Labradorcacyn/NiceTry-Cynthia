import 'package:final_proyect_mobile_cynthia/bloc/image_pick_bloc/image_pick_bloc_bloc.dart';
import 'package:final_proyect_mobile_cynthia/bloc/register_bloc/register_bloc.dart';
import 'package:final_proyect_mobile_cynthia/models/register_dto.dart';
import 'package:final_proyect_mobile_cynthia/models/register_response.dart';
import 'package:final_proyect_mobile_cynthia/repository/auth_repository/auth_repository.dart';
import 'package:final_proyect_mobile_cynthia/repository/auth_repository/auth_repository_impl.dart';
import 'package:final_proyect_mobile_cynthia/styles/styles.dart';
import 'package:final_proyect_mobile_cynthia/ui/screens/login_screen.dart';
import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:image_picker/image_picker.dart';

import 'dart:io';
import 'package:file_picker/file_picker.dart';
import 'package:shared_preferences/shared_preferences.dart';

class RegisterScreen extends StatefulWidget {
  const RegisterScreen({Key? key}) : super(key: key);

  @override
  _RegisterScreenState createState() => _RegisterScreenState();
}

class _RegisterScreenState extends State<RegisterScreen> {
  String imageSelect = "Imagen no selecionada";
  FilePickerResult? result;
  PlatformFile? file;

  late AuthRepository authRepository;
  final _formKey = GlobalKey<FormState>();
  TextEditingController name = TextEditingController();
  TextEditingController lastName = TextEditingController();
  TextEditingController nick = TextEditingController();
  TextEditingController emailController = TextEditingController();
  TextEditingController city = TextEditingController();
  TextEditingController rol = TextEditingController();
  TextEditingController password2 = TextEditingController();
  TextEditingController passwordController = TextEditingController();
  final String uploadUrl = 'https://nicetry-api.herokuapp.com/auth/register';
  String path = "";
  bool _passwordVisible = false;
  bool _password2Visible = false;
  bool isPublic = true;

  @override
  void initState() {
    authRepository = AuthRepositoryImpl();
    _passwordVisible = false;
    _password2Visible = false;
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      resizeToAvoidBottomInset: false,
      backgroundColor: Colors.white,
      body: MultiBlocProvider(
        providers: [
          BlocProvider(
            create: (context) {
              return ImagePickBlocBloc();
            },
          ),
          BlocProvider(
            create: (context) {
              return RegisterBloc(authRepository);
            },
          ),
        ],
        child: _createBody(context),
      ),
    );
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
            padding: const EdgeInsets.all(20),
            child: BlocConsumer<RegisterBloc, RegisterState>(
                listenWhen: (context, state) {
              return state is RegisterSuccessState ||
                  state is RegisterErrorState;
            }, listener: (context, state) async {
              if (state is RegisterSuccessState) {
                _registerSuccess(context, state.registerResponse);
              } else if (state is RegisterErrorState) {
                _showSnackbar(context, state.message);
              }
            }, buildWhen: (context, state) {
              return state is RegisterInitial || state is RegisterLoading;
            }, builder: (ctx, state) {
              if (state is RegisterInitial) {
                return _register(ctx);
              } else if (state is RegisterLoading) {
                return const Center(child: CircularProgressIndicator());
              } else {
                return _register(ctx);
              }
            })),
      ),
    );
  }

  Future<void> _registerSuccess(
      BuildContext context, RegisterResponse late) async {
    final prefs = await SharedPreferences.getInstance();
    prefs.setString('id', late.id);
    prefs.setString('avatar', late.avatar);
    prefs.setString('nick', late.nick);

    Navigator.push(
      context,
      MaterialPageRoute(builder: (context) => const LoginScreen()),
    );
  }

  void _showSnackbar(BuildContext context, String message) {
    final snackBar = SnackBar(
      content: Text(message),
    );
    ScaffoldMessenger.of(context).showSnackBar(snackBar);
  }

  _register(BuildContext context) {
    return SingleChildScrollView(
      child: SafeArea(
        child: Padding(
          padding: const EdgeInsets.fromLTRB(24.0, 40.0, 24.0, 0),
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.start,
            children: [
              Image(
                image: Image.asset('assets/images/logo-horizontal.png').image,
                width: 400,
              ),
              Container(
                margin: const EdgeInsets.only(top: 20),
                child: Text(
                  'Register',
                  style: textWhite18,
                ),
              ),
              Form(
                key: _formKey,
                child: Column(
                  children: [
                    BlocConsumer<ImagePickBlocBloc, ImagePickBlocState>(
                        listenWhen: (context, state) {
                          return state is ImageSelectedSuccessState;
                        },
                        listener: (context, state) {},
                        buildWhen: (context, state) {
                          return state is ImagePickBlocInitial ||
                              state is ImageSelectedSuccessState;
                        },
                        builder: (context, state) {
                          if (state is ImageSelectedSuccessState) {
                            path = state.pickedFile.path;

                            return Column(children: [
                              Container(
                                width: 200,
                                height: 200,
                                decoration: BoxDecoration(
                                  shape: BoxShape.circle,
                                  image: DecorationImage(
                                    fit: BoxFit.fill,
                                    image: FileImage(File(path)),
                                  ),
                                ),
                              ),
                              Container(
                                margin: const EdgeInsets.only(bottom: 20),
                                child: ElevatedButton(
                                    style: ButtonStyle(
                                      backgroundColor:
                                          MaterialStateProperty.all(pink),
                                      overlayColor:
                                          MaterialStateProperty.all(bgPurple),
                                      textStyle: MaterialStateProperty.all(
                                          textWhite18),
                                    ),
                                    onPressed: () async {
                                      BlocProvider.of<ImagePickBlocBloc>(
                                              context)
                                          .add(const SelectImageEvent(
                                              ImageSource.gallery));
                                      SharedPreferences prefs =
                                          await SharedPreferences.getInstance();
                                      prefs.setString('file', path);
                                    },
                                    child: const Icon(Icons.add_a_photo)),
                              ),
                            ]);
                          }
                          return Center(
                              child: ElevatedButton(
                                  style: ButtonStyle(
                                    backgroundColor:
                                        MaterialStateProperty.all(pink),
                                    overlayColor:
                                        MaterialStateProperty.all(bgPurple),
                                    textStyle:
                                        MaterialStateProperty.all(textWhite18),
                                  ),
                                  onPressed: () async {
                                    BlocProvider.of<ImagePickBlocBloc>(context)
                                        .add(const SelectImageEvent(
                                            ImageSource.gallery));
                                    SharedPreferences prefs =
                                        await SharedPreferences.getInstance();
                                    prefs.setString('file', path);
                                  },
                                  child: const Icon(Icons.add_a_photo)));
                        }),
                    TextFormField(
                      style: TextStyle(color: Colors.white),
                      controller: name,
                      decoration: InputDecoration(
                        focusedBorder: OutlineInputBorder(
                          borderRadius: BorderRadius.circular(30.0),
                          borderSide: BorderSide(color: pink, width: 1.0),
                        ),
                        labelText: 'Name',
                        labelStyle: textWhite18,
                        hintText: 'Introduce your name',
                        hintStyle: textWhite16,
                        enabledBorder: OutlineInputBorder(
                          borderRadius: BorderRadius.circular(30.0),
                          borderSide: BorderSide(color: pink, width: 1.0),
                        ),
                      ),
                    ),
                    Container(
                      margin: const EdgeInsets.only(top: 30),
                      decoration: BoxDecoration(
                        borderRadius: BorderRadius.circular(14.0),
                      ),
                      child: TextFormField(
                        style: TextStyle(color: Colors.white),
                        controller: lastName,
                        decoration: InputDecoration(
                          focusedBorder: OutlineInputBorder(
                            borderRadius: BorderRadius.circular(30.0),
                            borderSide: BorderSide(color: pink, width: 1.0),
                          ),
                          labelText: 'Last Name',
                          labelStyle: textWhite18,
                          hintText: 'Introduce your Last Name',
                          hintStyle: textWhite16,
                          enabledBorder: OutlineInputBorder(
                            borderRadius: BorderRadius.circular(30.0),
                            borderSide: BorderSide(color: pink, width: 1.0),
                          ),
                        ),
                      ),
                    ),
                    Container(
                      margin: const EdgeInsets.only(top: 30),
                      child: TextFormField(
                        style: TextStyle(color: Colors.white),
                        controller: nick,
                        decoration: InputDecoration(
                          focusedBorder: OutlineInputBorder(
                            borderRadius: BorderRadius.circular(30.0),
                            borderSide: BorderSide(color: pink, width: 1.0),
                          ),
                          labelText: 'Username',
                          labelStyle: textWhite18,
                          hintText: 'Introduce your username',
                          hintStyle: textWhite16,
                          enabledBorder: OutlineInputBorder(
                            borderRadius: BorderRadius.circular(30.0),
                            borderSide: BorderSide(color: pink, width: 1.0),
                          ),
                        ),
                      ),
                    ),
                    Container(
                      margin: const EdgeInsets.only(top: 30),
                      child: TextFormField(
                        style: TextStyle(color: Colors.white),
                        controller: city,
                        decoration: InputDecoration(
                          focusedBorder: OutlineInputBorder(
                            borderRadius: BorderRadius.circular(30.0),
                            borderSide: BorderSide(color: pink, width: 1.0),
                          ),
                          labelText: 'City',
                          labelStyle: textWhite18,
                          hintText: 'Introduce your city',
                          hintStyle: textWhite16,
                          enabledBorder: OutlineInputBorder(
                            borderRadius: BorderRadius.circular(30.0),
                            borderSide: BorderSide(color: pink, width: 1.0),
                          ),
                        ),
                      ),
                    ),
                    Container(
                      margin: const EdgeInsets.only(top: 30),
                      child: TextFormField(
                        style: TextStyle(color: Colors.white),
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
                    Column(
                      children: [
                        Container(
                          margin: const EdgeInsets.only(top: 30),
                          child: TextFormField(
                            style: TextStyle(color: Colors.white),
                            obscureText: !_passwordVisible,
                            controller: passwordController,
                            decoration: InputDecoration(
                              focusedBorder: OutlineInputBorder(
                                borderRadius: BorderRadius.circular(30.0),
                                borderSide: BorderSide(color: pink, width: 1.0),
                              ),
                              labelText: 'Password',
                              labelStyle: textWhite18,
                              hintStyle: textWhite16,
                              hintText: 'Introduce your password',
                              enabledBorder: OutlineInputBorder(
                                borderRadius: BorderRadius.circular(30.0),
                                borderSide: BorderSide(color: pink, width: 1.0),
                              ),
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
                            ),
                          ),
                        ),
                        Container(
                          margin: const EdgeInsets.only(top: 30),
                          child: TextFormField(
                            style: TextStyle(color: Colors.white),
                            obscureText: !_password2Visible,
                            controller: password2,
                            decoration: InputDecoration(
                                focusedBorder: OutlineInputBorder(
                                  borderRadius: BorderRadius.circular(30.0),
                                  borderSide:
                                      BorderSide(color: pink, width: 1.0),
                                ),
                                hintStyle: textWhite16,
                                enabledBorder: OutlineInputBorder(
                                  borderRadius: BorderRadius.circular(30.0),
                                  borderSide:
                                      BorderSide(color: pink, width: 1.0),
                                ),
                                labelText: 'Confirm Password',
                                labelStyle: textWhite18,
                                hintText: 'Confirm your password',
                                suffixIcon: IconButton(
                                  icon: Icon(
                                    _password2Visible
                                        ? Icons.visibility
                                        : Icons.visibility_off,
                                    color: Theme.of(context).primaryColorLight,
                                  ),
                                  onPressed: () {
                                    setState(() {
                                      _password2Visible = !_password2Visible;
                                    });
                                  },
                                )),
                          ),
                        ),
                      ],
                    ),
                  ],
                ),
              ),
              Container(
                margin: const EdgeInsets.only(top: 30, bottom: 20),
                child: Center(
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
                          final registerDto = RegisterDto(
                              name: name.text,
                              lastName: lastName.text,
                              nick: nick.text,
                              city: city.text,
                              rol: isPublic,
                              email: emailController.text,
                              password2: password2.text,
                              password: passwordController.text);

                          BlocProvider.of<RegisterBloc>(context)
                              .add(DoRegisterEvent(registerDto, path));
                        }
                      },
                      child: const Text('Sign up'),
                    ),
                  ),
                ),
              ),
              Row(
                mainAxisAlignment: MainAxisAlignment.center,
                children: [
                  Text(
                    "Already have an account? ",
                    style: textWhite16,
                  ),
                  GestureDetector(
                    onTap: () {
                      Navigator.pushNamed(context, '/login');
                    },
                    child: Text(
                      'Login',
                      style: textPink16,
                    ),
                  ),
                ],
              ),
            ],
          ),
        ),
      ),
    );
  }
}
