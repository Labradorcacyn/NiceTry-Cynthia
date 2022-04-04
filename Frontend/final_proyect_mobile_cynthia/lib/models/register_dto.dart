class RegisterDto {
  String? nick;
  String? email;
  String? name;
  String? lastName;
  String? city;
  bool? rol;
  String? password;
  String? password2;

  RegisterDto(
      {this.nick,
      this.email,
      this.name,
      this.lastName,
      this.city,
      this.rol,
      this.password,
      this.password2});

  RegisterDto.fromJson(Map<String, dynamic> json) {
    nick = json['nick'];
    email = json['email'];
    name = json['name'];
    lastName = json['lastName'];
    city = json['city'];
    rol = json['rol'];
    password = json['password'];
    password2 = json['password2'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = <String, dynamic>{};
    data['nick'] = nick;
    data['email'] = email;
    data['name'] = name;
    data['lastName'] = lastName;
    data['city'] = city;
    data['rol'] = rol;
    data['password'] = password;
    data['password2'] = password2;
    return data;
  }
}
