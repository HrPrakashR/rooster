import {Component, Injectable, OnInit} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import { FormControl } from '@angular/forms';
import {Team} from "./team";

@Component({
  selector: 'app-team',
  templateUrl: './team.component.html',
  styleUrls: ['./team.component.css']
})
@Injectable()
export class TeamComponent implements OnInit {

  newTeam?: Team;
  teams?: Team[];

  constructor(private http: HttpClient) {
    new FormControl()
  }

  ngOnInit(): void {
  }

  fetchAll() {
      this.http.get<Team[]>('/api/teams/get_all').subscribe(result => this.teams = result);
  }

  clearAll() {
      this.teams = undefined;
  }

  fetchTeamDTO(){
    this.http.get<Team>('/api/teams/new').subscribe(result => this.newTeam = result);
  }

}
