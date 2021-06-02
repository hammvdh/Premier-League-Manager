import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { PlayedTableItem } from './played-table-datasource';

@Injectable({
  providedIn: 'root'
})
export class PlayedService {
  data: PlayedTableItem[];
  retrievePlayedMatch(){
    return this.http.get<PlayedTableItem []>("http://localhost:8080/playedmatch")
  }
  constructor(private http: HttpClient) { }
}
