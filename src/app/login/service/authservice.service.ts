import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class AuthserviceService {
  authUrl: string;
  getIdUrl: string;

  constructor(private http: HttpClient) {
    this.authUrl = 'http://localhost:8080/userauth';
    this.getIdUrl = 'http://localhost:8080/getMid';
  }

  logout() :void {
    sessionStorage.setItem('isLoggedIn','false');
    sessionStorage.removeItem('token');
  }

  public authenticate(personalnummer: string, password: string): Observable<boolean>{
    return this.http.post<boolean>(this.authUrl, {'personalnummer':personalnummer, 'password':password});
  }

  public getMId(personalnummer: string): Observable<string>{
    return this.http.post<string>(this.getIdUrl, personalnummer);
  }
}
