import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { TraitDto } from '../models/dto/trait.dto';
import { CreateTraitResponse, TraitResponse } from '../models/interfaces/traits.interface';

const DEFAULT_HEADERS = {
  headers: new HttpHeaders({
    'Content-Type': 'application/json',
    'Authorization': 'Bearer ' + localStorage.getItem('token')
  })
};

const HEADER_PUT = {
  headers: new HttpHeaders({
    'Content-Type': 'text/plain',
    'Access-Control-Allow-Origin': '*',
    'Access-Control-Allow-Credentials': 'true',
    'Accept': 'application/json, text/plain, */*',
    'Access-Control-Allow-Methods': 'GET, POST, PUT, DELETE, OPTIONS',
    'Access-Control-Allow-Headers': 'Origin, Content-Type, X-Auth-Token',
    'Authorization': 'Bearer ' + localStorage.getItem('token'),
  })
}

@Injectable({
  providedIn: 'root'
})
export class TraitService {

  constructor(private http: HttpClient) { }

  getTraits() {
    return this.http.get<TraitResponse[]>(`${environment.apiBaseUrl}/traits`, DEFAULT_HEADERS);
  }

  deleteTrait(id: number) {
    return this.http.delete<any>(`${environment.apiBaseUrl}/trait/delete/${id}`, DEFAULT_HEADERS);
  }

  createTrait(trait:TraitDto):Observable<CreateTraitResponse> {
    return this.http.post<CreateTraitResponse>(`${environment.apiBaseUrl}/trait/create`, trait, DEFAULT_HEADERS);
  }

  updateTrait(trait:TraitDto, id: number):Observable<CreateTraitResponse> {
    return this.http.put<CreateTraitResponse>(`${environment.apiBaseUrl}/trait/update/${id}/`, trait, HEADER_PUT);
  }

  getTrait(id: number):Observable<CreateTraitResponse> {
    return this.http.get<CreateTraitResponse>(`${environment.apiBaseUrl}/trait/id/${id}`, DEFAULT_HEADERS);
  }
}
