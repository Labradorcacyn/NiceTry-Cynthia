class CompositionDto {
  String? name;
  String? description;
  List<String>? champions;

  CompositionDto({this.name, this.description, this.champions});

  CompositionDto.fromJson(Map<String, dynamic> json) {
    name = json['name'];
    description = json['description'];
    champions = json['champions'].cast<String>();
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = <String, dynamic>{};
    data['name'] = name;
    data['description'] = description;
    data['champions'] = champions;
    return data;
  }
}
