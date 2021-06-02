import { AfterViewInit, Component, OnInit, ViewChild } from '@angular/core';
import { MatTable, MatTableDataSource } from '@angular/material/table';
import { ClubTableDataSource, ClubTableItem } from './club-table-datasource';
import { ClubService } from './club.service';

@Component({
  selector: 'app-club-table',
  templateUrl: './club-table.component.html',
  styleUrls: ['./club-table.component.css']
})

export class ClubTableComponent implements AfterViewInit, OnInit {
  @ViewChild(MatTable) table: MatTable<ClubTableItem>;  // stating table is a MatTable component

  data: ClubTableItem[] = []; 

  private dataSource: ClubTableDataSource;
  constructor(
    private service: ClubService
  ){}

  // Columns displayed in the table.
  displayedColumns = ['clubName', 'numberOfWins','numberOfLosses','numberOfDraws','numberOfGoalsScored','numberOfGoalsReceived','numberOfClubPoints','numberOfMatchesPlayed'];

    ngOnInit() {
    this.service.retrieveAllClubs().subscribe(
      response => {
        console.log(response);
        this.data = response;
        this.dataSource.data = this.data;
      }
    )
    
  }
    // function to retrieve sorted arraylist to replace the table values
    retrieveWins(){
      this.service.retrieveClubsbyWins().subscribe(
        response => {
          console.log(response);
          this.data = response;
          this.dataSource.data = this.data;
        }
      )
    }

  // function to retrieve sorted arraylist to replace the table values
  retrievePoints(){
    this.service.retrieveClubsbyPoints().subscribe(
      response => {
        console.log(response);
        this.data = response;
        this.dataSource.data = this.data;
      }
    )
  }

  // function to retrieve sorted arraylist to replace the table values
  retrieveGoals(){
    this.service.retrieveClubsbyGoals().subscribe(
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