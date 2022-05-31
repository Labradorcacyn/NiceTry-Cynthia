import { Component, OnInit } from '@angular/core';
import { ChampionDto } from 'src/app/models/dto/champion.dto';
import { ChampionService } from 'src/app/services/champion.service';

@Component({
  selector: 'app-create-champion',
  templateUrl: './create-champion.component.html',
  styleUrls: ['./create-champion.component.css']
})
export class CreateChampionComponent implements OnInit {

  championDto = new ChampionDto();

  constructor(private championService: ChampionService) { }

  ngOnInit(): void {
  }

  createChampion(){
    this.championService.createChampion(this.championDto);
  }

}
