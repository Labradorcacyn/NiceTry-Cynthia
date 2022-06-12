import 'package:final_proyect_mobile_cynthia/repository/composition_repository/composition_repository.dart';
import 'package:flutter_bloc/flutter_bloc.dart';

import 'composition_event.dart';
import 'composition_state.dart';

class CompositionBloc extends Bloc<CompositionEvent, CompositionState> {
  final CompositionRepository repository;

  CompositionBloc(this.repository) : super(CompositionInitial()) {
    on<FetchCompositionsPublicEvent>(_compositionsFetched);
    on<DeleteCompositionEvent>(_compositionsDeleteFetched);
    on<CreateCompositionEvent>(_compositionCreated);
    on<UpdateCompositionEvent>(_compositionUpdated);
  }

  void _compositionsFetched(FetchCompositionsPublicEvent event,
      Emitter<CompositionState> emit) async {
    try {
      final composition = await repository.fetchAllComposition();
      emit(CompositionFetched(composition));
      return;
    } on Exception catch (e) {
      emit(CompositionFetchError(e.toString()));
    }
  }

  void _compositionsDeleteFetched(
      DeleteCompositionEvent event, Emitter<CompositionState> emit) async {
    try {
      final composition = await repository.deleteComposition(event.id);
      return emit(CompositionDeleteSuccess());
    } on Exception catch (e) {
      emit(CompositionDeleteError(e.toString()));
    }
  }

  void _compositionCreated(
      CreateCompositionEvent event, Emitter<CompositionState> emit) async {
    try {
      final composition = await repository.createComposition(event.composition);
      emit(CompositionCreateSuccess(composition));
      return;
    } on Exception catch (e) {
      emit(CompositionFetchError(e.toString()));
    }
  }

  void _compositionUpdated(
      UpdateCompositionEvent event, Emitter<CompositionState> emit) async {
    try {
      final composition =
          await repository.updateComposition(event.composition, event.id);
      emit(CompositionUpdateSuccess(composition));
      return;
    } on Exception catch (e) {
      emit(CompositionFetchError(e.toString()));
    }
  }
}
