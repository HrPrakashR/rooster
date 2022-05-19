import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {EmployeeComponent} from "./employee/employee.component";
import {EmployeeDetailsComponent} from "./employee-details/employee-details.component";

const routes: Routes = [
  {path: 'employee', component: EmployeeComponent},
  {path: 'employee/:id', component: EmployeeDetailsComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
