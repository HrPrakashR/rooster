import { Component, OnInit } from '@angular/core';
import {EmployeeService} from "../employee/employee.service";
import {Employee} from "../employee/employee";
import {HttpClient} from "@angular/common/http";

@Component({
  selector: 'app-landing',
  templateUrl: './landing.component.html',
  styleUrls: ['./landing.component.css']
})
export class LandingComponent implements OnInit {

  employees?: Employee[];
  currentUser?: Employee;

  constructor(private http: HttpClient, private employeeService: EmployeeService) { }

  ngOnInit(): void {
    this.http.get<Employee>('/api/users/current').subscribe(user => this.currentUser = user);
  }

  getEmployees() {
    this.http
      .get<Employee[]>('/api/employees/get_all')
      .subscribe(result => this.employees = result);
  }
}
