import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class AuthserviceService {
  authUrl: string;

  constructor(private http: HttpClient) {
    this.authUrl = 'http://localhost:8080/userauth'
  }

  logout() :void {
    sessionStorage.setItem('isLoggedIn','false');
    sessionStorage.removeItem('token');
  }

  public authenticate(personalnummer: string, password: string): Observable<boolean>{
    return this.http.post<boolean>(this.authUrl, {'personalnummer':personalnummer, 'password':password});
  }
}
