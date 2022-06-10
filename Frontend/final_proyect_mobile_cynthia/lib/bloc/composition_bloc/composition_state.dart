import 'package:equatable/equatable.dart';
import 'package:final_proyect_mobile_cynthia/models/composition_model.dart';

abstract class CompositionState extends Equatable {
  const CompositionState();

  @override
  List<Object> get props => [];
}

class CompositionInitial extends CompositionState {}

class CompositionFetched extends CompositionState {
  final List<CompositionModel> compositions;

  const CompositionFetched(this.compositions);

  @override
  List<Object> get props => [compositions];
}

class CompositionFetchError extends CompositionState {
  final String message;
  const CompositionFetchError(this.message);

  @override
  List<Object> get props => [message];
}

class CompositionOneFetched extends CompositionState {}

class CompositionOneFetchError extends CompositionState {}
