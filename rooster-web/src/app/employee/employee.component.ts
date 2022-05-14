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
  teams?: Team[];
  newEmployee = {} as Employee;
  isFormShown = false;
  public Role = Role;


  constructor(private http: HttpClient,
              private employeeService: EmployeeService) {
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

  getNextEmployeeId(employees: Employee[] | undefined) {
    if(employees) {
      const ids = employees.map(object => {
        return object.id;
      });
      const max = Math.max(...ids);
      return max + 1;
    } else {
      return 0;
    }
  }

  saveNewEmployee(newEmployee: Employee){
    newEmployee.id = this.getNextEmployeeId(this.employees);
    this.http.post<Employee>("/api/employees/new", newEmployee).subscribe(result=>this.employees?.push(result));
    this.isFormShown = false;
  }

  toggleShow() {
    this.isFormShown = ! this.isFormShown;
  }

  clearAll() {
    this.employees = undefined;
  }

  deleteEmployee(employee: Employee): void {
    // @ts-ignore
    this.employees = this.employees.filter(e => e !== employee);
    this.employeeService.deleteEmployee(employee.id).subscribe();
  }
}
