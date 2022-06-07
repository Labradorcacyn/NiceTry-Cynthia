import { Component, Input, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { ChampionService } from 'src/app/services/champion.service';

@Component({
  selector: 'app-champion-item',
  templateUrl: './champion-item.component.html',
  styleUrls: ['./champion-item.component.css']
})
export class ChampionItemComponent implements OnInit {

  @Input() champion: any;

  constructor(private championService: ChampionService, private toastSvc: ToastrService, private router: Router) { }

  ngOnInit(): void {
  }

  deleteChampion(id: string) {
    this.championService.deleteChampion(id).subscribe(
      (res: any) => {
        this.toastSvc.success('Eliminated champion', 'Éxito');
        window.location.reload();
      }
    );
  }
}
