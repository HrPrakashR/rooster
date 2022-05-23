import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';

import {Observable} from 'rxjs';

import {Employee} from './employee';
import {Team} from "../team/team";

@Injectable({providedIn: 'root'})
export class EmployeeService {

  httpOptions = {
    headers: new HttpHeaders({'Content-Type': 'application/json'})
  };
  private employeesUrl = 'api/employees';  // URL to web api

  constructor(private http: HttpClient) {
  }

  /**
   * This method has one parameter and returns an Employee object
   * used to get an employee and return it to other classes
   * @param id Employee id is given as an input
   */
  getEmployee(id: number): Observable<Employee> {
    const url = `${this.employeesUrl}/get/${id}`;
    return this.http.get<Employee>(url);
  }

  /**
   * This method has one parameter and returns an Employee object
   * used to get delete an Employee by id
   * @param id Employee id is given as an input
   */
  deleteEmployee(id: number): Observable<Employee> {
    const url = `${this.employeesUrl}/delete/${id}`;
    return this.http.delete<Employee>(url, this.httpOptions)
  }

  /**
   * This method has one parameter and returns an Employee object
   * used to edit an employee
   * @param employee Employee object is given as an input
   */
  editEmployee(employee: Employee): Observable<Employee> {
    const url = `${this.employeesUrl}/edit`;
    return this.http.post<Employee>(url, employee, this.httpOptions)
  }

  /**
   * This method has no parameters and returns all Teams
   * used to get all the teams
   */
  getTeams(): Observable<Team[]> {
    return this.http.get<Team[]>('/api/teams/get_all');
  }

}
