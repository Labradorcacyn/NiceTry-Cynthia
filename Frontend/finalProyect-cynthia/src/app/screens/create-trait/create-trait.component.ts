import { Component, OnInit } from '@angular/core';
import { TraitDto } from 'src/app/models/dto/trait.dto';
import { TraitService } from 'src/app/services/trait.service';

@Component({
  selector: 'app-create-trait',
  templateUrl: './create-trait.component.html',
  styleUrls: ['./create-trait.component.css']
})
export class CreateTraitComponent implements OnInit {

  traitDto = new TraitDto();

  constructor(private traitService: TraitService) { }

  ngOnInit(): void {
  }

  createTrait(){
   this.traitService.createTrait(this.traitDto);
  }

}
