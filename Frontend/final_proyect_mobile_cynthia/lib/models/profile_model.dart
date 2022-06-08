class ProfileModel {
  String? id;
  String? name;
  String? lastName;
  String? nick;
  String? email;
  String? avatar;
  String? userRoles;
  String? city;
  List<CompositionList>? compositionList;

  ProfileModel(
      {this.id,
      this.name,
      this.lastName,
      this.nick,
      this.email,
      this.avatar,
      this.userRoles,
      this.city,
      this.compositionList});

  ProfileModel.fromJson(Map<String, dynamic> json) {
    id = json['id'];
    name = json['name'];
    lastName = json['lastName'];
    nick = json['nick'];
    email = json['email'];
    avatar = json['avatar'];
    userRoles = json['userRoles'];
    city = json['city'];
    if (json['compositionList'] != null) {
      compositionList = <CompositionList>[];
      json['compositionList'].forEach((v) {
        compositionList!.add(new CompositionList.fromJson(v));
      });
    }
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = new Map<String, dynamic>();
    data['id'] = this.id;
    data['name'] = this.name;
    data['lastName'] = this.lastName;
    data['nick'] = this.nick;
    data['email'] = this.email;
    data['avatar'] = this.avatar;
    data['userRoles'] = this.userRoles;
    data['city'] = this.city;
    if (this.compositionList != null) {
      data['compositionList'] =
          this.compositionList!.map((v) => v.toJson()).toList();
    }
    return data;
  }
}

class CompositionList {
  String? id;
  String? name;
  String? description;
  String? authorNick;
  String? authorAvatar;
  String? date;
  List<Champions>? champions;
  List<String>? votes;
  int? comments;

  CompositionList(
      {this.id,
      this.name,
      this.description,
      this.authorNick,
      this.authorAvatar,
      this.date,
      this.champions,
      this.votes,
      this.comments});

  CompositionList.fromJson(Map<String, dynamic> json) {
    id = json['id'];
    name = json['name'];
    description = json['description'];
    authorNick = json['authorNick'];
    authorAvatar = json['authorAvatar'];
    date = json['date'];
    if (json['champions'] != null) {
      champions = <Champions>[];
      json['champions'].forEach((v) {
        champions!.add(new Champions.fromJson(v));
      });
    }
    votes = json['votes'].cast<String>();
    comments = json['comments'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = new Map<String, dynamic>();
    data['id'] = this.id;
    data['name'] = this.name;
    data['description'] = this.description;
    data['authorNick'] = this.authorNick;
    data['authorAvatar'] = this.authorAvatar;
    data['date'] = this.date;
    if (this.champions != null) {
      data['champions'] = this.champions!.map((v) => v.toJson()).toList();
    }
    data['votes'] = this.votes;
    data['comments'] = this.comments;
    return data;
  }
}

class Champions {
  String? id;
  String? name;
  int? cost;
  String? description;
  List<Traits>? traits;

  Champions({this.id, this.name, this.cost, this.description, this.traits});

  Champions.fromJson(Map<String, dynamic> json) {
    id = json['id'];
    name = json['name'];
    cost = json['cost'];
    description = json['description'];
    if (json['traits'] != null) {
      traits = <Traits>[];
      json['traits'].forEach((v) {
        traits!.add(new Traits.fromJson(v));
      });
    }
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = new Map<String, dynamic>();
    data['id'] = this.id;
    data['name'] = this.name;
    data['cost'] = this.cost;
    data['description'] = this.description;
    if (this.traits != null) {
      data['traits'] = this.traits!.map((v) => v.toJson()).toList();
    }
    return data;
  }
}

class Traits {
  int? id;
  String? name;
  String? description;

  Traits({this.id, this.name, this.description});

  Traits.fromJson(Map<String, dynamic> json) {
    id = json['id'];
    name = json['name'];
    description = json['description'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = new Map<String, dynamic>();
    data['id'] = this.id;
    data['name'] = this.name;
    data['description'] = this.description;
    return data;
  }
}
