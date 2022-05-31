import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {

  constructor(private router: Router) { }

  ngOnInit(): void {
  }

  logOut(){
    localStorage.removeItem('token');
    localStorage.removeItem('nombreUser');
    localStorage.removeItem('avatar');
    this.router.navigate(["/login"]);
  }

  createAdmin(){
    this.router.navigate(["/register"]);
  }

  getUsers(){
    this.router.navigate(["/users"]);
  }

  goHome(){
    this.router.navigate(["/home"]);
  }

  getChampions(){
    this.router.navigate(["/champions"]);
  }

  getTraits(){
    this.router.navigate(["/traits"]);
  }

  createChampion(){
    this.router.navigate(["/create-champion"]);
  }

  createTrait(){
    this.router.navigate(["/create-trait"]);
  }
}
