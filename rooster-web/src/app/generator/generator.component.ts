import {Component, OnInit} from '@angular/core';

@Component({
  selector: 'app-generator',
  templateUrl: './generator.component.html',
  styleUrls: ['./generator.component.css']
})
export class GeneratorComponent implements OnInit {

  month: number = 2;
  year: number = 2022;
  days: [number] = [1];

  yearsToChoose: [number] = [2022];
  monthToChoose: [number] = [1];

  constructor() {
    this.month = new Date().getMonth()+1;
    this.createCalendar();
  }

  ngOnInit(): void {}

  setMonth(readableMonth: number){
    this.month = readableMonth-1;
  }

  createCalendar(){
    this.days = [1];
    let i = 2;
    while(new Date(this.year, this.month, i).getMonth() == this.month){
      if(i<=12){
        this.monthToChoose.push(i);
      }
      if(i<20){
        this.yearsToChoose.push(i);
      }
      this.days.push(i++);
    }
  }

}
