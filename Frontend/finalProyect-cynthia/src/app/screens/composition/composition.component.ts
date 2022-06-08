import { Component, Input, OnInit } from '@angular/core';
import { CompositionService } from 'src/app/services/composition.service';

@Component({
  selector: 'app-composition',
  templateUrl: './composition.component.html',
  styleUrls: ['./composition.component.css']
})
export class CompositionComponent implements OnInit {

  @Input () composition: any;

  constructor(private compositionService: CompositionService) { }

  ngOnInit(): void {
  }

  deleteComposition(id: any) {
    this.compositionService.deleteComposition(id);
    window.location.reload();
  }
}
