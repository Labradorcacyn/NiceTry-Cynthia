class CompositionModel {
  CompositionModel({
    required this.id,
    required this.name,
    required this.description,
    required this.authorName,
    required this.date,
    required this.champions,
    required this.votes,
  });
  late final String id;
  late final String name;
  late final String description;
  late final String authorName;
  late final String date;
  late final List<Champions> champions;
  late final List<dynamic> votes;

  CompositionModel.fromJson(Map<String, dynamic> json) {
    id = json['id'];
    name = json['name'];
    description = json['description'];
    authorName = json['authorName'];
    date = json['date'];
    champions =
        List.from(json['champions']).map((e) => Champions.fromJson(e)).toList();
    votes = List.castFrom<dynamic, dynamic>(json['votes']);
  }

  Map<String, dynamic> toJson() {
    final _data = <String, dynamic>{};
    _data['id'] = id;
    _data['name'] = name;
    _data['description'] = description;
    _data['authorName'] = authorName;
    _data['date'] = date;
    _data['champions'] = champions.map((e) => e.toJson()).toList();
    _data['votes'] = votes;
    return _data;
  }
}

class Champions {
  Champions({
    required this.id,
    required this.name,
    required this.cost,
    required this.traits,
    required this.avatar,
  });
  late final String id;
  late final String name;
  late final int cost;
  late final List<dynamic> traits;
  late final String avatar;

  Champions.fromJson(Map<String, dynamic> json) {
    id = json['id'];
    name = json['name'];
    cost = json['cost'];
    traits = List.castFrom<dynamic, dynamic>(json['traits']);
    avatar = json['avatar'];
  }

  Map<String, dynamic> toJson() {
    final _data = <String, dynamic>{};
    _data['id'] = id;
    _data['name'] = name;
    _data['cost'] = cost;
    _data['traits'] = traits;
    _data['avatar'] = avatar;
    return _data;
  }
}
