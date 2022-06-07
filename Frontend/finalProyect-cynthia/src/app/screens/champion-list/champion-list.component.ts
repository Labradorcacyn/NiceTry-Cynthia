import { Component, OnInit } from '@angular/core';
import { ChampionResponse } from 'src/app/models/interfaces/champions.interface';
import { ChampionService } from 'src/app/services/champion.service';

@Component({
  selector: 'app-champion-list',
  templateUrl: './champion-list.component.html',
  styleUrls: ['./champion-list.component.css']
})
export class ChampionListComponent implements OnInit {

  champions!: ChampionResponse[];
  filtered!: ChampionResponse[];
  nameTr!: string;
  p!: number;

  constructor(private championService: ChampionService) { }

  ngOnInit(): void {
    this.championService.getChampions().subscribe(champions => {
      this.champions = champions;
      this.filtered = champions;
    });
  }

  getName(){
    this.filtered = this.champions.filter((ch) => ch.name.toLowerCase().includes(this.nameTr.toLowerCase()));
}
}
