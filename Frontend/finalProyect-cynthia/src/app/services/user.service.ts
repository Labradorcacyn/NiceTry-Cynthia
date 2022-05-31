import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { UsersResponse } from '../models/interfaces/users.interface';

const DEFAULT_HEADERS = {
  headers: new HttpHeaders({
    'Content-Type': 'application/json',
    'Authorization': 'Bearer ' + localStorage.getItem('token')
  })
};

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient) { }

  getUsers() {
    return this.http.get<UsersResponse[]>(`${environment.apiBaseUrl}/users`, DEFAULT_HEADERS);
  }

  deleteUser(id: string) {
    return this.http.delete<any>(`${environment.apiBaseUrl}/user/${id}`, DEFAULT_HEADERS);
  }
}
