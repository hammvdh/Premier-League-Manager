
import { Injectable } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { MatchService } from './match.service';

export interface MatchesTableItem {
  homeClub:string;
  homeClubGoalsScored: number;
  date: string;
  awayClubGoalsScored: number;
  awayClub: string;
}

@Injectable({
  providedIn: 'root',
})

export class MatchesTableDataSource extends MatTableDataSource<MatchesTableItem> {

  constructor(private service:MatchService) {
    super();
  }
}


