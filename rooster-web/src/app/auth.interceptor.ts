import { Injectable } from '@angular/core';
import {HttpHandler, HttpInterceptor, HttpRequest} from "@angular/common/http";
import {Observable} from "rxjs";

export class AuthInterceptor implements HttpInterceptor{

  constructor() {
  }

// @ts-ignore
  intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {

    if (request.url.startsWith('/api')){
      request = request.clone({
        headers: request.headers.append('X-Requested-With', 'XMLHttpRequest')
      })
    }

    return next.handle(request);
}
}
