import {HttpClient} from '@angular/common/http';
import {Component, Injectable, OnInit} from '@angular/core';
import {Employee} from './employee';
import {Observable} from "rxjs";
import {Team} from "../team/team";
import {Role} from "./role";

@Component({
  selector: 'app-employee',
  templateUrl: './employee.component.html',
  styleUrls: ['./employee.component.css']
})
@Injectable()
export class EmployeeComponent implements OnInit {

  employees?: Employee[];
  teams?: Team[];
  newEmployee = {} as Employee;
  isFormShown = false;
  public Role = Role;


  constructor(private http: HttpClient) {
  }

  ngOnInit(): void {
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

  saveNewEmployee(newEmployee: Employee): Observable<Employee> {
    return this.http.post<Employee>("/api/employees/new", newEmployee);
  }

  toggleShow() {
    this.isFormShown = ! this.isFormShown;
  }

  clearAll() {
    this.employees = undefined;
  }
}
