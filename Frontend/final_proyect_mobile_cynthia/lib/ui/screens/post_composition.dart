import 'package:final_proyect_mobile_cynthia/bloc/composition_bloc/composition_bloc.dart';
import 'package:final_proyect_mobile_cynthia/bloc/composition_bloc/composition_event.dart';
import 'package:final_proyect_mobile_cynthia/bloc/composition_bloc/composition_state.dart';
import 'package:final_proyect_mobile_cynthia/bloc/image_pick_bloc/image_pick_bloc_bloc.dart';
import 'package:final_proyect_mobile_cynthia/models/composition_dto.dart';
import 'package:final_proyect_mobile_cynthia/models/composition_model.dart';
import 'package:final_proyect_mobile_cynthia/repository/champion_repository/champion_repository.dart';
import 'package:final_proyect_mobile_cynthia/repository/champion_repository/champion_repositoryImp.dart';
import 'package:final_proyect_mobile_cynthia/repository/composition_repository/composition_repository.dart';
import 'package:final_proyect_mobile_cynthia/repository/composition_repository/composition_repositoryImp.dart';
import 'package:final_proyect_mobile_cynthia/styles/styles.dart';
import 'package:final_proyect_mobile_cynthia/ui/screens/home_screen.dart';
import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:shared_preferences/shared_preferences.dart';

import '../../models/champion_model.dart';

class PostCompositionScreen extends StatefulWidget {
  const PostCompositionScreen({Key? key}) : super(key: key);

  @override
  _PostCompositionScreenState createState() => _PostCompositionScreenState();
}

class _PostCompositionScreenState extends State<PostCompositionScreen> {
  late CompositionRepository compositionRepository;
  late ChampionRepository championRepository;
  final prefs = SharedPreferences.getInstance();
  final _formKey = GlobalKey<FormState>();
  TextEditingController name = TextEditingController();
  TextEditingController description = TextEditingController();
  List<String> champsSelected = [];
  List<ChampionModel> champions = [];
  List<CheckBoxState> checkBoxStates = [];
  String nick = '';
  String id = '';

  @override
  void initState() {
    compositionRepository = CompositionRepositoryImpl();
    championRepository = ChampionRepositoryImpl();
    _getUserLogin();
    _getChamps();
    super.initState();
  }

  _getChamps() async {
    final champs = await championRepository.fetchAllChampions();
    setState(() {
      this.champions = champs;
    });
    _getCheckBoxStates();
  }

  _getUserLogin() async {
    SharedPreferences prefs = await SharedPreferences.getInstance();
    setState(() {
      nick = prefs.getString('nick') ?? '';
      id = prefs.getString('id') ?? '';
    });
  }

  _getCheckBoxStates() {
    this
        .champions
        .map((e) => checkBoxStates.add(CheckBoxState(
              value: false,
              title: e.name!,
            )))
        .toList();
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
              return CompositionBloc(compositionRepository);
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
            child: BlocConsumer<CompositionBloc, CompositionState>(
                listenWhen: (context, state) {
              return state is CompositionCreateSuccess ||
                  state is CompositionCreateError;
            }, listener: (context, state) async {
              if (state is CompositionCreateSuccess) {
                _compositionSuccess(context, state.composition);
              } else if (state is CompositionCreateError) {
                _showSnackbar(context, state.message);
              }
            }, buildWhen: (context, state) {
              return state is CompositionInitial ||
                  state is CompositionCreateLoading;
            }, builder: (ctx, state) {
              if (state is CompositionInitial) {
                return _create(ctx);
              } else if (state is CompositionCreateLoading) {
                return const Center(child: CircularProgressIndicator());
              } else {
                return _create(ctx);
              }
            })),
      ),
    );
  }

  Future<void> _compositionSuccess(
      BuildContext context, CompositionModel late) async {
    Navigator.push(
      context,
      MaterialPageRoute(builder: (context) => const HomeScreen()),
    );
  }

  void _showSnackbar(BuildContext context, String message) {
    final snackBar = SnackBar(
      content: Text(message),
    );
    ScaffoldMessenger.of(context).showSnackBar(snackBar);
  }

  _create(BuildContext context) {
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
                child: Padding(
                  padding: const EdgeInsets.all(20.0),
                  child: Text(
                    'Create your composition',
                    style: textWhite18,
                  ),
                ),
              ),
              Form(
                key: _formKey,
                child: Column(
                  children: [
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
                        hintText: 'Introduce your composition name',
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
                        controller: description,
                        decoration: InputDecoration(
                          focusedBorder: OutlineInputBorder(
                            borderRadius: BorderRadius.circular(30.0),
                            borderSide: BorderSide(color: pink, width: 1.0),
                          ),
                          labelText: 'Description',
                          labelStyle: textWhite18,
                          hintText: 'Introduce your composition description',
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
                        child: Container(
                          width: MediaQuery.of(context).size.width * 0.3,
                          child: Column(
                            children: checkBoxStates
                                .map((c) => CheckboxListTile(
                                      activeColor: Colors.purple,
                                      title: Image.asset(
                                          'assets/images/champions/TFT5_${c.title}.png',
                                          height: 50),
                                      value: c.value,
                                      onChanged: (value) {
                                        setState(() => c.value = value!);
                                        if (c.value) {
                                          champsSelected.add(c.title);
                                        } else {
                                          champsSelected.remove(c.title);
                                        }
                                      },
                                    ))
                                .toList(),
                          ),
                        )),
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
                          final compositionDto = CompositionDto(
                              name: name.text,
                              description: description.text,
                              champions: champsSelected);

                          BlocProvider.of<CompositionBloc>(context)
                              .add(CreateCompositionEvent(compositionDto));
                        }
                      },
                      child: const Text('Create'),
                    ),
                  ),
                ),
              ),
            ],
          ),
        ),
      ),
    );
  }
}

class CheckBoxState {
  bool value = false;
  final String title;

  CheckBoxState({this.value = false, required this.title});
}
