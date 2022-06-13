import 'package:final_proyect_mobile_cynthia/models/comments_model.dart';
import 'package:final_proyect_mobile_cynthia/models/create_comment.dart';

abstract class CommentsRepository {
  Future<List<CommentsModel>> fetchAllComments(String id);
  Future<CreateComment> createComment(String idComposition, String comment);
}
