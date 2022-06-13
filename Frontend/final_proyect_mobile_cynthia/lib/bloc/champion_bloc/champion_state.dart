import 'package:equatable/equatable.dart';
import 'package:final_proyect_mobile_cynthia/models/champion_model.dart';

abstract class ChampionState extends Equatable {
  const ChampionState();

  @override
  List<Object> get props => [];
}

class ChampionInitial extends ChampionState {}

class ChampionFetched extends ChampionState {
  final List<ChampionModel> champions;

  const ChampionFetched(this.champions);

  @override
  List<Object> get props => [champions];
}

class ChampionFetchError extends ChampionState {
  final String message;
  const ChampionFetchError(this.message);

  @override
  List<Object> get props => [message];
}
