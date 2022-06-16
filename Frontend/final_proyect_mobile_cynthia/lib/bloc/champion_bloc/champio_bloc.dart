import 'package:final_proyect_mobile_cynthia/bloc/champion_bloc/champion_event.dart';
import 'package:final_proyect_mobile_cynthia/bloc/champion_bloc/champion_state.dart';
import 'package:final_proyect_mobile_cynthia/repository/champion_repository/champion_repository.dart';
import 'package:flutter_bloc/flutter_bloc.dart';

class ChampionBloc extends Bloc<ChampionEvent, ChampionState> {
  final ChampionRepository repository;

  ChampionBloc(this.repository) : super(ChampionInitial()) {
    on<FetchChampionsPublicEvent>(_championsFetched);
  }

  void _championsFetched(
      FetchChampionsPublicEvent event, Emitter<ChampionState> emit) async {
    try {
      final champions = await repository.fetchAllChampions();
      emit(ChampionFetched(champions));
      return;
    } on Exception catch (e) {
      emit(ChampionFetchError(e.toString()));
    }
  }
}
