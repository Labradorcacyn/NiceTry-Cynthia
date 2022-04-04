import 'package:final_proyect_mobile_cynthia/models/login_dto.dart';
import 'package:final_proyect_mobile_cynthia/models/login_response.dart';
import 'package:final_proyect_mobile_cynthia/models/register_dto.dart';
import 'package:final_proyect_mobile_cynthia/models/register_response.dart';

abstract class AuthRepository {
  Future<LoginResponse> login(LoginDto loginDto);
  Future<RegisterResponse> register(RegisterDto registerDto, String ImagePath);
}
