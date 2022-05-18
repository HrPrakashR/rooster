import {Component, OnInit} from '@angular/core';
import {AuthService} from '../auth.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  credentials = {email: '', password: ''}

  loginError?: string;

  constructor(private authService: AuthService, private router: Router) {
  }

  ngOnInit(): void {
  }

  valid() {
    return this.credentials && this.credentials.email.length !== 0 && this.credentials.password.length !== 0;
  }

  login() {
    if (this.valid()) {
      this.authService.authenticate(this.credentials.email, this.credentials.password,
        () => this.router.navigate(['/'])),
        (err: string | undefined) => this.loginError = err;
    }
  }

}
