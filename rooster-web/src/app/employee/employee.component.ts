import {Observable, Subscription, throwError} from 'rxjs';
import { catchError, retry } from 'rxjs/operators';
import { HttpClient } from '@angular/common/http';
import {Component, Injectable, OnInit} from '@angular/core';
import { Employee } from './employee';

@Component({
  selector: 'app-employee',
  templateUrl: './employee.component.html',
  styleUrls: ['./employee.component.css']
})
@Injectable()
export class EmployeeComponent implements OnInit {

  employees: Employee[] | undefined;

  constructor(private http: HttpClient) { }

  ngOnInit(): void {
    this.http.get<Employee[]>('/api/employees').subscribe(employees => {
      this.employees = employees;
    });
  }
}
