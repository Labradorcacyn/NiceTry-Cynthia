import 'package:final_proyect_mobile_cynthia/models/composition_model.dart';

abstract class CompositionRepository {
  Future<List<CompositionModel>> fetchAllComposition();
}
