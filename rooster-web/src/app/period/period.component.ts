import {Component, OnInit} from '@angular/core';
import {Period} from './period';
import {HttpClient} from "@angular/common/http";

@Component({
  selector: 'app-period',
  templateUrl: './period.component.html',
  styleUrls: ['./period.component.css']
})
export class PeriodComponent implements OnInit {

  newPeriod: Period = {} as Period;
  periods?: Period[];

  newLeave = false;

  constructor(private http: HttpClient) {
  }

  ngOnInit(): void {
  }

  fetchAll() {
    this.http.get<Period[]>('/api/periods/get_all').subscribe(result => this.periods = result);
  }

  clearAll() {
    this.periods = undefined;
  }

  showLeaveRequest(){
    this.newLeave = !this.newLeave;
  }

  saveEntry(){
    this.http.post<Period[]>('/api/periods/new', this.newPeriod)
      .subscribe( np => this.periods = np);
    // this.periods?.push(this.newPeriod);
    this.newPeriod = {} as Period;
  }

}
