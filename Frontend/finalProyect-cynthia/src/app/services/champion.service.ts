import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { TraitDto } from '../models/dto/trait.dto';
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
    return this.http.delete<any>(`${environment.apiBaseUrl}/champion/delete/${id}`, DEFAULT_HEADERS);
  }

  createChampion(champion: TraitDto):Observable<ChampionResponse> {
    return this.http.post<ChampionResponse>(`${environment.apiBaseUrl}/champion/create`, champion, DEFAULT_HEADERS);
  }

  updateChampion(champion: TraitDto, id: string):Observable<ChampionResponse> {
    return this.http.put<ChampionResponse>(`${environment.apiBaseUrl}/champion/update/${id}`, champion, DEFAULT_HEADERS);
  }

  getChampion(id: string): Observable<ChampionResponse> {
    return this.http.get<ChampionResponse>(`${environment.apiBaseUrl}/champion/${id}`, DEFAULT_HEADERS);
  }
}
