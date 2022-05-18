import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
// import { User } from './model/user';
import { Employee } from './employee/employee';

const SECURITY_LOGIN_STATE = 'SECURITY_LOGIN_STATE';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private employee?: Employee;
  private authenticated = false;

  constructor(private http: HttpClient) {
    if (AuthService.checkLoginState()) {
      this.refreshSession();
    }
  }

  private static createToken(username: string, password: string) {
    return btoa(username + ':' + password);
  }

  private static saveLoginState(employee: Employee) {
    const data = {employee: employee.email, login: Date.now()}
    localStorage.setItem(SECURITY_LOGIN_STATE, JSON.stringify(data));
  }

  private static checkLoginState(): { employee: string, login: number } | null {
    const data = localStorage.getItem(SECURITY_LOGIN_STATE);
    if (data) {
      return JSON.parse(data);
    }
    return null;
  }

  public authenticate(email: string, password: string, successCallback?: Function, errorCallback?: Function) {
    this.http.post<Employee>('/api/auth/login', {email, password}, {
      headers: new HttpHeaders({
        'Authorization': 'Basic ' + AuthService.createToken(email, password),
      }), withCredentials: true,
    }).subscribe({
        next: employee => {
          this.employee = employee;
          this.authenticated = true;
          AuthService.saveLoginState(employee);
          if (successCallback) {
            successCallback(employee);
          }
        },
        error: err => {
          console.error('Login failed!', err);
          if (errorCallback) {
            errorCallback(err);
          }
        }
      }
    )
  }

  private refreshSession() {
    this.http.get<Employee>('/api/users/current').subscribe(user => {
        if (user) {
          this.employee = user;
          this.authenticated = true;
          AuthService.saveLoginState(this.employee);
        } else {
          this.employee = undefined;
          this.authenticated = false;
          localStorage.removeItem(SECURITY_LOGIN_STATE);
        }
      }
    )
  }

  public logout() {
    this.http.post('/api/auth/logout', {}).subscribe(() => console.log('logout successful'))
    this.employee = undefined;
    this.authenticated = false;
    localStorage.removeItem(SECURITY_LOGIN_STATE);
  }

  get currentUser() {
    return this.employee;
  }

  get isAuthenticated() {
    return this.authenticated;
  }

  get isAdmin() {
    return this.employee?.role ?? false;   //TODO Rolle bestimmen
  }


}
