import 'package:final_proyect_mobile_cynthia/bloc/user_bloc/user_event.dart';
import 'package:final_proyect_mobile_cynthia/bloc/user_bloc/user_state.dart';
import 'package:final_proyect_mobile_cynthia/repository/user_repository/user_repository.dart';
import 'package:flutter_bloc/flutter_bloc.dart';

class UserBloc extends Bloc<UserEvent, UserState> {
  final UserRepository repository;

  UserBloc(this.repository) : super(USerInitial()) {
    on<FetchUsersPublicEvent>(_usersFetched);
  }

  void _usersFetched(
      FetchUsersPublicEvent event, Emitter<UserState> emit) async {
    try {
      final users = await repository.fetchAllUsers();
      emit(UserFetched(users));
      return;
    } on Exception catch (e) {
      emit(UserFetchError(e.toString()));
    }
  }
}
