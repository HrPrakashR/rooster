import {Employee} from "../employee/employee";

export interface Period {
  id: number;
  purpose: number;
  dateFrom: Date;
  dateTo: Date;
  employee: number;
}
