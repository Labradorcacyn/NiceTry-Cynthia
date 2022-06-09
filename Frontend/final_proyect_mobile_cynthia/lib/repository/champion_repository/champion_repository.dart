import 'package:final_proyect_mobile_cynthia/models/champion_model.dart';

abstract class ChampionRepository {
  Future<List<ChampionModel>> fetchAllChampions();
}
