import 'dart:convert';

import 'package:final_proyect_mobile_cynthia/models/composition_model.dart';
import 'package:final_proyect_mobile_cynthia/repository/composition_repository/composition_repository.dart';
import 'package:http/http.dart';
import 'package:shared_preferences/shared_preferences.dart';

class CompositionRepositoryImpl extends CompositionRepository {
  final Client _client = Client();

  @override
  Future<List<CompositionModel>> fetchAllComposition() async {
    Map<String, String> headers = {
      'Content-Type': 'application/json',
      'Authorization': 'Bearer ${await getToken()}'
    };
    final response = await _client.get(
      Uri.parse('https://nicetry-api.herokuapp.com/composition'),
      headers: headers,
    );

    if (response.statusCode == 200) {
      return List.from(json
          .decode(response.body)
          .map((e) => CompositionModel.fromJson(e))
          .toList());
    } else {
      throw Exception('Fail to load compositions');
    }
  }

  @override
  Future<CompositionModel> createComposition(
      CompositionModel composition) async {
    Map<String, String> headers = {
      'Content-Type': 'application/json',
      'Authorization': 'Bearer ${await getToken()}'
    };
    final response = await _client.post(
      Uri.parse('https://nicetry-api.herokuapp.com/composition'),
      headers: headers,
      body: json.encode(composition.toJson()),
    );

    if (response.statusCode == 201) {
      return CompositionModel.fromJson(json.decode(response.body));
    } else {
      throw Exception('Fail to create composition');
    }
  }

  @override
  Future<bool> deleteComposition(String id) async {
    Map<String, String> headers = {
      'Content-Type': 'application/json',
      'Authorization': 'Bearer ${await getToken()}'
    };
    final response = await _client.delete(
      Uri.parse('https://nicetry-api.herokuapp.com/composition/$id'),
      headers: headers,
    );

    if (response.statusCode == 200) {
      return true;
    } else {
      throw Exception('Fail to delete composition');
    }
  }

  @override
  Future<CompositionModel> updateComposition(
      CompositionModel composition, String id) async {
    Map<String, String> headers = {
      'Content-Type': 'application/json',
      'Authorization': 'Bearer ${await getToken()}'
    };
    final response = await _client.post(
      Uri.parse('https://nicetry-api.herokuapp.com/composition/${id}'),
      headers: headers,
      body: json.encode(composition.toJson()),
    );

    if (response.statusCode == 200) {
      return CompositionModel.fromJson(json.decode(response.body));
    } else {
      throw Exception('Fail to create composition');
    }
  }

  @override
  Future<CompositionModel> fetchCompositionById(String id) async {
    Map<String, String> headers = {
      'Content-Type': 'application/json',
      'Authorization': 'Bearer ${await getToken()}'
    };
    final response = await _client.get(
      Uri.parse('https://nicetry-api.herokuapp.com/composition/$id'),
      headers: headers,
    );

    if (response.statusCode == 200) {
      return CompositionModel.fromJson(json.decode(response.body));
    } else {
      throw Exception('Fail to load composition');
    }
  }

  getToken() {
    return SharedPreferences.getInstance().then((prefs) {
      return prefs.getString('token');
    });
  }
}
