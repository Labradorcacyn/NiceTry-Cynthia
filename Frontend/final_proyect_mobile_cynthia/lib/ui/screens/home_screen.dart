import 'package:final_proyect_mobile_cynthia/bloc/composition_bloc/composition_bloc.dart';
import 'package:final_proyect_mobile_cynthia/bloc/composition_bloc/composition_event.dart';
import 'package:final_proyect_mobile_cynthia/bloc/composition_bloc/composition_state.dart';
import 'package:final_proyect_mobile_cynthia/models/comment_dto.dart';
import 'package:final_proyect_mobile_cynthia/models/comments_model.dart';
import 'package:final_proyect_mobile_cynthia/models/composition_model.dart';
import 'package:final_proyect_mobile_cynthia/repository/comments_repository/comments_repositoryImpl.dart';
import 'package:final_proyect_mobile_cynthia/repository/composition_repository/composition_repository.dart';
import 'package:final_proyect_mobile_cynthia/repository/composition_repository/composition_repositoryImp.dart';
import 'package:final_proyect_mobile_cynthia/styles/styles.dart';
import 'package:final_proyect_mobile_cynthia/ui/screens/error_page.dart';
import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:intl/intl.dart';
import 'package:shared_preferences/shared_preferences.dart';

class HomeScreen extends StatefulWidget {
  const HomeScreen({Key? key}) : super(key: key);

  @override
  _HomeScreenState createState() => _HomeScreenState();
}

class _HomeScreenState extends State<HomeScreen> {
  late CompositionRepository compositionRepository;
  final prefs = SharedPreferences.getInstance();
  String nick = '';
  String id = '';
  List<CommentsModel> comments = [];
  final _formKey = GlobalKey<FormState>();
  TextEditingController text = TextEditingController();

  @override
  void initState() {
    super.initState();
    _getUserLogin();
    _getComments(this.id);
    compositionRepository = CompositionRepositoryImpl();
  }

  @override
  void dispose() {
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return BlocProvider(
      create: (context) {
        return CompositionBloc(compositionRepository)
          ..add(const FetchCompositionsPublicEvent());
      },
      child: Scaffold(body: _listCompositions(context)),
    );
  }

  _getUserLogin() async {
    SharedPreferences prefs = await SharedPreferences.getInstance();
    setState(() {
      nick = prefs.getString('nick') ?? '';
      id = prefs.getString('id') ?? '';
    });
  }

  _listCompositions(BuildContext context) {
    return BlocBuilder<CompositionBloc, CompositionState>(
        builder: (context, state) {
      if (state is CompositionInitial) {
        return const Center(child: CircularProgressIndicator());
      } else if (state is CompositionFetched) {
        return _createCompositionView(
            context, state.compositions.reversed.toList());
      } else if (state is CompositionFetchError) {
        return ErrorPage(
          message: state.message,
          retry: () {
            context
                .watch<CompositionBloc>()
                .add(const FetchCompositionsPublicEvent());
          },
        );
      } else {
        return const Text('Not support');
      }
    });
  }

  _getComments(String id) async {
    final com = await CommentsRepositoryImpl().fetchAllComments(id);
    setState(() {
      this.comments = com;
    });
  }

