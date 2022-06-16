import 'package:final_proyect_mobile_cynthia/models/champion_model.dart';
import 'package:flutter/material.dart';

class DetailChampion extends StatelessWidget {
  // Declara un campo que contenga el objeto Todo
  final ChampionModel? champions;

  // En el constructor, se requiere un objeto Todo
  const DetailChampion({Key? key, this.champions}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    // Usa el objeto Todo para crear nuestra UI
    return Scaffold(
      appBar: AppBar(
        title: Text(champions!.name ?? 'Detalle de campeón'),
      ),
      body: Padding(
        padding: EdgeInsets.all(16.0),
        child: Text(champions!.description ?? 'Detalle de campeón'),
      ),
    );
  }
}
