class RegisterResponse {
  RegisterResponse({
    required this.id,
    required this.name,
    required this.lastName,
    required this.nick,
    required this.city,
    required this.email,
    required this.avatar,
    required this.userRoles,
  });
  late final String id;
  late final String name;
  late final String lastName;
  late final String nick;
  late final String city;
  late final String email;
  late final String avatar;
  late final String userRoles;

  RegisterResponse.fromJson(Map<String, dynamic> json) {
    id = json['id'];
    name = json['name'];
    lastName = json['lastName'];
    nick = json['nick'];
    city = json['city'];
    email = json['email'];
    avatar = json['avatar'];
    userRoles = json['userRoles'];
  }

  Map<String, dynamic> toJson() {
    final _data = <String, dynamic>{};
    _data['id'] = id;
    _data['name'] = name;
    _data['lastName'] = lastName;
    _data['nick'] = nick;
    _data['city'] = city;
    _data['email'] = email;
    _data['avatar'] = avatar;
    _data['userRoles'] = userRoles;
    return _data;
  }
}
