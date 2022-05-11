import {HttpClient} from '@angular/common/http';
import {Component, Injectable, OnInit} from '@angular/core';
import {Employee} from './employee';

@Component({
  selector: 'app-employee',
  templateUrl: './employee.component.html',
  styleUrls: ['./employee.component.css']
})
@Injectable()
export class EmployeeComponent implements OnInit {

  employees?: Employee[];

  constructor(private http: HttpClient) {
  }

  ngOnInit(): void {
    this.getEmployee();
  }

  getEmployee() {
    this.http
      .get<Employee[]>('/api/employees')
      .subscribe(result => this.employees = result);
  }
}
