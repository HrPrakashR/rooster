import {Component, Injectable, Input, OnInit} from '@angular/core';
import {Period} from './period';
import {HttpClient} from "@angular/common/http";
import {Employee} from "../employee/employee";
import {Purpose} from "./purpose";

@Component({
  selector: 'app-period',
  templateUrl: './period.component.html',
  styleUrls: ['./period.component.css']
})
@Injectable()
export class PeriodComponent implements OnInit {

  @Input() userId: number = 0;

  newPeriod: Period = {} as Period;
  periods?: Period[];
  employees?: Employee[];
  employeeId = 0;

  selectedEmployee = "email@email.de"
  status = '';
  newLeave = false;
  showEmployeeList = false;

  public Purpose = Purpose;

  constructor(private http: HttpClient) {
  }

  getEmployees() {
    this.http.get<Employee[]>('/api/employees/get_all').subscribe(emp => this.employees = emp);
  }

  ngOnInit(): void {
    this.getEmployees();
  }

  fetchAll() {
    this.http.get<Period[]>('/api/periods/get_all').subscribe(result => this.periods = result);
  }

  clearAllPeriods() {
    this.periods = undefined;
  }

  showLeaveRequest() {
    this.newLeave = !this.newLeave;
  }

  showEmployees() {
    this.showEmployeeList = !this.showEmployeeList;
  }

  saveEntry() {
    this.http.post<Period[]>('/api/periods/new', this.newPeriod)
      .subscribe(np => this.periods = np);
    this.newPeriod = {} as Period;
  }

  public deletePeriod(id: number) {
    this.periods = this.periods?.filter(period => period.id !== id);
    this.http.delete<Period>('/api/periods/delete/' + id)
      .subscribe(() => this.status = 'Period successfully deleted');
  }

  public showEmployeesLeave(id: number) {
    this.periods = this.periods?.filter(emp => emp.employee == id);
    this.http.get('/api/periods/get')
      .subscribe(() => this.status = "All leave requests of selected Employee")
  }
}
