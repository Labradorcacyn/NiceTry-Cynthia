import 'dart:convert';

import 'package:final_proyect_mobile_cynthia/models/comments_model.dart';
import 'package:final_proyect_mobile_cynthia/models/create_comment.dart';
import 'package:http/http.dart';
import 'package:shared_preferences/shared_preferences.dart';

import 'comments_repository.dart';

class CommentsRepositoryImpl extends CommentsRepository {
  final Client _client = Client();

  getToken() {
    return SharedPreferences.getInstance().then((prefs) {
      return prefs.getString('token');
    });
  }

  @override
  Future<List<CommentsModel>> fetchAllComments(String id) async {
    Map<String, String> headers = {
      'Content-Type': 'application/json',
      'Authorization': 'Bearer ${await getToken()}'
    };
    final response = await _client.get(
      Uri.parse('https://nicetry-api.herokuapp.com/$id/comments'),
      headers: headers,
    );

    if (response.statusCode == 200) {
      return List.from(json
          .decode(response.body)
          .map((e) => CommentsModel.fromJson(e))
          .toList());
    } else {
      throw Exception('Fail to load comments');
    }
  }

  deleteComment(String idComposition, String id) async {
    Map<String, String> headers = {
      'Content-Type': 'application/json',
      'Authorization': 'Bearer ${await getToken()}'
    };
    final response = _client.delete(
      Uri.parse(
          'https://nicetry-api.herokuapp.com/${idComposition}/comments/${id}'),
      headers: headers,
    );
  }

  @override
  Future<CreateComment> createComment(
      String comment, String idComposition) async {
    Map<String, String> headers = {
      'Content-Type': 'application/json',
      'Authorization': 'Bearer ${await getToken()}'
    };
    final response = await _client.post(
      Uri.parse('https://nicetry-api.herokuapp.com/$idComposition/comment'),
      headers: headers,
      body: json.encode({
        'text': comment,
      }),
    );

    if (response.statusCode == 201) {
      return CreateComment.fromJson(json.decode(response.body));
    } else {
      throw Exception('Fail to create comment');
    }
  }
}
