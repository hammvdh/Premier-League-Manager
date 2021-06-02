import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ClubTableComponent } from './club-table/club-table.component';
import { MatchesTableComponent } from './matches-table/matches-table.component';
import { MatTableModule } from '@angular/material/table' ;
import { PlayedTableComponent } from './played-table/played-table.component';
import { MatFormFieldModule} from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
const routes: Routes = [
  { path: '', redirectTo: '/club', pathMatch: 'full' },
  { path:'club', component: ClubTableComponent},
  { path:'matches', component: MatchesTableComponent},
  { path:'playedmatch', component: PlayedTableComponent},
  { path:'club/sortbywins', component: ClubTableComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes), MatTableModule,MatFormFieldModule, MatInputModule],
  exports: [RouterModule,MatFormFieldModule, MatInputModule]
})
export class AppRoutingModule {
  
}
