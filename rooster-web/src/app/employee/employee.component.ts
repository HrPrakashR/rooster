import {HttpClient} from '@angular/common/http';
import {Component, Injectable, OnInit} from '@angular/core';
import {Employee} from './employee';
import {Team} from "../team/team";
import {Role} from "./role";
import {EmployeeService} from "./employee.service";
import {Period} from "../period/period";
import {Observable} from "rxjs";
import {AuthService} from "../auth.service";

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
  public role = Role;
  employeeSelected = false;
  selectedEmployee = {} as Employee;
  editMode = false;

  currentUser?: Employee;
  leaveRequest: boolean = false;
  createEmployee: boolean = false;
  myTeam: boolean = false;
  selectedTeam?: number = 0;

  constructor(private http: HttpClient, private employeeService: EmployeeService, public authService: AuthService) {
  }

  /**
   * This method has no parameters and returns void
   * is used to ensure that the initialization code runs
   */
  ngOnInit(): void {
    this.http.get<Employee>('/api/users/current').subscribe(user => this.currentUser = user);
    this.getEmployees();
    this.getTeams();
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

  /**
   * This method has no parameters and returns void
   * used to get all the teams
   */
  getTeams() {
    this.employeeService.getTeams()
      .subscribe(result => this.teams = result);
  }


  /**
   * This method has one parameter and returns void
   * used to get a team name
   * @param id team id is given as an input
   */
  getTeamName(id: number) {
    // @ts-ignore
    return this.teams?.find(t => t.id === id).name;
  }

  /**
   * This method has one parameter and returns void
   * used to save an employees
   * @param newEmployee Employee object is given as input
   */
  saveEmployee(newEmployee: Employee) {
    this.http.post<Employee>("/api/employees/new", newEmployee).subscribe(result => this.employees?.push(result));
    this.createEmployee = false;
    this.newEmployee = {} as Employee;
  }

   /**
   * This method has no parameters and returns void
   * used to change the boolean to toggle a form
   */
  toggleShow() {
    this.isFormShown = !this.isFormShown;
  }

  /**
   * This method has no parameters and returns void
   * used to clear all the employees
   */
  clearAll() {
    this.employees = undefined;
  }


  /**
   * This method has one parameter and returns void
   * used to get an employee
   * @param id Employee id is given as input
   */
  getEmployee(id: number) {
    this.http.get<Employee>('api/employees/get/'+id)
      .subscribe(result => this.selectedEmployee = result);
    this.employeeSelected = true;
  }

  /**
   * This method has one parameter and returns void
   * used to delete an employee
   * @param id Employee id is given as input
   */
  deleteEmployee(id: number): void {
    var result = confirm("Are you sure you want to delete this employee? This operation cannot be undone.");
    if (result) {
      this.employees = this.employees?.filter(employee => employee.id !== id);
      this.http.delete('api/employees/delete/' + id)
        .subscribe();
    }
  }

  /**
   * This method has no parameters and returns void
   * used to change the boolean to view employee details
   */
  closeEmployeeDetails() {
    this.employeeSelected = false;
  }

  /**
   * This method has one parameter and returns void
   * used to capitalize a word
   * @param word A String is given as an input
   */
  capitalizeFirstLetter(word: string) {
    return word.charAt(0).toUpperCase() + word.slice(1).toLowerCase();
  }

  /**
   * This method has one parameter and returns void
   * used to change the boolean for edit mode and set input parameter to selectedEmployee
   * @param employee Employee object is given as an input
   */
  public editModeOn(employee: Employee){
    this.editMode = true;
    this.selectedEmployee = employee;
  }

  /**
   * This method has no parameters and returns void
   * used to change the boolean for edit mode to false
   */
  public editModeOff(){
    this.editMode = false;
  }

  /**
   * This method has one parameter and returns void
   * used to edit an employee
   * @param employee Employee object is given as an input
   */
  editEmployee(employee: Employee){
    this.employeeService.editEmployee(employee).subscribe(() => this.getEmployees());
  }

}
