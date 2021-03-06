import 'package:bloc/bloc.dart';
import 'package:equatable/equatable.dart';
import 'package:final_proyect_mobile_cynthia/models/register_dto.dart';
import 'package:final_proyect_mobile_cynthia/models/register_response.dart';
import 'package:final_proyect_mobile_cynthia/repository/auth_repository/auth_repository.dart';

import '../../models/register_dto.dart';

part 'register_event.dart';
part 'register_state.dart';

class RegisterBloc extends Bloc<RegisterEvent, RegisterState> {
  final AuthRepository authRepository;

  RegisterBloc(this.authRepository) : super(RegisterInitial()) {
    on<DoRegisterEvent>(_doRegisterEvent);
  }

  void _doRegisterEvent(
      DoRegisterEvent event, Emitter<RegisterState> emit) async {
    try {
      final registerResponse =
          await authRepository.register(event.registerDto, event.imagePath);
      emit(RegisterSuccessState(registerResponse));
      return;
    } on Exception catch (e) {
      emit(RegisterErrorState(e.toString()));
    }
  }
}
