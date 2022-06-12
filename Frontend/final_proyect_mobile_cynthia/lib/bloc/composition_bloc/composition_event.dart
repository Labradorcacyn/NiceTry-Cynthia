import 'package:equatable/equatable.dart';
import 'package:final_proyect_mobile_cynthia/models/composition_dto.dart';
import 'package:final_proyect_mobile_cynthia/models/composition_model.dart';

abstract class CompositionEvent extends Equatable {
  const CompositionEvent();

  @override
  List<Object> get props => [];
}

class FetchCompositionsPublicEvent extends CompositionEvent {
  const FetchCompositionsPublicEvent();
}

class FetchCompositionsPrivateEvent extends CompositionEvent {
  const FetchCompositionsPrivateEvent();
}

class FetchCompositionByIdEvent extends CompositionEvent {
  final String id;

  const FetchCompositionByIdEvent(this.id);

  @override
  List<Object> get props => [id];
}

class CreateCompositionEvent extends CompositionEvent {
  final CompositionDto composition;

  const CreateCompositionEvent(this.composition);

  @override
  List<Object> get props => [composition];
}

class UpdateCompositionEvent extends CompositionEvent {
  final CompositionModel composition;
  final String id;

  const UpdateCompositionEvent(this.composition, this.id);

  @override
  List<Object> get props => [composition, id];
}

class DeleteCompositionEvent extends CompositionEvent {
  final String id;

  const DeleteCompositionEvent(this.id);

  @override
  List<Object> get props => [id];
}
