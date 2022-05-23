import {Component, OnInit} from '@angular/core';
import {AuthService} from '../auth.service';
import {Router} from '@angular/router';
import {HttpClient} from "@angular/common/http";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  credentials = {email: '', password: ''}

  loginError?: string;
  passwordAlert?: string;

  constructor(private http: HttpClient,
              private authService: AuthService,
              private router: Router) {
  }

  ngOnInit(): void {
  }

  /**
   * This method has no parameters and returns void
   * is used to validate the login credentials
   */
  valid() {
    return this.credentials && this.credentials.email.length !== 0 && this.credentials.password.length !== 0;
  }

  /**
   * This method has no parameters and returns void
   * is used to enable login or get an error message
   */
  login() {
    if (this.valid()) {
      this.authService.authenticate(this.credentials.email, this.credentials.password,
        () => this.router.navigate(['/'])),
        (err: string | undefined) => this.loginError = err;
    }
  }

  /**
   * This method has no parameters and returns void
   * is used to reset the password
   */
  forgotPassword() {
    this.http.get("/api/users/sendPassword/email/" + this.credentials.email, {responseType: 'text'}).subscribe(result => this.passwordAlert = result);
  }

}
