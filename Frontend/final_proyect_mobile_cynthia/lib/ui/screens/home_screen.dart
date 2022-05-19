import 'package:final_proyect_mobile_cynthia/bloc/bloc_composition/composition_bloc.dart';
import 'package:final_proyect_mobile_cynthia/bloc/bloc_composition/composition_event.dart';
import 'package:final_proyect_mobile_cynthia/bloc/bloc_composition/composition_state.dart';
import 'package:final_proyect_mobile_cynthia/models/composition_model.dart';
import 'package:final_proyect_mobile_cynthia/repository/composition_repository/composition_repository.dart';
import 'package:final_proyect_mobile_cynthia/repository/composition_repository/composition_repositoryImp.dart';
import 'package:final_proyect_mobile_cynthia/ui/screens/error_page.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';

class HomeScreen extends StatefulWidget {
  HomeScreen({Key? key}) : super(key: key);

  @override
  _HomeScreenState createState() => _HomeScreenState();
}

class _HomeScreenState extends State<HomeScreen> {
  late CompositionRepository compositionRepository;

  @override
  void initState() {
    super.initState();
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

  _listCompositions(BuildContext context) {
    return BlocBuilder<CompositionBloc, CompositionState>(
        builder: (context, state) {
      if (state is CompositionInitial) {
        return const Center(child: CircularProgressIndicator());
      } else if (state is CompositionFetched) {
        return _createCompositionView(context, state.compositions);
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

  _createCompositionView(
      BuildContext context, List<CompositionModel> compositions) {
    return SizedBox(
      height: 800,
      width: MediaQuery.of(context).size.width,
      child: ListView(
        physics: AlwaysScrollableScrollPhysics(),
        children: <Widget>[
          Padding(
            padding: const EdgeInsets.all(8.0),
            child: Row(
              mainAxisAlignment: MainAxisAlignment.spaceBetween,
              children: <Widget>[
                Image.asset("assets/images/logo.png", width: 200),
                Row(
                  children: <Widget>[
                    IconButton(
                      onPressed: () => print('Agregar Publicación'),
                      iconSize: 20,
                      icon: Icon(Icons.add),
                      color: Colors.purple,
                    ),
                    IconButton(
                      onPressed: () => print('Ver interacciones'),
                      iconSize: 20,
                      icon: Icon(Icons.heart_broken),
                      color: Colors.purple,
                    ),
                    IconButton(
                      onPressed: () => print('Ver mensajes'),
                      iconSize: 20,
                      icon: Icon(Icons.message),
                      color: Colors.purple,
                    )
                  ],
                )
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
                  );
                }),
          ),
          Container(
            width: double.infinity,
            height: MediaQuery.of(context).size.height,
            child: ListView.builder(
                scrollDirection: Axis.vertical,
                itemCount: compositions.length,
                itemBuilder: (BuildContext context, int index) {
                  return Container(
                      margin: EdgeInsets.all(10),
                      width: double.infinity,
                      height: 610,
                      child: Column(
                        children: <Widget>[
                          Row(
                            children: <Widget>[
                              Padding(
                                padding: const EdgeInsets.all(8.0),
                                child:
                                    Text(compositions[index].name.toString()),
                              ),
                            ],
                          ),
                          Row(
                            mainAxisAlignment: MainAxisAlignment.start,
                            children: <Widget>[
                              Row(
                                children: <Widget>[
                                  IconButton(
                                      onPressed: () => print("Like post"),
                                      icon: Icon(Icons.thumb_up)),
                                  Text("450")
                                ],
                              ),
                              Row(
                                children: <Widget>[
                                  IconButton(
                                      onPressed: () => print("Comments"),
                                      icon: Icon(Icons.comment)),
                                  Text("45")
                                ],
                              ),
                            ],
                          ),
                        ],
                      ));
                }),
          ),
        ],
      ),
    );
  }
}
