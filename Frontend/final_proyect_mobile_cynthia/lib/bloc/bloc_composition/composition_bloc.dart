import 'package:http/http.dart' as http;

import 'package:final_proyect_mobile_cynthia/bloc/bloc_composition/composition_event.dart';
import 'package:final_proyect_mobile_cynthia/bloc/bloc_composition/composition_state.dart';
import 'package:final_proyect_mobile_cynthia/repository/composition_repository/composition_repository.dart';
import 'package:flutter_bloc/flutter_bloc.dart';

class CompositionBloc extends Bloc<CompositionEvent, CompositionState> {
  final CompositionRepository repository;

  CompositionBloc(this.repository) : super(CompositionInitial()) {
    on<FetchCompositionsPublicEvent>(_compositionsFetched);
  }

  void _compositionsFetched(FetchCompositionsPublicEvent event,
      Emitter<CompositionState> emit) async {
    try {
      final movies = await repository.fetchAllComposition();
      emit(CompositionFetched(movies));
      return;
    } on Exception catch (e) {
      emit(CompositionFetchError(e.toString()));
    }
  }
}
