import {Component, Injectable, OnInit, ViewChild} from '@angular/core';
import {Period} from './period';
import {HttpClient} from "@angular/common/http";
import {Employee} from "../employee/employee";

@Component({
  selector: 'app-period',
  templateUrl: './period.component.html',
  styleUrls: ['./period.component.css']
})
@Injectable()
export class PeriodComponent implements OnInit {

  newPeriod: Period = {} as Period;
  periods?: Period[];
  employees?: Employee[];

  selectedEmployee = "email@email.de"

  getEmployees(){
    this.http.get<Employee[]>('/api/employees/get_all').subscribe(emp => this.employees = emp);
  }

  status = '';
  newLeave = false;
  showEmployeeList = false;

  constructor(private http: HttpClient) {
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

  clearAllEmployees() {
    this.employees = undefined;
  }

  showLeaveRequest(){
    this.newLeave = !this.newLeave;
  }

  showEmployees(){
    this.showEmployeeList = !this.showEmployeeList;
  }

  saveEntry(){
    this.http.post<Period[]>('/api/periods/new', this.newPeriod)
      .subscribe( np => this.periods = np);
    // this.periods?.push(this.newPeriod);
    this.newPeriod = {} as Period;
  }

  public deletePeriod(id: number){
    this.periods = this.periods?.filter(p => p.id !== id);
    this.http.delete<Period>('/api/periods/delete/' + id)
      .subscribe( () => this.status = 'Period successfully deleted');
  }

  public showEmployeesLeave () {
    this.periods = this.periods?.filter(emp => emp.id);
    this.http.get('/api/periods/get')
      .subscribe(()=> this.status = "All leave requests of selected Employee")
  }
}
