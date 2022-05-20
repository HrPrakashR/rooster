import {Component, Injectable, OnInit} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Team} from "./team";
import {Employee} from "../employee/employee";
import {EmployeeService} from "../employee/employee.service";

@Component({
  selector: 'app-team',
  templateUrl: './team.component.html',
  styleUrls: ['./team.component.css']
})
@Injectable()
export class TeamComponent implements OnInit {

  currentUser?: Employee;
  employees?: Employee[];
  newTeam: Team = {} as Team;
  teams: Team[] = [];
  selectedTeam = {} as Team;
  teamSelected = false;
  addTeam = false;
  editMode = false;
  status = '';
  teamUrl = '/api/teams';
  team?: Team;


  constructor(private http: HttpClient, private employeeService: EmployeeService) {
  }

  ngOnInit(): void {
    this.http.get<Employee>('/api/users/current').subscribe(user => this.currentUser = user);
    this.getEmployees();
    this.getTeams();
  }

  getEmployees() {
    this.http
      .get<Employee[]>('/api/employees/get_all')
      .subscribe(result => this.employees = result);
  }

  getTeams() {
    this.http
      .get<Team[]>('/api/teams/get_all')
      .subscribe(result => this.teams = result);
  }

  // getTeamName(id: number) {
  //   // @ts-ignore
  //   return this.teams?.find(t => t.id === id).name;
  // }

  getTeamName(id: number) {
    // @ts-ignore
    return this.teams?.find(t => t.id === id).name;
  }

  fetchAll() {
    this.http.get<Team[]>('/api/teams/get_all').subscribe(result => this.teams = result);
  }

  clearTeamDTO() {
    this.newTeam = {} as Team;
  }

  showAddTeamRequest() {
    this.addTeam = !this.addTeam;
  }

  public addNewTeam(newTeam: Team) {
    this.http
      .post<Team>("api/teams/new", newTeam).subscribe(result => this.teams?.push(result));
    this.addTeam = false;
  }

  editTeam(team: Team) {
    const url = `${this.teamUrl}/edit`;
    this.http.post<Team>(url, team).subscribe(() => this.fetchAll());
    this.editMode = false;
  }

  public deleteTeam(id: number) {
    this.teams = this.teams?.filter(t => t.id !== id);
    this.http.delete('api/teams/delete/' + id)
      .subscribe(() => this.status = 'Delete successful');
  }

  public getTeam(id: number) {
    this.http
      .get<Team>('api/teams/get/' + id)
      .subscribe(result => this.selectedTeam = result);
    this.teamSelected = true;
  }

  public closeTeamDetailsWindow() {
    this.teamSelected = false;
  }

  public editModeOn(team: Team) {
    this.editMode = true;
    this.selectedTeam = team;
  }

  public editModeOff() {
    this.editMode = false;
  }

}
