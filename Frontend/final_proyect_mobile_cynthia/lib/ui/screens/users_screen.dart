import 'package:final_proyect_mobile_cynthia/bloc/user_bloc/user_bloc.dart';
import 'package:final_proyect_mobile_cynthia/bloc/user_bloc/user_event.dart';
import 'package:final_proyect_mobile_cynthia/bloc/user_bloc/user_state.dart';
import 'package:final_proyect_mobile_cynthia/models/all_user_model.dart';
import 'package:final_proyect_mobile_cynthia/repository/user_repository/user_repository.dart';
import 'package:final_proyect_mobile_cynthia/repository/user_repository/user_repositoryImp.dart';
import 'package:final_proyect_mobile_cynthia/ui/screens/error_page.dart';
import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:shared_preferences/shared_preferences.dart';

class UsersScreen extends StatefulWidget {
  const UsersScreen({Key? key}) : super(key: key);

  @override
  _UsersScreenState createState() => _UsersScreenState();
}

class _UsersScreenState extends State<UsersScreen> {
  late UserRepository userRepository;
  final prefs = SharedPreferences.getInstance();
  String nick = '';
  String id = '';

  @override
  void initState() {
    super.initState();
    _getUserLogin();
    userRepository = UserRepositoryImpl();
  }

  @override
  void dispose() {
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return BlocProvider(
      create: (context) {
        return UserBloc(userRepository)..add(const FetchUsersPublicEvent());
      },
      child: Scaffold(body: _listUsers(context)),
    );
  }

  _getUserLogin() async {
    SharedPreferences prefs = await SharedPreferences.getInstance();
    setState(() {
      nick = prefs.getString('nick') ?? '';
      id = prefs.getString('id') ?? '';
    });
  }

  _listUsers(BuildContext context) {
    return BlocBuilder<UserBloc, UserState>(builder: (context, state) {
      if (state is USerInitial) {
        return const Center(child: CircularProgressIndicator());
      } else if (state is UserFetched) {
        return _createUsersView(context, state.users.reversed.toList());
      } else if (state is UserFetchError) {
        return ErrorPage(
          message: state.message,
          retry: () {
            context.watch<UserBloc>().add(const FetchUsersPublicEvent());
          },
        );
      } else {
        return const Text('Not support');
      }
    });
  }

  _createUsersView(BuildContext context, List<AllUserModel> users) {
    return SizedBox(
        height: 800,
        width: MediaQuery.of(context).size.width,
        child: Container(
          color: Color.fromARGB(255, 213, 192, 247),
          child: Stack(children: <Widget>[
            Positioned(
                top: 0,
                child: Image(
                    width: MediaQuery.of(context).size.width,
                    fit: BoxFit.cover,
                    image: Image.asset('assets/images/vector-up.png').image)),
            ListView(
              physics: AlwaysScrollableScrollPhysics(),
              children: <Widget>[
                Padding(
                  padding: const EdgeInsets.all(8.0),
                  child: Row(
                    mainAxisAlignment: MainAxisAlignment.spaceBetween,
                    children: <Widget>[
                      Image.asset("assets/images/logo-horizontal.png",
                          width: 200),
                      IconButton(
                        onPressed: () => Navigator.popAndPushNamed(
                            context, '/create-composition'),
                        iconSize: 20,
                        icon: Icon(Icons.add_box),
                        color: Colors.white,
                      ),
                    ],
                  ),
                ),
                Container(
                  width: double.infinity,
                  height: MediaQuery.of(context).size.height,
                  child: ListView.builder(
                      scrollDirection: Axis.vertical,
                      itemCount: users.length,
                      itemBuilder: (BuildContext context, int index) {
                        return Card(
                            color: Colors.purple[900],
                            shape: RoundedRectangleBorder(
                              borderRadius: BorderRadius.circular(30),
                            ),
                            margin: EdgeInsets.all(10),
                            child: Column(
                              children: <Widget>[
                                Row(
                                  children: <Widget>[
                                    Container(
                                      margin: EdgeInsets.all(10),
                                      width: 60,
                                      height: 60,
                                      decoration: BoxDecoration(
                                          shape: BoxShape.circle,
                                          boxShadow: [
                                            BoxShadow(
                                                color: Colors.black45,
                                                offset: Offset(0, 2),
                                                blurRadius: 6.0),
                                          ]),
                                      child: CircleAvatar(
                                        child: ClipOval(
                                          child: Image.network(
                                            users[index].avatar ??
                                                'https://www.gravatar.com/avatar/205e460b479e2e5b48aec07710c08d50',
                                            fit: BoxFit.cover,
                                            errorBuilder:
                                                (context, error, stackTrace) {
                                              return Container(
                                                width: 60,
                                                height: 60,
                                                color: Colors.purple,
                                                alignment: Alignment.center,
                                                child: const Text(
                                                  'Whoops!',
                                                  style: TextStyle(
                                                    fontSize: 10,
                                                  ),
                                                ),
                                              );
                                            },
                                          ),
                                        ),
                                      ),
                                    ),
                                    Text(
                                      users[index].name ?? '',
                                      style: TextStyle(
                                          color: Colors.white, fontSize: 20),
                                    )
                                  ],
                                ),
                              ],
                            ));
                      }),
                ),
              ],
            )
          ]),
        ));
  }
}
