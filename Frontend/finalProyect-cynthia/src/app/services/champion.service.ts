import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { ChampionResponse } from '../models/interfaces/champions.interface';


const DEFAULT_HEADERS = {
  headers: new HttpHeaders({
    'Content-Type': 'application/json',
    'Authorization': 'Bearer ' + localStorage.getItem('token')
  })
};
@Injectable({
  providedIn: 'root'
})
export class ChampionService {

  constructor(private http: HttpClient) { }

  getChampions() {
    return this.http.get<ChampionResponse[]>(`${environment.apiBaseUrl}/champions`, DEFAULT_HEADERS);
  }

  deleteChampion(id: string) {
    return this.http.delete(`${environment.apiBaseUrl}/champion/delete/${id}`, DEFAULT_HEADERS);
  }

  createChampion(champion: any) {
    return this.http.post(`${environment.apiBaseUrl}/champion/create`, champion, DEFAULT_HEADERS);
  }

  updateChampion(champion: any) {
    return this.http.put(`${environment.apiBaseUrl}/champion/update/${champion.id}`, champion, DEFAULT_HEADERS);
  }
}
