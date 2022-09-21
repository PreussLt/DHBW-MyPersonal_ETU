import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {AbstractControl, ValidationErrors, ValidatorFn} from "@angular/forms";

@Injectable({
  providedIn: 'root'
})
export class AuthserviceService {
  authUrl: string;
  getIdUrl: string;
  checkPWUrl: string;
  changePWUrl: string;

  constructor(private http: HttpClient) {
    this.authUrl = 'http://localhost:8080/userauth';
    this.getIdUrl = 'http://localhost:8080/getMid';
    this.checkPWUrl = 'http://localhost:8080/pwauth';
    this.changePWUrl = 'http://localhost:8080/changepw';
  }

  logout(): void {
    sessionStorage.setItem('isLoggedIn','false');
    sessionStorage.removeItem('token');
  }

  public authenticate(personalnummer: string, password: string): Observable<boolean>{
    return this.http.post<boolean>(this.authUrl, {'personalnummer':personalnummer, 'password':password});
  }

  public getMId(personalnummer: string): Observable<string>{
    return this.http.post<string>(this.getIdUrl, personalnummer);
  }

  public isPwMatching(inputpw: string): Observable<boolean>{
    let mid = sessionStorage.getItem("mid");
    return this.http.post<boolean>(this.checkPWUrl, {"mid":mid, "pw":inputpw})
  }

  public changePW(newPW: string): Observable<boolean>{
    let mid = sessionStorage.getItem("mid");
    return this.http.post<boolean>(this.changePWUrl, {"mid": mid, "pw":newPW})
  }

  createPasswordStrengthValidator(): ValidatorFn {
    return (control:AbstractControl) : ValidationErrors | null => {

      const value = control.value;

      if (!value) {
        return null;
      }

      const hasUpperCase = /[A-Z]+/.test(value);

      const hasLowerCase = /[a-z]+/.test(value);

      const hasNumeric = /[0-9]+/.test(value);

      const passwordValid = hasUpperCase && hasLowerCase && hasNumeric;

      return !passwordValid ? {passwordStrength:true}: null;
    }
  }
}