  _createCompositionView(
      BuildContext context, List<CompositionModel> compositions) {
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
                  height: 100,
                  child: ListView.builder(
                      scrollDirection: Axis.horizontal,
                      itemCount: compositions.length,
                      itemBuilder: (BuildContext context, int index) {
                        return Container(
                            margin: EdgeInsets.all(10),
                            width: 60,
                            height: 60,
                            decoration: BoxDecoration(
                                shape: BoxShape.circle,
                                boxShadow: [
                                  BoxShadow(
                                      color: Colors.black45,
                                      offset: Offset(0, 2),
                                      blurRadius: 6.0)
                                ]),
                            child: CircleAvatar(
                                child: ClipOval(
                              child: Image.network(
                                compositions[index].authorAvatar ??
                                    'https://www.gravatar.com/avatar/205e460b479e2e5b48aec07710c08d50',
                                fit: BoxFit.cover,
                                errorBuilder: (context, error, stackTrace) {
                                  return Container(
                                    width: 60,
                                    height: 60,
                                    color: Colors.purple,
                                    alignment: Alignment.center,
                                    child: const Text(
                                      'Whoops!',
                                      style: TextStyle(fontSize: 10),
                                    ),
                                  );
                                },
                              ),
                            )));
                      }),
                ),
                Container(
                  width: double.infinity,
                  height: 550,
                  child: ListView.builder(
                      scrollDirection: Axis.vertical,
                      itemCount: compositions.length,
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
                                            compositions[index].authorAvatar ??
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
                                    Padding(
                                      padding:
                                          const EdgeInsets.only(left: 10.0),
                                      child: Text(
                                          compositions[index].authorNick ?? '',
                                          style: TextStyle(
                                              fontSize: 20,
                                              color: Colors.white,
                                              fontWeight: FontWeight.bold)),
                                    ),
                                    if (compositions[index].authorNick == nick)
                                      Padding(
                                        padding:
                                            const EdgeInsets.only(left: 10.0),
                                        child: IconButton(
                                          onPressed: () => {
                                            BlocProvider.of<CompositionBloc>(
                                                    context)
                                                .add(DeleteCompositionEvent(
                                                    compositions[index].id ??
                                                        '')),
                                            Navigator.popAndPushNamed(
                                                context, '/menu')
                                          },
                                          iconSize: 20,
                                          icon: Icon(Icons.delete),
                                          color: Colors.white,
                                        ),
                                      ),
                                  ],
                                ),
                                Row(
                                  children: <Widget>[
                                    Padding(
                                      padding:
                                          const EdgeInsets.only(left: 30.0),
                                      child: Text(
                                          compositions[index].name ?? '',
                                          style:
                                              TextStyle(color: Colors.white)),
                                    ),
                                    Padding(
                                      padding:
                                          const EdgeInsets.only(left: 20.0),
                                      child: IconButton(
                                          color: Colors.white,
                                          onPressed: () => showModalBottomSheet(
                                              shape: RoundedRectangleBorder(
                                                borderRadius:
                                                    BorderRadius.vertical(
                                                  top: Radius.circular(30),
                                                ),
                                              ),
                                              context: context,
                                              builder: (context) => buildSheet(
                                                  compositions[index])),
                                          icon: Icon(
                                              Icons.remove_red_eye_rounded)),
                                    ),
                                    Text("View Champs",
                                        style: TextStyle(
                                            color: Colors.white, fontSize: 13)),
                                  ],
                                ),
                                Row(
                                  children: <Widget>[
                                    Padding(
                                      padding:
                                          const EdgeInsets.only(left: 30.0),
                                      child: Text(
                                        compositions[index].description ??
                                            'No have description',
                                        style: TextStyle(color: Colors.white),
                                      ),
                                    ),
                                  ],
                                ),
                                Row(
                                  mainAxisAlignment: MainAxisAlignment.start,
                                  children: <Widget>[
                                    Row(
                                      children: <Widget>[
                                        Padding(
                                          padding:
                                              const EdgeInsets.only(left: 20.0),
                                          child: IconButton(
                                              color: Colors.white,
                                              onPressed: () => {
                                                    CompositionRepositoryImpl()
                                                        .addVote(
                                                            compositions[index]
                                                                    .id ??
                                                                ''),
                                                    CompositionRepositoryImpl()
                                                        .deleteVote(
                                                            compositions[index]
                                                                    .id ??
                                                                ''),
                                                    Navigator.popAndPushNamed(
                                                        context, '/users'),
                                                    Navigator.popAndPushNamed(
                                                        context, '/menu'),
                                                  },
                                              icon: Icon(Icons.thumb_up)),
                                        ),
                                        Text(
                                            compositions[index]
                                                    .votes
                                                    ?.length
                                                    .toString() ??
                                                '0',
                                            style:
                                                TextStyle(color: Colors.white)),
                                      ],
                                    ),
                                    Row(
                                      children: <Widget>[
                                        IconButton(
                                            color: Colors.white,
                                            onPressed: () => {
                                                  _getComments(
                                                      compositions[index].id ??
                                                          ''),
                                                  showModalBottomSheet(
                                                      shape:
                                                          RoundedRectangleBorder(
                                                        borderRadius:
                                                            BorderRadius
                                                                .vertical(
                                                          top: Radius.circular(
                                                              30),
                                                        ),
                                                      ),
                                                      context: context,
                                                      builder: (context) =>
                                                          buildSheetComments(
                                                              comments,
                                                              compositions[
                                                                          index]
                                                                      .id ??
                                                                  '')),
                                                },
                                            icon: Icon(Icons.comment)),
                                        Text(
                                            compositions[index]
                                                    .comments
                                                    .toString() ??
                                                '0',
                                            style:
                                                TextStyle(color: Colors.white)),
                                      ],
                                    ),
                                  ],
                                ),
                                Row(
                                  children: <Widget>[
                                    Padding(
                                      padding: const EdgeInsets.all(30.0),
                                      child: Text(
                                          DateFormat.yMMMEd().format(
                                              DateTime.parse(
                                                  compositions[index].date ??
                                                      '')),
                                          style:
                                              TextStyle(color: Colors.white)),
                                    ),
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

  Widget buildSheetComments(List<CommentsModel> comments, String id) =>
      Column(children: <Widget>[
        Container(
          width: MediaQuery.of(context).size.width,
          height: 300,
          child: ListView.builder(
            scrollDirection: Axis.vertical,
            itemCount: comments.length,
            itemBuilder: (context, index) => Column(
              children: <Widget>[
                ListTile(
                  title: Text(comments[index].author!.nick ?? ''),
                  subtitle: Text(comments[index].text ?? ''),
                ),
                if (comments[index].composition!.authorNick == nick)
                  Padding(
                    padding: const EdgeInsets.only(left: 300),
                    child: IconButton(
                        color: Colors.purple,
                        onPressed: () => {
                              CommentsRepositoryImpl()
                                  .deleteComment(id, comments[index].id ?? ''),
                              Navigator.popAndPushNamed(context, '/menu')
                            },
                        icon: Icon(Icons.delete)),
                  ),
                Divider(
                  height: 2,
                ),
              ],
            ),
          ),
        ),
        Container(
            width: MediaQuery.of(context).size.width,
            height: 100,
            child: Form(
              key: _formKey,
              child: Column(
                children: [
                  TextFormField(
                    style: TextStyle(color: Colors.black),
                    controller: text,
                    decoration: InputDecoration(
                      focusedBorder: OutlineInputBorder(
                        borderRadius: BorderRadius.circular(30.0),
                        borderSide: BorderSide(color: pink, width: 1.0),
                      ),
                      labelText: 'Write your comment',
                      labelStyle: textBlack18,
                      hintText: 'Write your comment',
                      hintStyle: textBlack18,
                      enabledBorder: OutlineInputBorder(
                        borderRadius: BorderRadius.circular(30.0),
                        borderSide: BorderSide(color: pink, width: 1.0),
                      ),
                    ),
                  ),
                ],
              ),
            )),
        Container(
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
                    final commentDto = CommentDto(text: text.text);
                    CommentsRepositoryImpl()
                        .createComment(commentDto.text!, id);
                  }
                  Navigator.popAndPushNamed(context, '/users');
                  Navigator.popAndPushNamed(context, '/menu');
                },
                child:
                    const Text('Send', style: TextStyle(color: Colors.white)),
              ),
            ),
          ),
        ),
      ]);

  Widget buildSheet(CompositionModel? composition) => Container(
        child: Padding(
          padding: const EdgeInsets.all(30),
          child: ListView.builder(
            itemCount: composition!.champions?.length,
            itemBuilder: (BuildContext context, int index) {
              return Row(
                children: [
                  Padding(
                    padding: const EdgeInsets.only(top: 10, left: 30),
                    child: Image(
                      height: 40,
                      width: 40,
                      image: Image.asset(
                              'assets/images/champions/TFT5_${composition.champions?[index].name}.png')
                          .image,
                    ),
                  ),
                  Padding(
                    padding: const EdgeInsets.all(8.0),
                    child: Text(composition.champions![index].name ?? '',
                        style: TextStyle(
                            fontSize: 20,
                            color: Colors.purple,
                            fontWeight: FontWeight.bold)),
                  ),
                ],
              );
            },
          ),
        ),
      );
}
