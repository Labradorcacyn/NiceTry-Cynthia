import 'package:equatable/equatable.dart';
import 'package:final_proyect_mobile_cynthia/models/profile_model.dart';

abstract class ProfileState extends Equatable {
  const ProfileState();

  @override
  List<Object> get props => [];
}

class ProfileInitial extends ProfileState {}

class ProfileFetched extends ProfileState {
  final ProfileModel profile;

  const ProfileFetched(this.profile);

  @override
  List<Object> get props => [profile];
}

class ProfileFetchError extends ProfileState {
  final String message;
  const ProfileFetchError(this.message);

  @override
  List<Object> get props => [message];
}

class ProfileOneFetched extends ProfileState {}

class ProfileOneFetchError extends ProfileState {}
