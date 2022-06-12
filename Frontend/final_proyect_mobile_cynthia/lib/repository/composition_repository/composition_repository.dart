import 'package:final_proyect_mobile_cynthia/models/composition_dto.dart';
import 'package:final_proyect_mobile_cynthia/models/composition_model.dart';

abstract class CompositionRepository {
  Future<List<CompositionModel>> fetchAllComposition();
  Future<CompositionModel> fetchCompositionById(String id);
  Future<CompositionModel> createComposition(CompositionDto composition);
  Future<CompositionModel> updateComposition(
      CompositionModel composition, String id);
  Future<bool> deleteComposition(String id);
}
