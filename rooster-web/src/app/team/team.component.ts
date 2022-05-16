import {Component, Injectable, OnInit} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {FormBuilder, NgForm} from '@angular/forms';
import {Team} from "./team";
import {catchError} from "rxjs";

@Component({
  selector: 'app-team',
  templateUrl: './team.component.html',
  styleUrls: ['./team.component.css']
})
@Injectable()
export class TeamComponent implements OnInit {

  newTeam: Team = {} as Team;
  teams?: Team[];
  selectedTeam = {} as Team;

  teamSelected = false;
  addTeam = false;
  editMode = false;
  status = '';

  constructor(private http: HttpClient) {  }

  ngOnInit(): void {
  }

  fetchAll() {
      this.http.get<Team[]>('/api/teams/get_all').subscribe(result => this.teams = result);
  }

  clearAll() {
      this.teams = undefined;
  }

  clearTeamDTO(){
    this.newTeam = {} as Team;
  }

  showAddTeamRequest(){
    this.addTeam = !this.addTeam;
  }

  public addNewTeam(newTeam: Team){
    this.http
      .post<Team>("api/teams/new",newTeam).subscribe(result => this.teams?.push(result));
    this.addTeam = false;
  }


   public deleteTeam(id: number){
    this.teams = this.teams?.filter(t => t.id !== id );
  this.http.delete('api/teams/delete/'+ id)
.subscribe(() => this.status = 'Delete successful');
  }

  public getTeam(id: number){
    this.http
      .get('api/teams/get/' + id)
      .subscribe(result => this.selectedTeam == result);
    this.teamSelected = true;
  }

  // public closeTeamDetailsWindow(){
  //   this.teamSelected = false;
  // }
  //
  // public editModeOn(){
  //   this.editMode = true;
  // }
  //
  // public editModeOff(){
  //   this.editMode = false;
  // }

}
