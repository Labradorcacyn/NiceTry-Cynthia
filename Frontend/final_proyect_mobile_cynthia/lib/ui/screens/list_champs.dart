import 'package:final_proyect_mobile_cynthia/bloc/champion_bloc/champio_bloc.dart';
import 'package:final_proyect_mobile_cynthia/bloc/champion_bloc/champion_state.dart';
import 'package:final_proyect_mobile_cynthia/bloc/composition_bloc/composition_bloc.dart';
import 'package:final_proyect_mobile_cynthia/bloc/composition_bloc/composition_event.dart';
import 'package:final_proyect_mobile_cynthia/bloc/composition_bloc/composition_state.dart';
import 'package:final_proyect_mobile_cynthia/models/champion_model.dart';
import 'package:final_proyect_mobile_cynthia/models/comment_dto.dart';
import 'package:final_proyect_mobile_cynthia/models/comments_model.dart';
import 'package:final_proyect_mobile_cynthia/models/composition_model.dart';
import 'package:final_proyect_mobile_cynthia/repository/champion_repository/champion_repository.dart';
import 'package:final_proyect_mobile_cynthia/repository/champion_repository/champion_repositoryImp.dart';
import 'package:final_proyect_mobile_cynthia/repository/comments_repository/comments_repositoryImpl.dart';
import 'package:final_proyect_mobile_cynthia/repository/composition_repository/composition_repositoryImp.dart';
import 'package:final_proyect_mobile_cynthia/styles/styles.dart';
import 'package:final_proyect_mobile_cynthia/ui/screens/error_page.dart';
import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:intl/intl.dart';
import 'package:shared_preferences/shared_preferences.dart';

import '../../bloc/champion_bloc/champion_event.dart';

class ListChamps extends StatefulWidget {
  const ListChamps({Key? key}) : super(key: key);

  @override
  _ListChampsState createState() => _ListChampsState();
}

class _ListChampsState extends State<ListChamps> {
  late ChampionRepository championRepository;
  List<ChampionModel> champions = [];
  final prefs = SharedPreferences.getInstance();
  String nick = '';
  String id = '';

  @override
  void initState() {
    super.initState();
    _getUserLogin();
    championRepository = ChampionRepositoryImpl();
  }

  @override
  void dispose() {
    super.dispose();
  }

  _getChamps() async {
    final champs = await championRepository.fetchAllChampions();
    setState(() {
      this.champions = champs;
    });
  }

  @override
  Widget build(BuildContext context) {
    return BlocProvider(
      create: (context) {
        return ChampionBloc(championRepository)
          ..add(const FetchChampionsPublicEvent());
      },
      child: Scaffold(body: _listChampions(context)),
    );
  }

  _getUserLogin() async {
    SharedPreferences prefs = await SharedPreferences.getInstance();
    setState(() {
      nick = prefs.getString('nick') ?? '';
      id = prefs.getString('id') ?? '';
    });
  }

  _listChampions(BuildContext context) {
    return BlocBuilder<ChampionBloc, ChampionState>(builder: (context, state) {
      if (state is ChampionInitial) {
        return const Center(child: CircularProgressIndicator());
      } else if (state is ChampionFetched) {
        return _createChampionView(context, state.champions);
      } else {
        return const Text('Not support');
      }
    });
  }

  _createChampionView(BuildContext context, List<ChampionModel> champions) {
    return SizedBox(
        height: MediaQuery.of(context).size.height,
        width: MediaQuery.of(context).size.width,
        child: Container(
            child: ListView.builder(
                scrollDirection: Axis.vertical,
                itemCount: champions.length,
                itemBuilder: (BuildContext context, int index) {
                  return Card(
                      child: Column(
                          mainAxisAlignment: MainAxisAlignment.start,
                          crossAxisAlignment: CrossAxisAlignment.start,
                          children: <Widget>[
                        GestureDetector(
                          onTap: () {
                            Navigator.pushNamed(context, '/champ-detail',
                                arguments: champions[index]);
                          },
                          child: ListTile(
                            leading: CircleAvatar(
                              backgroundImage: NetworkImage(
                                  'https://ddragon.leagueoflegends.com/cdn/9.24.1/img/champion/${champions[index].name}.png'),
                            ),
                            title: Text(champions[index].name!),
                          ),
                        )
                      ]));
                })));
  }
}
