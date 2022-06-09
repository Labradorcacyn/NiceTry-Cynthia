import 'package:equatable/equatable.dart';
import 'package:final_proyect_mobile_cynthia/models/all_user_model.dart';

abstract class UserState extends Equatable {
  const UserState();

  @override
  List<Object> get props => [];
}

class USerInitial extends UserState {}

class UserFetched extends UserState {
  final List<AllUserModel> users;

  const UserFetched(this.users);

  @override
  List<Object> get props => [users];
}

class UserFetchError extends UserState {
  final String message;
  const UserFetchError(this.message);

  @override
  List<Object> get props => [message];
}

class UserOneFetched extends UserState {}

class UserOneFetchError extends UserState {}
