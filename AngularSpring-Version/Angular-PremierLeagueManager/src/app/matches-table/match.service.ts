import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { MatchesTableItem } from './matches-table-datasource';

@Injectable({
  providedIn: 'root'
})
export class MatchService {
  data: MatchesTableItem[];

  // retrieve match data from spring localhost
  retrieveAllMatches(){
    return this.http.get<MatchesTableItem []>("http://localhost:8080/allmatches")
  }
  constructor(private http: HttpClient) { }
}
