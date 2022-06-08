import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { CompositionResponse } from '../models/interfaces/composition.interface';

const DEFAULT_HEADERS = {
  headers: new HttpHeaders({
    'Content-Type': 'application/json',
    'Authorization': 'Bearer ' + localStorage.getItem('token')
  })
};

@Injectable({
  providedIn: 'root'
})
export class CompositionService {

  constructor(private http: HttpClient) {
  }

  getCompositions() {
    return this.http.get<CompositionResponse[]>(`${environment.apiBaseUrl}/composition`, DEFAULT_HEADERS);
  }

  deleteComposition(id: string) {
    return this.http.delete(`${environment.apiBaseUrl}/composition/${id}`, DEFAULT_HEADERS);
  }
}
