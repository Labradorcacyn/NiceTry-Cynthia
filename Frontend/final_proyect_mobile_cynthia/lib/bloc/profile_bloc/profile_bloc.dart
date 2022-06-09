import 'package:final_proyect_mobile_cynthia/bloc/profile_bloc/profile_bloc_event.dart';
import 'package:final_proyect_mobile_cynthia/bloc/profile_bloc/profile_bloc_state.dart';
import 'package:final_proyect_mobile_cynthia/repository/user_repository/user_repository.dart';
import 'package:flutter_bloc/flutter_bloc.dart';

class ProfileBloc extends Bloc<ProfileEvent, ProfileState> {
  final UserRepository repository;

  ProfileBloc(this.repository) : super(ProfileInitial()) {
    on<FetchProfilePublicEvent>(_profileFetched);
  }

  void _profileFetched(FetchProfilePublicEvent event,
      Emitter<ProfileState> emit) async {
    try {
      final profile = await repository.fetchProfile();
      emit(ProfileFetched(profile));
      return;
    } on Exception catch (e) {
      emit(ProfileFetchError(e.toString()));
    }
  }
}
