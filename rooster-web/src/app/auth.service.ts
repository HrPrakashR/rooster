import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
// import { User } from './model/user';
import {Employee} from './employee/employee';

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

  /**
   * This is a getter to get Current user
   */
  get currentUser() {
    return this.employee;
  }

  /**
   * This is a getter to get an Authenticated user
   */
  get isAuthenticated() {
    return this.authenticated;
  }

  /**
   * This is a getter to get an Admin
   */
  get isAdmin() {
    return (this.employee?.role === 'MANAGER' || this.employee?.role === 'OWNER') ?? false;   //TODO Rolle bestimmen
  }

  /**
   * This is a getter to get an Owner
   */
  get isOwner() {
    return (this.employee?.role === 'OWNER') ?? false;
  }

  /**
   * This is a getter to get a Manager
   */
  get isManager() {
    return (this.employee?.role === 'MANAGER') ?? false;
  }

  /**
   * This method has two parameters and returns void
   * is used to create a Token for a User
   * @param username Username as String is given as an input
   * @param password Password as String is given as an input
   * @private
   */
  private static createToken(username: string, password: string) {
    return btoa(username + ':' + password);
  }

  /**
   * This method has one parameter and returns void
   * is used to save the Login state of an Employee
   * @param employee Employee object is given as an input
   * @private
   */
  private static saveLoginState(employee: Employee) {
    const data = {employee: employee.email, login: Date.now()}
    localStorage.setItem(SECURITY_LOGIN_STATE, JSON.stringify(data));
  }

  /**
   * This method has no parameters and returns employee data string / number / null
   * is used to check the Login state of an Employee
   * @private
   */
  private static checkLoginState(): { employee: string, login: number } | null {
    const data = localStorage.getItem(SECURITY_LOGIN_STATE);
    if (data) {
      return JSON.parse(data);
    }
    return null;
  }

  /**
   * This method has four parameters and returns void
   * is used to authenticate a User after checking the credentials
   * @param email Email as String is given as an input
   * @param password Password as String is given as an input
   * @param successCallback a Function is given as a parameter
   * @param errorCallback a Function is given as a parameter
   */
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

  /**
   * This method has no parameters and returns void
   * is used to logout the user
   */
  public logout() {
    this.http.post('/api/auth/logout', {}).subscribe(() => console.log('logout successful'))
    this.employee = undefined;
    this.authenticated = false;
    localStorage.removeItem(SECURITY_LOGIN_STATE);
  }

  /**
   * This method has no parameters and returns void
   * is used to refresh the session
   * @private
   */
  private refreshSession() {
    this.http.get<Employee>('/api/users/current').subscribe(employee => {
        if (employee) {
          this.employee = employee;
          this.authenticated = true;
          AuthService.saveLoginState(employee);
        } else {
          this.employee = undefined;
          this.authenticated = false;
          localStorage.removeItem(SECURITY_LOGIN_STATE);
        }
      }
    )
  }
}
