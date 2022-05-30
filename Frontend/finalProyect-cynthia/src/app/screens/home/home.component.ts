import { Component, OnInit } from '@angular/core';
import { CompositionResponse } from 'src/app/models/interfaces/composition.interface';
import { CompositionService } from 'src/app/services/composition.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  compositions: CompositionResponse[] | undefined;
  constructor(private compositionService: CompositionService) { }

  ngOnInit(): void {
    this.compositionService.getCompositions().subscribe(data => {
      this.compositions = data;
    });
  }

}
