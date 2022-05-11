import {Team} from "../team/team";
import {Role} from "./role";

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
  role: Role;
}