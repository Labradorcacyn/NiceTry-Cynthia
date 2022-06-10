class AllUserModel {
  String? id;
  String? name;
  String? lastName;
  String? nick;
  String? avatar;

  AllUserModel({this.id, this.name, this.lastName, this.nick, this.avatar});

  AllUserModel.fromJson(Map<String, dynamic> json) {
    id = json['id'];
    name = json['name'];
    lastName = json['lastName'];
    nick = json['nick'];
    avatar = json['avatar'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = new Map<String, dynamic>();
    data['id'] = this.id;
    data['name'] = this.name;
    data['lastName'] = this.lastName;
    data['nick'] = this.nick;
    data['avatar'] = this.avatar;
    return data;
  }
}
