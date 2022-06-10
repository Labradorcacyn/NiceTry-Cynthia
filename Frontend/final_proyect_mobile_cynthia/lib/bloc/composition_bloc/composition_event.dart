import 'package:equatable/equatable.dart';

abstract class CompositionEvent extends Equatable {
  const CompositionEvent();

  @override
  List<Object> get props => [];
}

class FetchCompositionsPublicEvent extends CompositionEvent {
  const FetchCompositionsPublicEvent();
}
