import {Injectable, OnInit} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Arbeitstagliste} from "../../models/arbeitstagliste/arbeitstagliste";
import {Observable} from "rxjs";
import {Arbeitstag} from "../../models/arbeitstagliste/arbeitstag";

@Injectable({
  providedIn: 'root'
})
export class ArbeitslisteService{

  private arbeitslisteUrl: string;

  constructor(private http: HttpClient) {
    this.arbeitslisteUrl = "http://localhost:8080/arbeitstage"
  }

  public getArbeitstagliste(): Observable<Arbeitstag[]> {
    let mid = sessionStorage.getItem("mid");
    return this.http.post<Arbeitstag[]>(this.arbeitslisteUrl, mid)
  }
}
