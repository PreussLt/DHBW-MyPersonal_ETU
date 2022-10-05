import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Arbeitstag} from "../../models/arbeitstagliste/arbeitstag";

@Injectable({
  providedIn: 'root'
})
export class ArbeitslisteService{

  private arbeitslisteUrl: string;
  private sollarbeitszeitUrl: string;
  private getCWUrl: string;

  constructor(private http: HttpClient) {
    this.arbeitslisteUrl = "http://localhost:8080/arbeitstage"
    this.sollarbeitszeitUrl = "http://localhost:8080/sollarbeitszeit"
    this.getCWUrl = "http://localhost:8080/getCW"
  }


  public getArbeitstagliste(): Observable<Arbeitstag[]> {
    let mid = sessionStorage.getItem("mid");
    return this.http.post<Arbeitstag[]>(this.arbeitslisteUrl, mid);
  }

  public getSollarbeitszeit(): Observable<number> {
    return this.http.post<number>(this.sollarbeitszeitUrl, {});
  }

  public getCW(): Observable<string> {
    return this.http.post(this.getCWUrl, {}, {responseType: 'text'});
  }

}
