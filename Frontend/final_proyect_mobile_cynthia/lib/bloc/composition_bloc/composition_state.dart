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

class CompositionCreated extends CompositionState {
  final CompositionModel composition;
  const CompositionCreated(this.composition);

  @override
  List<Object> get props => [composition];
}

class CompositionCreateSuccess extends CompositionState {
  final CompositionModel composition;
  const CompositionCreateSuccess(this.composition);

  @override
  List<Object> get props => [composition];
}

class CompositionDeleteSuccess extends CompositionState {
  const CompositionDeleteSuccess();
}

class CompositionUpdateSuccess extends CompositionState {
  final CompositionModel composition;
  const CompositionUpdateSuccess(this.composition);

  @override
  List<Object> get props => [composition];
}

class CompositionCreateError extends CompositionState {
  final String message;
  const CompositionCreateError(this.message);

  @override
  List<Object> get props => [message];
}

class CompositionDeleteError extends CompositionState {
  final String message;
  const CompositionDeleteError(this.message);

  @override
  List<Object> get props => [message];
}

class CompositionUpdateError extends CompositionState {
  final String message;
  const CompositionUpdateError(this.message);

  @override
  List<Object> get props => [message];
}

class CompositionCreateLoading extends CompositionState {
  @override
  List<Object> get props => [];
}
