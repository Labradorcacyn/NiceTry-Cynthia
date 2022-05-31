import { Component, Input, OnInit } from '@angular/core';
import { ChampionService } from 'src/app/services/champion.service';

@Component({
  selector: 'app-champion-item',
  templateUrl: './champion-item.component.html',
  styleUrls: ['./champion-item.component.css']
})
export class ChampionItemComponent implements OnInit {

  @Input() champion: any;

  constructor(private championService: ChampionService) { }

  ngOnInit(): void {
  }

  deleteChampion(id: string) {
    this.championService.deleteChampion(id);
  }
}
