import {Component, Injectable, OnInit} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Team} from "./team";
import {Employee} from "../employee/employee";
import {EmployeeService} from "../employee/employee.service";
import {AuthService} from "../auth.service";

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


  constructor(private http: HttpClient,
              private employeeService: EmployeeService,
              public authService: AuthService) {
  }

  /**
   * This method has no parameters and returns void
   * is used to ensure that the initialization code
   * runs together with methods getEmployees() and getTeams().
   */
  ngOnInit(): void {
    this.http.get<Employee>('/api/users/current').subscribe(user => this.currentUser = user);
    this.getEmployees();
    this.getTeams();
  }

  /**
   * This method has no parameters and returns void
   * gets all employees.
   */
  getEmployees() {
    this.http
      .get<Employee[]>('/api/employees/get_all')
      .subscribe(result => this.employees = result);
  }

  /**
   * This method has no parameters and returns void
   * gets all teams.
   */
  getTeams() {
    this.http
      .get<Team[]>('/api/teams/get_all')
      .subscribe(result => this.teams = result);
  }

  // getTeamName(id: number) {
  //   // @ts-ignore
  //   return this.teams?.find(t => t.id === id).name;
  // }

  /**
   * This method takes a number as a parameter and returns
   * name of team searched by id.
   */
  getTeamName(id: number) {
    // @ts-ignore
    return this.teams?.find(t => t.id === id).name;
  }

  /**
   * This method has no parameters and returns void
   * gets all teams.
   */
  fetchAll() {
    this.http.get<Team[]>('/api/teams/get_all').subscribe(result => this.teams = result);
  }


  /**
   * This method has no parameters and returns void
   * deletes new team.
   */
  clearTeamDTO() {
    this.newTeam = {} as Team;
  }

  /**
   * This method has no parameters and returns void
   * sets show add team request true.
   */
  showAddTeamRequest() {
    this.addTeam = !this.addTeam;
  }

  /**
   * This method takes a team as parameter and returns void
   * adds a new team and sets addTeam false.
   */
  public addNewTeam(newTeam: Team) {
    this.http
      .post<Team>("api/teams/new", newTeam).subscribe(result => this.teams?.push(result));
    this.addTeam = false;
  }

  /**
   * This method takes a team as parameter and returns void
   * edits given team and sets edit mode back to false.
   */
  editTeam(team: Team) {
    const url = `${this.teamUrl}/edit`;
    this.http.post<Team>(url, team).subscribe(() => this.fetchAll());
    this.editMode = false;
  }


  /**
   * This method takes an id as a parameter and returns void
   * deletes team after confirming to confirm message.
   */
  public deleteTeam(id: number) {
    var result = confirm("Are you sure you want to delete this team? This operation cannot be undone.");
    if (result) {
      this.teams = this.teams?.filter(t => t.id !== id);
      this.http.delete('api/teams/delete/' + id)
        .subscribe(() => this.status = 'Delete successful');
    }
  }

  /**
   * This method takes an id as parameter and returns void
   * gets team by id.
   */
  public getTeam(id: number) {
    this.http
      .get<Team>('api/teams/get/' + id)
      .subscribe(result => this.selectedTeam = result);
    // this.teamSelected = true;
  }

  /**
   * This method has no parameters and returns void
   * sets team selected to false.
   */
  public closeTeamDetailsWindow() {
    this.teamSelected = false;
  }

  /**
   * This method takes a team as parameter and returns void
   * sets edit mode true and selected team as team.
   */
  public editModeOn(team: Team) {
    this.editMode = true;
    this.selectedTeam = team;
  }

  /**
   * This method has no parameters and returns void
   * sets edit mode back to false.
   */
  public editModeOff() {
    this.editMode = false;
  }

}
