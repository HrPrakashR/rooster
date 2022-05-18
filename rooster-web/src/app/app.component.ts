import {Component} from '@angular/core';
import {AuthService} from "./auth.service";
import {HttpClient} from "@angular/common/http";
import {Employee} from "./employee/employee";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title?: 'rooster-web';

  constructor(public authService: AuthService) {
  }
}
