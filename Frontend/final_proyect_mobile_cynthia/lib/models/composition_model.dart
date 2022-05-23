class CompositionModel {
  final String? id;
  final String? name;
  final String? description;
  final String? authorNick;
  final String? authorAvatar;
  final String? date;
  final List<Champions>? champions;
  final List<dynamic>? votes;
  final int? comments;

  CompositionModel({
    this.id,
    this.name,
    this.description,
    this.authorNick,
    this.authorAvatar,
    this.date,
    this.champions,
    this.votes,
    this.comments,
  });

  CompositionModel.fromJson(Map<String, dynamic> json)
      : id = json['id'] as String?,
        name = json['name'] as String?,
        description = json['description'] as String?,
        authorNick = json['authorNick'] as String?,
        authorAvatar = json['authorAvatar'] as String?,
        date = json['date'] as String?,
        champions = (json['champions'] as List?)
            ?.map((dynamic e) => Champions.fromJson(e as Map<String, dynamic>))
            .toList(),
        votes = json['votes'] as List?,
        comments = json['comments'] as int?;

  Map<String, dynamic> toJson() => {
        'id': id,
        'name': name,
        'description': description,
        'authorNick': authorNick,
        'authorAvatar': authorAvatar,
        'date': date,
        'champions': champions?.map((e) => e.toJson()).toList(),
        'votes': votes,
        'comments': comments
      };
}

class Champions {
  final String? id;
  final String? name;
  final int? cost;
  final List<Traits>? traits;
  final String? avatar;

  Champions({
    this.id,
    this.name,
    this.cost,
    this.traits,
    this.avatar,
  });

  Champions.fromJson(Map<String, dynamic> json)
      : id = json['id'] as String?,
        name = json['name'] as String?,
        cost = json['cost'] as int?,
        traits = (json['traits'] as List?)
            ?.map((dynamic e) => Traits.fromJson(e as Map<String, dynamic>))
            .toList(),
        avatar = json['avatar'] as String?;

  Map<String, dynamic> toJson() => {
        'id': id,
        'name': name,
        'cost': cost,
        'traits': traits?.map((e) => e.toJson()).toList(),
        'avatar': avatar
      };
}

class Traits {
  final int? id;
  final String? name;
  final String? description;
  final String? avatar;

  Traits({
    this.id,
    this.name,
    this.description,
    this.avatar,
  });

  Traits.fromJson(Map<String, dynamic> json)
      : id = json['id'] as int?,
        name = json['name'] as String?,
        description = json['description'] as String?,
        avatar = json['avatar'] as String?;

  Map<String, dynamic> toJson() =>
      {'id': id, 'name': name, 'description': description, 'avatar': avatar};
}
