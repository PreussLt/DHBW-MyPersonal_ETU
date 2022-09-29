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

  constructor(private http: HttpClient) {
    this.arbeitslisteUrl = "http://localhost:8080/arbeitstage"
    this.sollarbeitszeitUrl = "http://localhost:8080/sollarbeitszeit"
  }


  public getArbeitstagliste(): Observable<Arbeitstag[]> {
    let mid = sessionStorage.getItem("mid");
    return this.http.post<Arbeitstag[]>(this.arbeitslisteUrl, mid)
  }

  public getSollarbeitszeit(): Observable<number> {
    return this.http.post<number>(this.sollarbeitszeitUrl, {});
  }
}
