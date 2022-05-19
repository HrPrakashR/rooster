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

  predefinedPeriods?: Period[];


  constructor(private http: HttpClient) {
    this.createCalendar();
  }

  ngOnInit(): void {
    this.month = new Date().getMonth();
    this.year = new Date().getFullYear();
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
        .subscribe(result => {
            this.employees = result
            this.predefinedPeriods = [];
            this.employees?.forEach(employee => {

              this.predefinedPeriods = [];

                this.http.get<Period[]>('/api/periods/employee/' + employee.id + '/' + this.year + '/' + this.month)
                  .subscribe(result => this.predefinedPeriods?.push(...result));
              }
            );
          }
        );
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

  setSelectedTeam() {
    this.http
      .get<Team>('/api/teams/get/' + this.selectedTeamId)
      .subscribe(result => this.selectedTeam = result);
  }

  returnDayName(day: number) {
    let selectedDate = this.createDate(day);
    switch (selectedDate.getDay()) {
      case 0:
        return "Sunday";
      case 1:
        return "Monday";
      case 2:
        return "Tuesday";
      case 3:
        return "Wednesday";
      case 4:
        return "Thursday";
      case 5:
        return "Friday";
      case 6:
        return "Saturday";
      default:
        return "Unknown";
    }
  }

  returnCompulsory(day: number) {
    switch (this.createDate(day).getDay()) {
      case 0:
        return this.selectedTeam?.sundayFrom != null ? this.selectedTeam?.sundayFrom + ' - ' + this.selectedTeam?.sundayTo : "";
      case 1:
        return this.selectedTeam?.mondayFrom != null ? this.selectedTeam?.mondayFrom + ' - ' + this.selectedTeam?.mondayTo : "";
      case 2:
        return this.selectedTeam?.tuesdayFrom != null ? this.selectedTeam?.tuesdayFrom + ' - ' + this.selectedTeam?.tuesdayTo : "";
      case 3:
        return this.selectedTeam?.wednesdayFrom != null ? this.selectedTeam?.wednesdayFrom + ' - ' + this.selectedTeam?.wednesdayTo : "";
      case 4:
        return this.selectedTeam?.thursdayFrom != null ? this.selectedTeam?.thursdayFrom + ' - ' + this.selectedTeam?.thursdayTo : "";
      case 5:
        return this.selectedTeam?.fridayFrom != null ? this.selectedTeam?.fridayFrom + ' - ' + this.selectedTeam?.fridayTo : "";
      case 6:
        return this.selectedTeam?.saturdayFrom != null ? this.selectedTeam?.saturdayFrom + ' - ' + this.selectedTeam?.saturdayTo : "";
      default:
        return "Unknown";
    }
  }

  private createDate(day: number) {
    let date = new Date();
    date.setDate(day);
    date.setMonth(this.month);
    date.setFullYear(this.year)
    return date;
  }

  getWorkingHours(employee: Employee) {
      let i = 0;
      this.http.get<number>('/api/periods/employee/workingHour/' + employee.id + '/' + this.year + '/' + this.month)
        .subscribe(result => i = result);
      return i;
  }
}
