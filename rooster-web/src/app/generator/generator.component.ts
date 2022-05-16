import {Component, OnInit} from '@angular/core';

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

  constructor() {}

  ngOnInit(): void {
    this.month = new Date().getMonth();
    this.createCalendar();
  }

  getMonthName(month: number){
    switch (month){
      case 0:
        return "January";
      case 1:
        return "February";
      case 3:
        return "March";
      case 4:
        return "April";
      case 5:
        return "May";
      case 6:
        return "June";
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
        return "Unknown Month";
    }
  }

  createCalendar(){
    this.days = [1];
    this.yearsToChoose = [2022];
    this.monthToChoose = [0];

    let i = 1;
    while(new Date(this.year, this.month, i).getMonth().valueOf() == this.month){
      if(i<=11){
        this.monthToChoose.push(i);
      }
      if(i<20){
        this.yearsToChoose.push(this.yearsToChoose[0].valueOf() + i);
      }
      if(i>1){
        this.days.push(i);
      }
      i++;
    }
  }

}
