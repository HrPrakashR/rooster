import {HttpClient} from '@angular/common/http';
import {Component, Injectable, OnInit} from '@angular/core';
import {Employee} from './employee';
import {Team} from "../team/team";
import {Role} from "./role";
import {EmployeeService} from "./employee.service";

@Component({
  selector: 'app-employee',
  templateUrl: './employee.component.html',
  styleUrls: ['./employee.component.css']
})
@Injectable()
export class EmployeeComponent implements OnInit {

  employees?: Employee[];
  teams: Team[] = [];
  newEmployee = {} as Employee;
  isFormShown = false;
  public Role = Role;
  employeeSelected = false;
  selectedEmployee = {} as Employee;
  editMode = false;

  currentUser?: Employee;
  leaveRequest: boolean = false;
  createEmployee: boolean = false;
  myTeam: boolean = false;
  selectedTeam?: number = 0;

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

  getTeamName(id: number) {
    // @ts-ignore
    return this.teams?.find(t => t.id === id).name;
  }

  saveEmployee(newEmployee: Employee) {
    this.http.post<Employee>("/api/employees/new", newEmployee).subscribe(result => this.employees?.push(result));
    this.createEmployee = false;
    this.newEmployee = {} as Employee;
  }

  toggleShow() {
    this.isFormShown = !this.isFormShown;
  }

  clearAll() {
    this.employees = undefined;
  }

  getEmployee(id: number) {
    this.employeeService.getEmployee(id).subscribe(result => this.selectedEmployee = result);
    this.employeeSelected = true;
  }

  deleteEmployee(id: number): void {
    this.employees = this.employees?.filter(employee => employee.id !== id);
    this.http.delete('api/employees/delete/' + id)
      .subscribe();
  }

  closeEmployeeDetails() {
    this.employeeSelected = false;
  }

  capitalizeFirstLetter(word: string) {
    return word.charAt(0).toUpperCase() + word.slice(1).toLowerCase();
  }

  editModeOn() {
    this.editMode = true;
  }

  editEmployee(employee: Employee) {
    this.employeeService.editEmployee(employee).subscribe();
    this.editMode = false;
  }
}
