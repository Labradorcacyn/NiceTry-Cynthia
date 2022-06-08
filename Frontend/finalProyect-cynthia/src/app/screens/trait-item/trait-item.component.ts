import { Component, Input, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { TraitService } from 'src/app/services/trait.service';

@Component({
  selector: 'app-trait-item',
  templateUrl: './trait-item.component.html',
  styleUrls: ['./trait-item.component.css']
})
export class TraitItemComponent implements OnInit {

  @Input() trait: any;

  constructor(private traitService: TraitService, private toastSvc: ToastrService, private router: Router) { }

  ngOnInit(): void {
  }

  deleteTrait(id: number) {
    this.traitService.deleteTrait(id).subscribe(
      (res: any) => {
        this.toastSvc.success('Eliminated trait', 'Éxito');
        window.location.reload();
      }
    );
  }

}
