import {Component, Injectable, OnInit} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Team} from "./team";

@Component({
  selector: 'app-team',
  templateUrl: './team.component.html',
  styleUrls: ['./team.component.css']
})
@Injectable()
export class TeamComponent implements OnInit {

  teams?: Team[];

  constructor(private http: HttpClient) {
  }

  ngOnInit(): void {
    this.fetchAll();
  }

  fetchAll() {
      this.http.get<Team[]>('/api/teams/get_all').subscribe(result => this.teams = result);
  }

}
