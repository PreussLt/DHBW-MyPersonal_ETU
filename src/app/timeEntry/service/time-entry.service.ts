import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {TimeEntry} from "../model/time-entry";

@Injectable({
  providedIn: 'root'
})
export class TimeEntryService {

  private userUrl: string;
  private newEntryUrl: string;


  constructor(private http: HttpClient) {
    this.userUrl = "http://localhost:8080/entries"
    this.newEntryUrl = "http://localhost:8080/newEntry"
  }

  //Get-Request an Spring-Endpoint senden
  public findAll(): Observable<TimeEntry[]>{
    return this.http.get<TimeEntry[]>(this.userUrl);
  }

  public newEntry(mId: string, date: string, time: string): Observable<boolean>{
    return this.http.post<boolean>(this.newEntryUrl, {"mid":mId, "date":date, "time":time});
  }
}
