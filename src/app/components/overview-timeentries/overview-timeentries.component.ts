import {Component, OnInit} from '@angular/core';
import {TimeEntryService} from "../../services/time-entry/time-entry.service";
import {ActivatedRoute, Router} from "@angular/router";

@Component({
  selector: 'app-overview-timeentries',
  templateUrl: './overview-timeentries.component.html',
  styleUrls: ['./overview-timeentries.component.css']
})
export class OverviewTimeentriesComponent implements OnInit {

  entries: string[][] | null | undefined;
  date:string;

  constructor(
    private timeEntryService: TimeEntryService,
    private activatedRoute: ActivatedRoute,
    private router: Router
  ) {
  }

  ngOnInit(): void {
    //Zeiteinträge aus Parameter auslesen
    const array = this.activatedRoute.snapshot.queryParamMap.get('entries');
    if(array == null){
      this.entries = null;
    }
    else {
      this.entries = JSON.parse(array);
      // @ts-ignore
      this.date = this.entries[0][0].substring(0,10)
    }
  }

  editEntry(id: string): void{
    this.router.navigate(["/editEntry", id])
  }

}
