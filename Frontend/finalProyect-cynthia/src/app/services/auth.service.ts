import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { AuthLoginDto, AuthSignUpDto } from '../models/dto/auth.dto';
import { AuthLoginResponse, AuthSignUpResponse } from '../models/interfaces/auth.interface';

const AUTH_BASE_URL = '/auth';
const DEFAULT_HEADERS = {
  headers: new HttpHeaders({
    'Content-Type': 'application/json',
    'Access-Control-Allow-Origin': '*',
    'Access-Control-Allow-Headers': 'Content-Type',
    'Access-Control-Max-Age': '3600',
    'Access-Control-Allow-Methods': 'GET,POST,OPTIONS,DELETE,PUT',
  })
}

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  authBaseUrl = `${environment.apiBaseUrl}${AUTH_BASE_URL}`;
  constructor(private http: HttpClient) { }

  login(loginDto: AuthLoginDto): Observable<AuthLoginResponse>{

    let requestUrl = `${this.authBaseUrl}/login`;
    return this.http.post<AuthLoginResponse>(requestUrl, loginDto, DEFAULT_HEADERS);
  }

  register(signUpDto: AuthSignUpDto, avatar?:File): Observable<AuthSignUpResponse> {

    const headers = new HttpHeaders({
      enctype: 'multipart/form-data',
      Accept: 'application/json',
      contentType: 'multipart/form-data'
    });

      let options = {
        headers: headers,
      };

    let requestUrl = `${environment.apiBaseUrl}${AUTH_BASE_URL}/register`;
    //let body = JSON.stringify(signUpDto);
    var body = JSON.stringify({
      'nick': signUpDto.nick,
      'email': signUpDto.email,
      'name': signUpDto.name,
      'lastName': signUpDto.lastName,
      'city': signUpDto.city,
      'rol': signUpDto.rol,
      'avatar': signUpDto.avatar,
      'password': signUpDto.password,
      'password2': signUpDto.password2
    });

    const formData = new FormData();
    formData.append('body', body);
    formData.append('file', avatar!);

    return this.http.post<AuthSignUpResponse>(requestUrl, formData, options);
  }
}
