import {Employee} from "../employee/employee";

export interface Period {
  purpose: number;
  dateFrom: Date;
  dateTo: Date;
  employee: number;
}
