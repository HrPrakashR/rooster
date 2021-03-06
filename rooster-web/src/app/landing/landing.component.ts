import { Component, OnInit } from '@angular/core';
import {EmployeeService} from "../employee/employee.service";
import {Employee} from "../employee/employee";
import {HttpClient} from "@angular/common/http";
import {AuthService} from "../auth.service";

@Component({
  selector: 'app-landing',
  templateUrl: './landing.component.html',
  styleUrls: ['./landing.component.css']
})
export class LandingComponent implements OnInit {

  employees?: Employee[];
  currentUser?: Employee;

  constructor(private http: HttpClient,
              private employeeService: EmployeeService,
              public authService: AuthService ) { }

  /**
   * This method has no parameters and returns void
   * is used to ensure that the initialization code runs
   */
  ngOnInit(): void {
    this.http.get<Employee>('/api/users/current').subscribe(user => this.currentUser = user);
  }

  /**
   * This method has no parameters and returns void
   * used to get all the employees
   */
  getEmployees() {
    this.http
      .get<Employee[]>('/api/employees/get_all')
      .subscribe(result => this.employees = result);
  }
}
