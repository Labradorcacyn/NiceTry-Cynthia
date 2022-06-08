class ChampionModel {
  String? id;
  String? name;
  int? cost;
  String? description;
  List<Traits>? traits;

  ChampionModel({this.id, this.name, this.cost, this.description, this.traits});

  ChampionModel.fromJson(Map<String, dynamic> json) {
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
