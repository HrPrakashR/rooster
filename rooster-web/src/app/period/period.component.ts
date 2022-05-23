import {Component, Injectable, Input, OnInit} from '@angular/core';
import {Period} from './period';
import {HttpClient} from "@angular/common/http";
import {Employee} from "../employee/employee";
import {Purpose} from "./purpose";
import {EmployeeService} from "../employee/employee.service";
import {Team} from "../team/team";
import {AuthService} from "../auth.service";

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
  selectedPeriod = {} as Period;

  currentUser?: Employee;

  status = '';
  userLeave = false;
  newLeaveRequest = false;
  allLeave = false;
  showEmployeeList = false;
  periodSelected = false;

  public Purpose = Purpose;

  constructor(private http: HttpClient,
              private employeeService: EmployeeService,
              public authService: AuthService) {
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
      .subscribe(np => { this.periods = np; this.newLeaveRequest = false});
    this.newPeriod = {} as Period;
  }

  public deletePeriod(id: number) {
    var result = confirm("Are you sure you want to delete this leave request? This operation cannot be undone.");
    if (result) {
      this.periods = this.periods?.filter(period => period.id !== id);
      this.http.delete<Period>('/api/periods/delete/' + id)
        .subscribe(() => this.status = 'Period successfully deleted');
    }
  }

  public showEmployeesLeave(id: number) {
    this.periods = this.periods?.filter(emp => emp.employee == id);
    this.http.get('/api/periods/get')
      .subscribe(() => this.status = "All leave requests of selected Employee")
  }

  editModeOn(period: Period) {
    this.editMode = true;
    this.selectedPeriod = period;
  }

  editModeOff() {
    this.editMode = false;
  }

  editPeriod(period: Period) {
    const url = `${this.periodUrl}/edit`;
    this.http.post<Period>(url, period).subscribe();
    this.editMode = false;
  }

  public showRequest(id: number) {
    this.http
      .get<Period>('api/periods/get/' + id)
      .subscribe(result => this.selectedPeriod = result);
    this.periodSelected = true;
  }

  public removeTFromDate(date: string){
    return date.replace('T', ' ')
  }

  public openFormNewLeaveRequest() {
    this.newLeaveRequest = !this.newLeaveRequest;
  }

  // @ts-ignore
  public hasEmployeePeriods(employee: Employee): boolean {
    this.periods?.forEach(period =>
    {return period.employee === employee.id;});
  }

}
