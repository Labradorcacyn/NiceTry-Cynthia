import 'dart:convert';

import 'package:final_proyect_mobile_cynthia/models/login_dto.dart';
import 'package:final_proyect_mobile_cynthia/models/login_response.dart';
import 'package:final_proyect_mobile_cynthia/models/register_dto.dart';
import 'package:final_proyect_mobile_cynthia/models/register_response.dart';
import 'package:http/http.dart';
import 'package:http_parser/http_parser.dart';
import 'package:shared_preferences/shared_preferences.dart';
import 'auth_repository.dart';
import 'package:http/http.dart' as http;

class AuthRepositoryImpl extends AuthRepository {
  final Client _client = Client();

  @override
  Future<LoginResponse> login(LoginDto loginDto) async {
    SharedPreferences prefs = await SharedPreferences.getInstance();
    Map<String, String> headers = {
      'Content-Type': 'application/json',
      // 'Authorization': 'Bearer $token'
    };

    final response = await _client.post(
        Uri.parse('https://nicetry-api.herokuapp.com/auth/login'),
        headers: headers,
        body: jsonEncode(loginDto.toJson()));
    if (response.statusCode == 201) {
      return LoginResponse.fromJson(json.decode(response.body));
    } else {
      throw Exception(prefs.getString('nick').toString());
    }
  }

  @override
  Future<RegisterResponse> register(
      RegisterDto registerDto, String imagePath) async {
    Map<String, String> headers = {'Content-Type': 'multipart/form-data'};

    Map<String, String> headers2 = {
      'Content-Type': 'application/json',
    };

    SharedPreferences prefs = await SharedPreferences.getInstance();

    var uri = Uri.parse('https://nicetry-api.herokuapp.com/auth/register');
    var body = jsonEncode({
      'nick': registerDto.nick,
      'email': registerDto.email,
      'name': registerDto.name,
      'lastName': registerDto.lastName,
      'city': registerDto.city,
      'rol': registerDto.rol,
      'password': registerDto.password,
      'password2': registerDto.password2
    });
    var request = http.MultipartRequest('POST', uri)
      ..files.add(http.MultipartFile.fromString('body', body,
          contentType: MediaType('application', 'json')))
      ..files.add(await http.MultipartFile.fromPath('file', imagePath,
          contentType: MediaType('image', 'jpg')))
      ..headers.addAll(headers);

    var response = await request.send();

    if (response.statusCode == 201) {
      return RegisterResponse.fromJson(
          jsonDecode(await response.stream.bytesToString()));
    } else {
      print(response.statusCode);
      throw Exception('Ha ocurrido un error');
    }
  }
}
