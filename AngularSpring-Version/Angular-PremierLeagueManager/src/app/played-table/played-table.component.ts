import { AfterViewInit, Component, OnInit, ViewChild } from '@angular/core';

import { MatTable } from '@angular/material/table';
import { PlayedService } from './played.service';
import { PlayedTableDataSource, PlayedTableItem } from './played-table-datasource';


@Component({
  selector: 'app-played-table',
  templateUrl: './played-table.component.html',
  styleUrls: ['./played-table.component.css']
})
export class PlayedTableComponent implements AfterViewInit, OnInit {

  @ViewChild(MatTable) table: MatTable<PlayedTableItem>;

  private dataSource: PlayedTableDataSource;
  data: PlayedTableItem[];
  constructor(
    private service: PlayedService
  ){}

  /** Columns displayed in the table. Columns IDs can be added, removed, or reordered. */
  displayedColumns = ['homeClub', 'homeClubGoalsScored','date','awayClubGoalsScored','awayClub'];

  ngOnInit() {
    this.service.retrievePlayedMatch().subscribe(
      response => {
        console.log(response);
        this.data = response;
        this.dataSource.data = this.data;
      }
    )
  }

  ngAfterViewInit() {
    this.table.dataSource = this.dataSource;
  }
}
