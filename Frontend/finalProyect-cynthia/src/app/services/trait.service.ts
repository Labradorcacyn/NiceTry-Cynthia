import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { TraitDto } from '../models/dto/trait.dto';
import { TraitResponse } from '../models/interfaces/traits.interface';

const DEFAULT_HEADERS = {
  headers: new HttpHeaders({
    'Content-Type': 'application/json',
    'Authorization': 'Bearer ' + localStorage.getItem('token')
  })
};

@Injectable({
  providedIn: 'root'
})
export class TraitService {

  constructor(private http: HttpClient) { }

  getTraits() {
    return this.http.get<TraitResponse[]>(`${environment.apiBaseUrl}/traits`, DEFAULT_HEADERS);
  }

  deleteTrait(id: string) {
    return this.http.delete(`${environment.apiBaseUrl}/trait/delete/${id}`, DEFAULT_HEADERS);
  }

  createTrait(trait: TraitDto) {
    let params = new HttpParams().set('name', trait.name)
    .set('description', trait.description)
    return this.http.post(`${environment.apiBaseUrl}/trait/create`, {'params': params}, DEFAULT_HEADERS);
  }

  updateTrait(trait: any) {
    return this.http.put(`${environment.apiBaseUrl}/trait/update/${trait.id}`, trait, DEFAULT_HEADERS);
  }

  getTrait(id: string) {
    return this.http.get(`${environment.apiBaseUrl}/trait/${id}`, DEFAULT_HEADERS);
  }
}
