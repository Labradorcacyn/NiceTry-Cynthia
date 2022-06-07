import { Component, OnInit } from '@angular/core';
import { TraitResponse } from 'src/app/models/interfaces/traits.interface';
import { TraitService } from 'src/app/services/trait.service';

@Component({
  selector: 'app-trait-list',
  templateUrl: './trait-list.component.html',
  styleUrls: ['./trait-list.component.css']
})
export class TraitListComponent implements OnInit {

  traits!: TraitResponse[];
  filtered!: TraitResponse[];
  nameTr!: string;
  p!: number;

  constructor(private traitService: TraitService) { }

  ngOnInit(): void {
    this.traitService.getTraits().subscribe(traits => {
      this.traits = traits;
      this.filtered = traits;
    });
  }

  getName(){
      this.filtered = this.traits.filter((tr) => tr.name.toLowerCase().includes(this.nameTr.toLowerCase()));
  }
}
