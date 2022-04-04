import 'dart:convert';

import 'package:final_proyect_mobile_cynthia/models/login_dto.dart';
import 'package:final_proyect_mobile_cynthia/models/login_response.dart';
import 'package:final_proyect_mobile_cynthia/models/register_dto.dart';
import 'package:final_proyect_mobile_cynthia/models/register_response.dart';
import 'package:http/http.dart';
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
        Uri.parse('http://10.0.2.2:8080/auth/login'),
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
    Map<String, String> headers = {
      'Content-Type': 'multipart/form-data',
      // 'Authorization': 'Bearer $token'
    };

    Map<String, String> headers2 = {
      'Content-Type': 'application/json',
    };

    SharedPreferences prefs = await SharedPreferences.getInstance();

    var uri = Uri.parse('http://10.0.2.2:8080/auth/register');
    var request = http.MultipartRequest('POST', uri);
    request.fields['nombre'] = registerDto.name.toString();
    request.fields['apellidos'] = registerDto.lastName.toString();
    request.fields['nick'] = registerDto.nick.toString();
    request.fields['email'] = registerDto.email.toString();
    request.fields['fechaNacimiento'] = registerDto.city.toString();
    request.fields['rol'] = registerDto.rol.toString();
    request.fields['password'] = registerDto.password.toString();
    request.fields['password2'] = registerDto.password2.toString();
    request.files.add(await http.MultipartFile.fromPath(
        'file', prefs.getString('file').toString()));

    var response = await request.send();
    if (response.statusCode == 201) print('Uploaded!');

    if (response.statusCode == 201) {
      return RegisterResponse.fromJson(
          jsonDecode(await response.stream.bytesToString()));
    } else {
      print(response.statusCode);
      throw Exception('Ha ocurrido un error');
    }
  }
}
