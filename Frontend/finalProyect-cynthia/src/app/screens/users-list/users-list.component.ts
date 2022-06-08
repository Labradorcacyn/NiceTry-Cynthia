import { Component, OnInit } from '@angular/core';
import { UsersResponse } from 'src/app/models/interfaces/users.interface';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-users-list',
  templateUrl: './users-list.component.html',
  styleUrls: ['./users-list.component.css']
})
export class UsersListComponent implements OnInit {

  users!: UsersResponse[];
  filtered!: UsersResponse[];
  nameUser!: string;
  p!: number;

  constructor(private userService: UserService) { }

  ngOnInit(): void {
    this.userService.getUsers().subscribe(
      (data) => {
        this.users = data;
        this.filtered = data;
      }
    );
  }

  getName(){
    this.filtered = this.users.filter((us) => us.name.toLowerCase().includes(this.nameUser.toLowerCase()));
  }
}
