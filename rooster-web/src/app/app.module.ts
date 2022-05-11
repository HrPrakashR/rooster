import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';
import {AppComponent} from './app.component';
import {EmployeeComponent} from './employee/employee.component';
import {RouterModule} from "@angular/router";
import {FormsModule} from "@angular/forms";
import {PeriodComponent} from './period/period.component';
import {TeamComponent} from './team/team.component';
import {GeneratorComponent} from './generator/generator.component';
import {HttpClientModule} from "@angular/common/http";

@NgModule({
  declarations: [
    AppComponent,
    EmployeeComponent,
    PeriodComponent,
    TeamComponent,
    GeneratorComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    FormsModule,
    RouterModule.forRoot([
      {path: 'employee', component: EmployeeComponent},
      {path: 'period', component: PeriodComponent},
      {path: 'team', component: TeamComponent},
      {path: 'generator', component: GeneratorComponent},
    ])
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
