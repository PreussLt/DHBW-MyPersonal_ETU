import {Component, OnInit} from '@angular/core';
import {TranslateService} from "@ngx-translate/core";
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
    private translate: TranslateService,
    private timeEntryService: TimeEntryService,
    private activatedRoute: ActivatedRoute,
    private router: Router
  ) {
    translate.setDefaultLang('de');
    translate.use('de');
  }

  ngOnInit(): void {
    const array = this.activatedRoute.snapshot.queryParamMap.get('entries');
    if(array == null){
      this.entries = null;
    }
    else {
      this.entries = JSON.parse(array);
      // console.log(this.entries)
      // @ts-ignore
      this.date = this.entries[0][0].substring(0,10)
    }
  }

  editEntry(id: string): void{
    this.router.navigate(["/editEntry", id])
  }

}
