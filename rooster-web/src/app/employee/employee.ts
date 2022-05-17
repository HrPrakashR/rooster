import {Team} from "../team/team";
import {Role} from "./role";

export interface Employee {
  id: number
  firstName: string;
  lastName: string;
  password: string;
  email: string;
  team: number;
  hoursPerWeek: number;
  balanceHours: number;
  breakTime: number;
  role: string;
}
