import { Injectable } from '@angular/core';
import { ClubService } from './club.service';
import { MatTableDataSource } from '@angular/material/table';

// stating the variables to store the values for each table item
export interface ClubTableItem {
  clubName: string;
  numberOfWins: number;
  numberOfLosses: number;
  numberOfDraws: number;
  numberOfGoalsScored: number;
  numberOfGoalsReceived: number;
  numberOfClubPoints: number;
  numberOfMatchesPlayed: number;
}

// used for dependancy injection
@Injectable({
  providedIn: 'root',
})

export class ClubTableDataSource extends MatTableDataSource<ClubTableItem> {
  constructor(private service:ClubService) {
    super();
  }
}