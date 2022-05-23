import {Component, Input, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {Employee} from "../employee/employee";
import {EmployeeService} from "../employee/employee.service";
import {Role} from "../employee/role";
import {Team} from "../team/team";
import {AuthService} from "../auth.service";

@Component({
  selector: 'app-employee-details',
  templateUrl: './employee-details.component.html',
  styleUrls: ['./employee-details.component.css']
})
export class EmployeeDetailsComponent implements OnInit {

  employee?: Employee;
  editMode = false;
  Role = Role;
  @Input() teams?: any[]
  roleNames = Object.keys(Role);

  constructor(private employeeService: EmployeeService,
              private route:ActivatedRoute,
              private router: Router,
              public authService: AuthService) { }

  /**
   * This method has no parameters and returns void
   * is used to ensure that the initialization code runs
   */
  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id')
    this.employeeService.getEmployee(Number(id)).subscribe(result => this.employee = result);
    this.getTeams();
  }

  /**
   * This method has no parameters and returns void
   * used to navigate current Page to employee
   */
  goBack() {
    this.router.navigate(['/employee']);
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
   * @param id Team id is given as an input
   */
  getTeamName(id: number) {
    // @ts-ignore
    return this.teams?.find(t => t.id === id).name;
  }

  /**
   * This method has no parameters and returns void
   * used to change the boolean value as true for editMode
   */
  editModeOn() {
    this.editMode = true;
  }

  /**
   * This method has no parameters and returns void
   * used to change the boolean value as false for editMode
   */
  public editModeOff() {
    this.editMode = false;
  }

  /**
   * This method has no parameters and returns void
   * used to edit an employee
   * @param employee Employee object is given as an input
   */
  editEmployee(employee: Employee) {
    this.employeeService.editEmployee(employee).subscribe();
  }

  /**
   * This method has one parameter and returns void
   * used to capitalize the first letter of a word
   * @param word a String is given as an input
   */
  capitalizeFirstLetter(word: string) {
    return word.charAt(0).toUpperCase() + word.slice(1).toLowerCase();
  }

}
