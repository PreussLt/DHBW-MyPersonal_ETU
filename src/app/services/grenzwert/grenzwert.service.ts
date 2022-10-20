import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Grenzwerte} from "../../models/grenzwerte/grenzwerte";

@Injectable({
  providedIn: 'root'
})
export class GrenzwertService {

  private getGrenzwerteUrl;
  private setGrenzwerteUrl;
  private getGrenzwerteForUrl;

  constructor(
    private http: HttpClient
  ) {
    this.getGrenzwerteUrl = "http://localhost:8080/getGrenzwerte"
    this.setGrenzwerteUrl = "http://localhost:8080/setGrenzwerte"
    this.getGrenzwerteForUrl = "http://localhost:8080/getGrenzwerteFor"

  }

  getGrenzwerte(num: number): Observable<number[]>{
    return this.http.post<number[]>(this.getGrenzwerteUrl, num);
  }

  setGrenzwerte(confnum:string, day: string, week: string, yellow: string, red: string): Observable<boolean>{
    return this.http.post<boolean>(this.setGrenzwerteUrl, {"confnum": confnum, "maxday": day, "maxweek": week, "yellow": yellow, "red": red});
  }

  getGrenzwerteFor(): Observable<Grenzwerte>{
    let mid = sessionStorage.getItem("mid")
    return this.http.post<Grenzwerte>(this.getGrenzwerteForUrl, mid);
  }
}
