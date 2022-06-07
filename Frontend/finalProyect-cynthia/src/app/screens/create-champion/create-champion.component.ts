import { Component, OnInit } from '@angular/core';
import { MatCheckboxChange } from '@angular/material/checkbox';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { ChampionDto } from 'src/app/models/dto/champion.dto';
import { TraitResponse } from 'src/app/models/interfaces/traits.interface';
import { ChampionService } from 'src/app/services/champion.service';
import { TraitService } from 'src/app/services/trait.service';

@Component({
  selector: 'app-create-champion',
  templateUrl: './create-champion.component.html',
  styleUrls: ['./create-champion.component.css']
})
export class CreateChampionComponent implements OnInit {

  championDto = new ChampionDto();

  traits: TraitResponse[] | undefined;

  idEdit!: string;

  constructor(private championService: ChampionService, private traitService: TraitService, private toastSvc: ToastrService, private router: Router) { }

  ngOnInit(): void {
    this.traitService.getTraits().subscribe(traits => {
      this.traits = traits;
    });

    if(this.router.url.includes("edit")){
      this.idEdit = this.router.url.split("/")[3];
      this.championService.getChampion(this.idEdit).subscribe(findChampion => {
        this.championDto.name = findChampion.name;
        this.championDto.description = findChampion.description;
        this.championDto.cost = findChampion.cost;
      });
    }
  }

  addChampion(){
    if(this.router.url.includes("edit")){
      this.editChampion();
      this.toastSvc.success('Edited champion', 'Success');
      window.location.reload();
      this.router.navigate(["/champions"]);
    }else{
      this.createChampion();
      this.toastSvc.success('Created champion', 'Success');
      window.location.reload();
      this.router.navigate(["/champions"]);
    }
  }

  editChampion(){
    if (this.championDto.name=="" ||
        this.championDto.description=="" ||
        this.championDto.cost==0||
        this.championDto.traitsUuid.length == 0) {
      this.toastSvc.error('You must enter all fields', 'Error');
    }
    else{
      this.championService.updateChampion(this.championDto, this.idEdit).subscribe( championDto => {
        this.championDto.name = championDto.name;
        this.championDto.description = championDto.description;
        this.championDto.cost = championDto.cost;
      });
    }
  }

  createChampion(){
    if( this.championDto.name=="" ||
        this.championDto.description=="" ||
        this.championDto.cost==0 ||
        this.championDto.traitsUuid.length == 0){
      this.toastSvc.error('You must enter all fields', 'Error');
    }else{
      this.championService.createChampion(this.championDto).subscribe(championDto => {
        this.championDto.name = championDto.name;
        this.championDto.description = championDto.description;
        this.championDto.cost = championDto.cost;
      });
    }
  }

  onChkChange(event: MatCheckboxChange, trait: TraitResponse){
    if(event.checked){
      if(!this.championDto.traitsUuid.includes(trait.id)){
      this.championDto.traitsUuid.push(trait.id);
      }
    }
  }
}
