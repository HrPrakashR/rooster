import {Component, OnInit} from '@angular/core';
import {Team} from "../team/team";
import {HttpClient} from "@angular/common/http";
import {Employee} from "../employee/employee";
import {Period} from "../period/period";

@Component({
  selector: 'app-generator',
  templateUrl: './generator.component.html',
  styleUrls: ['./generator.component.css']
})
export class GeneratorComponent implements OnInit {

  // 0 = January
  month: number = 0;
  year: number = 2022;

  days: [number] = [1];
  yearsToChoose: [number] = [2022];
  monthToChoose: [number] = [0];

  selectedTeam?: Team;
  selectedTeamId?: number;
  teams?: Team[];
  employees?: Employee[];

  predefinedPeriods: Period[] | undefined;

  constructor(private http: HttpClient) {
    this.createCalendar();
  }

  ngOnInit(): void {
    this.month = new Date().getMonth();
    this.createCalendar();
  }

  getMonthName(month: number | string) {
    if (typeof month === "string") {
      month = parseInt(month);
    }
    switch (month) {
      case 0:
        return "January";
      case 1:
        return "February";
      case 2:
        return "March";
      case 3:
        return "April";
      case 4:
        return "May";
      case 5:
        return "June";
      case 6:
        return "July";
      case 7:
        return "August";
      case 8:
        return "September";
      case 9:
        return "October";
      case 10:
        return "November";
      case 11:
        return "December";
      default:
        return "UNKNOWN";
    }
  }

  createCalendar() {
    this.days = [1];
    this.yearsToChoose = [2022];
    this.monthToChoose = [0];
    this.http.get<Team[]>('/api/teams/get_all').subscribe(result => {
      this.teams = result
      if (this.selectedTeamId === undefined) this.selectedTeamId = result[0].id;
    });
    if (this.selectedTeamId !== undefined) {
      this.setSelectedTeam();
      this.http
        .get<Employee[]>('api/employees/get_all/' + this.selectedTeamId)
        .subscribe(result => this.employees = result);
      this.setPredefinedPeriods()
    }

    let i = 1;
    while (new Date(this.year, this.month, i).getMonth().valueOf() == this.month) {
      if (i <= 11) {
        this.monthToChoose.push(i);
      }
      if (i < 20) {
        this.yearsToChoose.push(this.yearsToChoose[0].valueOf() + i);
      }
      if (i > 1) {
        this.days.push(i);
      }
      i++;
    }
  }

  setPredefinedPeriods(){
    this.http
      .get<Period[]>('/api/generator/roster/'+this.selectedTeamId+'/'+this.year+'/'+this.month)
      .subscribe(result => this.predefinedPeriods = result);
  }

  setSelectedTeam() {
    this.http
      .get<Team>('/api/teams/get/' + this.selectedTeamId)
      .subscribe(result => this.selectedTeam = result);
  }

  isSpecificDay(day: number, checkWith: number) {
    let selectedDate = new Date();
    selectedDate.setDate(day);
    selectedDate.setMonth(this.month);
    selectedDate.setFullYear(this.year)
    return selectedDate.getDay() == checkWith;
  }

  getPeriod(day: number, employee: Employee){
    let period: Period | undefined;
    this.http
      .get<Period>('/api/generator/get/' + employee.id + '/' + this.year + '/' + this.month + '/' + day)
      .subscribe(result => period = result);
    return period?.dateTo;
  }
}
