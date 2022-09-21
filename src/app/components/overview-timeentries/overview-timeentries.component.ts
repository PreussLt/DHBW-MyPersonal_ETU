import { Component, OnInit } from '@angular/core';
import {TranslateService} from "@ngx-translate/core";
import {TimeEntryService} from "../../services/time-entry/time-entry.service";
import {TimeEntry} from "../../models/timeEntry/time-entry";

@Component({
  selector: 'app-overview-timeentries',
  templateUrl: './overview-timeentries.component.html',
  styleUrls: ['./overview-timeentries.component.css']
})
export class OverviewTimeentriesComponent implements OnInit {

  entries: TimeEntry[];

  constructor(
    private translate: TranslateService,
    private timeEntryService: TimeEntryService
  ) {
    translate.setDefaultLang('de');
    translate.use('de');
  }

  ngOnInit(): void {
    this.timeEntryService.findAll().subscribe(data => {
      this.entries = data;
    });
  }

}
