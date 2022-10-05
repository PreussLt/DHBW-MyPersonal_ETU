import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class VacationService {
  setVacationUrl: string;
  setFlexibledayUrl: string;

  constructor(
    private http: HttpClient
  ) {
    this.setVacationUrl = "http://localhost:8080/setVacation";
    this.setFlexibledayUrl = "http://localhost:8080/setFlexibleday"
  }

  setVacation(dateBegin: string, dateEnd: string): Observable<boolean>{
    let mid = sessionStorage.getItem("mid");
    return this.http.post<boolean>(this.setVacationUrl, {"mid": mid, "dateBegin":dateBegin, "dateEnd": dateEnd})
  }

  setGleitzeittag(date:string): Observable<string>{
    let mid = sessionStorage.getItem("mid");
    return this.http.post(this.setFlexibledayUrl, {"mid": mid, "date":date}, {responseType: 'text'})
  }
}
