import {Component, OnInit} from '@angular/core';

@Component({
  selector: 'app-generator',
  templateUrl: './generator.component.html',
  styleUrls: ['./generator.component.css']
})
export class GeneratorComponent implements OnInit {

  month: number = 0;
  year: number = 2022;
  days: [number] = [1];

  yearsToChoose: [number] = [2022];
  monthToChoose: [number] = [0];

  constructor() {
    this.month = new Date().getMonth();
    this.createCalendar();
  }

  ngOnInit(): void {}

  getMonth(value: number){
    return value+1;
  }

  createCalendar(){
    this.days = [1];
    let i = 1;
    while(new Date(this.year, this.month, i).getMonth() == this.month){
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
