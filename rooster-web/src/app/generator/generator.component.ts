import {Component, OnInit} from '@angular/core';
import {Team} from "../team/team";
import {HttpClient} from "@angular/common/http";

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
      .get<Team>('api/teams/get/' + this.selectedTeamId)
      .subscribe(result => this.selectedTeam = result);
  }

  isSpecificDay(day: number, checkWith: number) {
    let selectedDate = new Date();
    selectedDate.setDate(day);
    selectedDate.setMonth(this.month);
    selectedDate.setFullYear(this.year)
    return selectedDate.getDay() == checkWith;
  }
}
