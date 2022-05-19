import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';

import {Observable} from 'rxjs';

import {Employee} from './employee';

// import { MessageService } from './message.service';


@Injectable({providedIn: 'root'})
export class EmployeeService {

  httpOptions = {
    headers: new HttpHeaders({'Content-Type': 'application/json'})
  };
  private employeesUrl = 'api/employees';  // URL to web api

  constructor(private http: HttpClient) {
  }

  /** GET employee by id. Will 404 if id not found (this part is not included) */
  getEmployee(id: number): Observable<Employee> {
    const url = `${this.employeesUrl}/get/${id}`;
    return this.http.get<Employee>(url);
  }

  /** DELETE: delete the hero from the server */
  deleteEmployee(id: number): Observable<Employee> {
    const url = `${this.employeesUrl}/delete/${id}`;
    return this.http.delete<Employee>(url, this.httpOptions)
  }

  editEmployee(employee: Employee): Observable<Employee> {
    const url = `${this.employeesUrl}/edit`;
    return this.http.post<Employee>(url, employee, this.httpOptions)
  }

  /**
   * Handle Http operation that failed.
   * Let the app continue.
   *
   * @param operation - name of the operation that failed
   * @param result - optional value to return as the observable result
   */
  // private handleError<T>(operation = 'operation', result?: T) {
  //   return (error: any): Observable<T> => {
  //
  //     // TODO: send the error to remote logging infrastructure
  //     console.error(error); // log to console instead
  //
  //     // TODO: better job of transforming error for user consumption
  //     this.log(`${operation} failed: ${error.message}`);
  //
  //     // Let the app keep running by returning an empty result.
  //     return of(result as T);
  //   };
  // }

  // /** Log a HeroService message with the MessageService */
  // private log(message: string) {
  //   this.messageService.add(`HeroService: ${message}`);
  // }
}
