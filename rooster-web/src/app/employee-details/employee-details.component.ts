import {Component, Input, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {Employee} from "../employee/employee";
import {EmployeeService} from "../employee/employee.service";
import {Role} from "../employee/role";
import {Team} from "../team/team";

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

  constructor(private employeeService: EmployeeService, private route:ActivatedRoute, private router: Router) { }

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id')
    this.employeeService.getEmployee(Number(id)).subscribe(result => this.employee = result);
    this.getTeams();
  }

//TODO: Write the duplicate methods in EmployeeService
  goBack() {
    this.router.navigate(['/employee']);
  }

  getTeams() {
    this.employeeService.getTeams()
      .subscribe(result => this.teams = result);
  }

  getTeamName(id: number) {
    // @ts-ignore
    return this.teams?.find(t => t.id === id).name;
  }

  editModeOn() {
    this.editMode = true;
  }

  public editModeOff() {
    this.editMode = false;
  }

  editEmployee(employee: Employee) {
    this.employeeService.editEmployee(employee).subscribe();
  }

  capitalizeFirstLetter(word: string) {
    return word.charAt(0).toUpperCase() + word.slice(1).toLowerCase();
  }

}
