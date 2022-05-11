import {DatePipe} from "@angular/common";

export interface Team {
  id: number;
  name: string;
  restHours: number;
  restDays: number;
  minBreakTime: number;
  weekdays: DatePipe[];
}
