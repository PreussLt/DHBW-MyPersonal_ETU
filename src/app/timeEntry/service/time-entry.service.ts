import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {TimeEntry} from "../model/time-entry";

@Injectable({
  providedIn: 'root'
})
export class TimeEntryService {

  private userUrl: string;

  constructor(private http: HttpClient) {
    this.userUrl = "http://localhost:8080/entries"
  }

  //Get-Request an Spring-Endpoint senden
  public findAll(): Observable<TimeEntry[]>{
    return this.http.get<TimeEntry[]>(this.userUrl);
  }
}
