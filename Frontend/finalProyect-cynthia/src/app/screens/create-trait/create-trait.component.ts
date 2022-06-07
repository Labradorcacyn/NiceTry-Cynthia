import { HttpResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Toast, ToastrService } from 'ngx-toastr';
import { TraitDto } from 'src/app/models/dto/trait.dto';
import { CreateTraitResponse } from 'src/app/models/interfaces/traits.interface';
import { TraitService } from 'src/app/services/trait.service';

@Component({
  selector: 'app-create-trait',
  templateUrl: './create-trait.component.html',
  styleUrls: ['./create-trait.component.css']
})
export class CreateTraitComponent implements OnInit {

  trait = new TraitDto();
  idEdit!: number;

  constructor(private traitService: TraitService, private toastSvc: ToastrService, private router: Router) { }

  ngOnInit(): void {
    if(this.router.url.includes("edit")){
      this.idEdit = parseInt(this.router.url.split("/")[3]);
      this.traitService.getTrait(this.idEdit).subscribe(findTrait => {
        this.trait.name = findTrait.name;
        this.trait.description = findTrait.description;
      });
    }
  }

  createTrait(){
    if (this.trait.name=="" ||
        this.trait.description=="") {
      this.toastSvc.error('You must enter all fields', 'Error');
    }else{
        this.traitService.createTrait(this.trait).subscribe( traitDto => {
        this.trait = traitDto;
      });
    }
  }

  editTrait(){
    if (this.trait.name=="" ||
        this.trait.description=="") {
      this.toastSvc.error('You must enter all fields', 'Error');
    }
    else{
        this.traitService.updateTrait(this.trait, this.idEdit).subscribe( traitDto => {
          this.trait.name = traitDto.name;
          this.trait.description = traitDto.description;
        });
    }
  }

  addTrait(){
    if(this.router.url.includes("edit")){
      this.editTrait();
      this.toastSvc.success('Edited trait', 'Success');
      this.router.navigate(["/traits"]);
    }else{
      this.createTrait();
      this.toastSvc.success('Created trait', 'Success');
      this.router.navigate(["/traits"]);
    }
  }

}
