import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';
import {AppComponent} from './app.component';
import {EmployeeComponent} from './employee/employee.component';
import {RouterModule} from "@angular/router";
import {FormsModule} from "@angular/forms";
import {PeriodComponent} from './period/period.component';
import {TeamComponent} from './team/team.component';
import {GeneratorComponent} from './generator/generator.component';
import {HTTP_INTERCEPTORS, HttpClientModule} from "@angular/common/http";
import {LoginComponent} from './login/login.component';
import {AuthInterceptor} from "./auth.interceptor";
import {AppRoutingModule} from "./app-routing.module";
import { EmployeeDetailsComponent } from './employee-details/employee-details.component';
import { LandingComponent } from './landing/landing.component';

@NgModule({
  declarations: [
    AppComponent,
    EmployeeComponent,
    PeriodComponent,
    TeamComponent,
    GeneratorComponent,
    LoginComponent,
    EmployeeDetailsComponent,
    LandingComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    RouterModule.forRoot([
      {path: '', component: LandingComponent, pathMatch: 'full'},
      {path: 'employee', component: EmployeeComponent},
      {path: 'period', component: PeriodComponent},
      {path: 'team', component: TeamComponent},
      {path: 'generator', component: GeneratorComponent},
      {path: 'login', component: LoginComponent},
    ])
  ],
  providers: [
    {provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true}
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
