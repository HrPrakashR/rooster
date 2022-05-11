import {Team} from "../team/team";

export interface Employee {
  id: number;
  firstName: string;
  lastName: string;
  password: string;
  email: string;
  team: Team;
  hoursPerWeek: number;
  balanceHours: number;
  breakTime: number;
  role: string;
}
