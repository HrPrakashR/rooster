import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {Employee} from "../employee/employee";
import {EmployeeService} from "../employee/employee.service";

@Component({
  selector: 'app-employee-details',
  templateUrl: './employee-details.component.html',
  styleUrls: ['./employee-details.component.css']
})
export class EmployeeDetailsComponent implements OnInit {

  employee?: Employee;
  constructor(private employeeService: EmployeeService, private route:ActivatedRoute, private router: Router) { }

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id')
    this.employeeService.getEmployee(Number(id)).subscribe(result => this.employee = result);;
  }

  goBack() {
    this.router.navigate(['/employee']);
  }

}
