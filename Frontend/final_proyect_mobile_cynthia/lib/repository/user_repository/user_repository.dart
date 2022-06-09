import 'package:final_proyect_mobile_cynthia/models/all_user_model.dart';
import 'package:final_proyect_mobile_cynthia/models/profile_model.dart';

abstract class UserRepository {
  Future<ProfileModel> fetchProfile();
  Future<List<AllUserModel>> fetchAllUsers();
}
