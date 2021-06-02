import { AfterViewInit, Component, OnInit, ViewChild } from '@angular/core';

import { MatTable, MatTableDataSource } from '@angular/material/table';
import { MatchService } from './match.service';
import { MatchesTableItem } from './matches-table-datasource';


@Component({
  selector: 'app-matches-table',
  templateUrl: './matches-table.component.html',
  styleUrls: ['./matches-table.component.css']
})

export class MatchesTableComponent implements AfterViewInit, OnInit {

  @ViewChild(MatTable) table: MatTable<MatchesTableItem>;

  // private dataSource: MatchesTableDataSource;
  dataSource: MatTableDataSource<MatchesTableItem> = new MatTableDataSource<MatchesTableItem>()

  data: MatchesTableItem[];
  searchValue:string;
  matchArray:Array<MatchesTableItem>

  constructor(
    private service: MatchService
  ){}

  displayedColumns = ['homeClub', 'homeClubGoalsScored','date','awayClubGoalsScored','awayClub'];

  ngOnInit() {
    this.service.retrieveAllMatches().subscribe(
      response => {
        console.log(response);
        this.matchArray = response;
        this.dataSource = new MatTableDataSource<MatchesTableItem>(this.matchArray);
      }
    )
  }

  filterMatches(searchedDate:string) {
    var filteredArray = new Array<MatchesTableItem>();
    var addToFilteredArrayIndex = 0;
      for(var i = 0; i < this.matchArray.length; i++) {
        if (this.matchArray[i].date == searchedDate) {
          filteredArray[addToFilteredArrayIndex] = this.matchArray[i]
          addToFilteredArrayIndex++;
        }
      }
      this.dataSource = new MatTableDataSource<MatchesTableItem>(filteredArray);
  }

  resetMatches(){
    this.dataSource = new MatTableDataSource<MatchesTableItem>(this.matchArray);
  }

  ngAfterViewInit() {
    this.table.dataSource = this.dataSource;
  }
}
