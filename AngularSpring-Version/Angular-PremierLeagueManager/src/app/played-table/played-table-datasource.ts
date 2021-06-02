
import { Injectable } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { PlayedService } from './played.service';

export interface PlayedTableItem {
  homeClub:string;
  homeClubGoalsScored: number;
  date: string;
  awayClubGoalsScored: number;
  awayClub: string;
}

@Injectable({
  providedIn: 'root',
})

export class PlayedTableDataSource extends MatTableDataSource<PlayedTableItem> {
  constructor(private service:PlayedService) {
    super();
  }
}


