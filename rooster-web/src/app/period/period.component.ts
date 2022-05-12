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

  constructor(private http: HttpClient) {
  }

  ngOnInit(): void {
  }

}
