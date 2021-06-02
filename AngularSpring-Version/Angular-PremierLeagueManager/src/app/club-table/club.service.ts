import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { ClubTableItem } from './club-table-datasource';

@Injectable({
  providedIn: 'root'
})
export class ClubService {

  data: ClubTableItem[];

  retrieveAllClubs(){
    return this.http.get<ClubTableItem []>("http://localhost:8080/allclubs")
  }

  retrieveClubsbyWins(){
    return this.http.get<ClubTableItem []>("http://localhost:8080/allclubs/sortbywins")
  }

  retrieveClubsbyPoints(){
    return this.http.get<ClubTableItem []>("http://localhost:8080/allclubs/sortbypoints")
  }

  retrieveClubsbyGoals(){
    return this.http.get<ClubTableItem []>("http://localhost:8080/allclubs/sortbygoals")
  }

  constructor(private http: HttpClient) { }


}
