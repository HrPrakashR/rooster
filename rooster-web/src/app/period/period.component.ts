import {Component, Injectable, Input, OnInit} from '@angular/core';
import {Period} from './period';
import {HttpClient} from "@angular/common/http";
import {Employee} from "../employee/employee";
import {Purpose} from "./purpose";
import {EmployeeService} from "../employee/employee.service";
import {Team} from "../team/team";
import {AuthService} from "../auth.service";
import {PurposeRequests} from "./purposeRequests";

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
  public PurposeRequests = PurposeRequests;

  constructor(private http: HttpClient,
              private employeeService: EmployeeService,
              public authService: AuthService) {
  }


  /**
   * This method has no parameters and returns void
   * is used to ensure that the initialization code
   * runs together with methods getEmployees() and fetchAll().
   */
  ngOnInit(): void {
    this.http.get<Employee>('/api/users/current').subscribe(user => this.currentUser = user);
    this.getEmployees();
    this.fetchAll();
  }

  /**
   * This method has no parameters and returns void
   * gets all employees.
   */
  getEmployees() {
    this.http.get<Employee[]>('/api/employees/get_all').subscribe(emp => this.employees = emp);
  }

  /**
   * This method has no parameters and returns void
   * gets all leave requests.
   */
  fetchAll() {
    this.http.get<Period[]>('/api/periods/get_all').subscribe(result => this.periods = result);
  }

  /**
   * This method has no parameters and returns void
   * clears all leave requests.
   */
  clearAllPeriods() {
    this.allLeave = !this.allLeave;
    // this.periods = undefined;
  }

  /**
   * This method has no parameters and returns void
   * shows all leave requests.
   */
  showLeaveRequest() {
    this.userLeave = !this.userLeave
  }

  /**
   * This method has no parameters and returns void
   * shows all employees.
   */
  showEmployees() {
    this.showEmployeeList = !this.showEmployeeList;
  }

  /**
   * This method has no parameters and returns void
   * saves a newly created leave request.
   */
  saveEntry(newPeriod: Period) {
    this.http.post<Period[]>('/api/periods/new', newPeriod)
      .subscribe(np => { this.periods = np; this.newLeaveRequest = false});
    this.newPeriod = {} as Period;
  }

  public deletePeriod(id: number) {
    let result = confirm("Are you sure you want to delete this leave request? This operation cannot be undone.");
    if (result) {
      this.periods = this.periods?.filter(period => period.id !== id);
      this.http.delete<Period>('/api/periods/delete/' + id)
        .subscribe(() => this.status = 'Period successfully deleted');
    }
  }

  /**
   * This method has no parameters and returns void
   * shows all leave requests from a selected employee
   * using his/her id.
   */
  public showEmployeesLeave(id: number) {
    this.periods = this.periods?.filter(emp => emp.employee == id);
    this.http.get('/api/periods/get')
      .subscribe(() => this.status = "All leave requests of selected Employee")
  }

  /**
   * This method takes a period as a parameter returns void
   * edit mode is set true.
   */
  editModeOn(period: Period) {
    this.editMode = true;
    this.selectedPeriod = period;
  }

  /**
   * This method has no parameter returns void
   * edit mode is set false.
   */
  editModeOff() {
    this.editMode = false;
  }

  /**
   * This method takes a period as a parameter returns void
   * gets period and sets edit mode true, so value of period
   * can be altered
   */
  editPeriod(period: Period) {
    const url = `${this.periodUrl}/edit`;
    this.http.post<Period>(url, period).subscribe();
    this.editMode = false;
  }

  /**
   * This method takes an id as a parameter and returns void
   * gets period by id
   */
  public showRequest(id: number) {
    this.http
      .get<Period>('api/periods/get/' + id)
      .subscribe(result => this.selectedPeriod = result);
    this.periodSelected = true;
  }

  /**
   * This method takes a date as a parameter and returns
   * date without time
   */
  public removeTFromDate(date: string){
    return date.replace('T', ' ')
  }

  /**
   * This method has no parameter and
   * sets new leave request to true
   */
  public openFormNewLeaveRequest() {
    this.newLeaveRequest = !this.newLeaveRequest;
  }

  /**
   * This method takes an employee as a parameter and returns
   * a boolean.
   */
  // @ts-ignore
  public hasEmployeePeriods(employee: Employee): boolean {
    this.periods?.forEach(period =>
    {return period.employee === employee.id;});
  }

}
