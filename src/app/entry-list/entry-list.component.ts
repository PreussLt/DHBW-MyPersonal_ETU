import { Component, OnInit } from '@angular/core';
import {TimeEntry} from "../timeEntry/model/time-entry";
import {TimeEntryService} from "../timeEntry/service/time-entry.service";

@Component({
  selector: 'app-entry-list',
  templateUrl: './entry-list.component.html',
  styleUrls: ['./entry-list.component.css']
})
export class EntryListComponent implements OnInit {

  entries: TimeEntry[];

  constructor(private timeEntryService: TimeEntryService) { }

  ngOnInit(): void {
    //Daten aus Request an Variable binden
    this.timeEntryService.findAll().subscribe(data => this.entries = data);
  }

}
