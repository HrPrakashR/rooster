import {Component, Injectable, Input, OnInit} from '@angular/core';
import {Period} from './period';
import {HttpClient} from "@angular/common/http";
import {Employee} from "../employee/employee";
import {Purpose} from "./purpose";
import {EmployeeService} from "../employee/employee.service";

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
  periodUrl = '/api/periods';
  editMode = false;

  currentUser?: Employee;

  status = '';
  userLeave = false
  allLeave = false;
  showEmployeeList = false;

  public Purpose = Purpose;

  constructor(private http: HttpClient, private employeeService: EmployeeService) {
  }

  getEmployees() {
    this.http.get<Employee[]>('/api/employees/get_all').subscribe(emp => this.employees = emp);
  }

  ngOnInit(): void {
    this.http.get<Employee>('/api/users/current').subscribe(user => this.currentUser = user);
    this.getEmployees();
    this.fetchAll();
  }

  fetchAll() {
    this.http.get<Period[]>('/api/periods/get_all').subscribe(result => this.periods = result);
  }

  clearAllPeriods() {
    this.allLeave = !this.allLeave;
    // this.periods = undefined;
  }

  showLeaveRequest() {
    this.userLeave = !this.userLeave
  }

  showEmployees() {
    this.showEmployeeList = !this.showEmployeeList;
  }

  saveEntry(newPeriod: Period) {
    this.http.post<Period[]>('/api/periods/new', newPeriod)
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

  editModeOn() {
    this.editMode = true;
  }

  editPeriod(period: Period) {
    const url = `${this.periodUrl}/edit`;
    this.http.post<Period>(url, period).subscribe();
    this.editMode = false;
  }
}
