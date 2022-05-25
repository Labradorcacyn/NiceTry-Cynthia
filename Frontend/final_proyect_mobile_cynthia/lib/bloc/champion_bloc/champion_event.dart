import 'package:equatable/equatable.dart';

abstract class ChampionEvent extends Equatable {
  const ChampionEvent();

  @override
  List<Object> get props => [];
}

class FetchChampionsPublicEvent extends ChampionEvent {
  const FetchChampionsPublicEvent();
}
