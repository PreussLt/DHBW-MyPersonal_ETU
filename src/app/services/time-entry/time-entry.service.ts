import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {TimeEntry} from "../../models/timeEntry/time-entry";

@Injectable({
  providedIn: 'root'
})
export class TimeEntryService {

  private entryUrl: string;
  private newEntryUrl: string;
  private updateEntryUrl: string;

  constructor(private http: HttpClient) {
    this.entryUrl = "http://localhost:8080/getEntry"
    this.newEntryUrl = "http://localhost:8080/newEntry"
    this.updateEntryUrl = "http://localhost:8080/updateEntry"
  }

  public getEntry(id: string): Observable<TimeEntry>{
    return this.http.post<TimeEntry>(this.entryUrl, id);
  }

  public newEntry(mId: string, date: string, time: string): Observable<boolean>{
    return this.http.post<boolean>(this.newEntryUrl, {"mid":mId, "date":date, "time":time});
  }

  public updateEntry(zid: string, date: string, time: string): Observable<boolean>{
    let timestamp = date + " " + time + ":00";
    return this.http.post<boolean>(this.updateEntryUrl, {"zid":zid, "bid":"-1", "timestamp": timestamp})
  }
}
