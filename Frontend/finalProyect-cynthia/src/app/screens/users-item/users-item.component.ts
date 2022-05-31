import { Component, Input, OnInit } from '@angular/core';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-users-item',
  templateUrl: './users-item.component.html',
  styleUrls: ['./users-item.component.css']
})
export class UsersItemComponent implements OnInit {

  @Input () user: any;

  constructor(private userService: UserService) { }

  ngOnInit(): void {
  }

  deleteUser(id: any) {
  this.userService.deleteUser(id);
  }
}
