class CommentsModel {
  String? id;
  Author? author;
  Composition? composition;
  String? text;

  CommentsModel({this.id, this.author, this.composition, this.text});

  CommentsModel.fromJson(Map<String, dynamic> json) {
    id = json['id'];
    author =
        json['author'] != null ? new Author.fromJson(json['author']) : null;
    composition = json['composition'] != null
        ? new Composition.fromJson(json['composition'])
        : null;
    text = json['text'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = new Map<String, dynamic>();
    data['id'] = this.id;
    if (this.author != null) {
      data['author'] = this.author!.toJson();
    }
    if (this.composition != null) {
      data['composition'] = this.composition!.toJson();
    }
    data['text'] = this.text;
    return data;
  }
}

class Author {
  String? id;
  String? nick;
  String? avatar;

  Author({this.id, this.nick, this.avatar});

  Author.fromJson(Map<String, dynamic> json) {
    id = json['id'];
    nick = json['nick'];
    avatar = json['avatar'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = new Map<String, dynamic>();
    data['id'] = this.id;
    data['nick'] = this.nick;
    data['avatar'] = this.avatar;
    return data;
  }
}

class Composition {
  String? id;
  String? name;
  String? description;
  String? authorNick;
  String? authorAvatar;
  String? date;
  List<Champions>? champions;
  List<String>? votes;
  int? comments;

  Composition(
      {this.id,
      this.name,
      this.description,
      this.authorNick,
      this.authorAvatar,
      this.date,
      this.champions,
      this.votes,
      this.comments});

  Composition.fromJson(Map<String, dynamic> json) {
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
