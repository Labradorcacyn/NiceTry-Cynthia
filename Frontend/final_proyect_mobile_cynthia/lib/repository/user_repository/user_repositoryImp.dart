import 'dart:convert';

import 'package:final_proyect_mobile_cynthia/models/all_user_model.dart';
import 'package:final_proyect_mobile_cynthia/models/profile_model.dart';
import 'package:final_proyect_mobile_cynthia/repository/user_repository/user_repository.dart';
import 'package:http/http.dart';
import 'package:shared_preferences/shared_preferences.dart';

class UserRepositoryImpl extends UserRepository {
  final Client _client = Client();

  @override
  Future<ProfileModel> fetchProfile() async {
    Map<String, String> headers = {
      'Content-Type': 'application/json',
      'Authorization': 'Bearer ${await getToken()}'
    };
    final response = await _client.get(
      Uri.parse('https://nicetry-api.herokuapp.com/myProfile'),
      headers: headers,
    );

    if (response.statusCode == 200) {
      return ProfileModel.fromJson(json.decode(response.body));
    } else {
      throw Exception('Fail to load your profile');
    }
  }

  getToken() {
    return SharedPreferences.getInstance().then((prefs) {
      return prefs.getString('token');
    });
  }

  @override
  Future<List<AllUserModel>> fetchAllUsers() async {
    Map<String, String> headers = {
      'Content-Type': 'application/json',
      'Authorization': 'Bearer ${await getToken()}'
    };
    final response = await _client.get(
      Uri.parse('https://nicetry-api.herokuapp.com/users'),
      headers: headers,
    );

    if (response.statusCode == 200) {
      return List.from(json
          .decode(response.body)
          .map((e) => AllUserModel.fromJson(e))
          .toList());
    } else {
      throw Exception('Fail to load users');
    }
  }
}
