import { Component, Input, OnInit } from '@angular/core';
import { TraitService } from 'src/app/services/trait.service';

@Component({
  selector: 'app-trait-item',
  templateUrl: './trait-item.component.html',
  styleUrls: ['./trait-item.component.css']
})
export class TraitItemComponent implements OnInit {

  @Input() trait: any;

  constructor(private traitService: TraitService) { }

  ngOnInit(): void {
  }

  deleteTrait(id: string) {
    this.traitService.deleteTrait(id);
  }

}
