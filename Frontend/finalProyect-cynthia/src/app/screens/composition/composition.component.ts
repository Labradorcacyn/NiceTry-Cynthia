import { Component, Input, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { CompositionService } from 'src/app/services/composition.service';

@Component({
  selector: 'app-composition',
  templateUrl: './composition.component.html',
  styleUrls: ['./composition.component.css']
})
export class CompositionComponent implements OnInit {

  @Input () composition: any;

  constructor(private compositionService: CompositionService, private toastSvc: ToastrService, private router: Router) { }

  ngOnInit(): void {
  }

  deleteComposition(id: any) {
    this.compositionService.deleteComposition(id).subscribe(
      (res: any) => {
        this.toastSvc.success('Eliminated composition', 'Success');
      });
    window.location.reload();
  }
  
  imgError($event: any){
    $event.target.src = '../../../assets/user.png';
  }
}
