import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup} from "@angular/forms";
import {TimeEntryService} from "../../services/time-entry/time-entry.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-newentryfield',
  templateUrl: './newentryfield.component.html',
  styleUrls: ['./newentryfield.component.css']
})
export class NewentryfieldComponent implements OnInit {
  newTimeEntryForm: FormGroup;
  newEntryUrl: string;

  constructor(private formBuilder: FormBuilder, private timeEntryService: TimeEntryService, private router:Router) {
    this.newEntryUrl = "http://localhost:8080/newEntry"
  }

  ngOnInit(): void {
    this.newTimeEntryForm = this.formBuilder.group({
      entryType: [-1],

      dateStamp: [],
      timeStamp: [],

      dateDay: [],
      timeBeginDay: [],
      timeEndDay: [],

      dateFlexibleday: [],

      dateBeginVacation: [],
      dateEndVacation: []
    });
  }

  get f() { return this.newTimeEntryForm.controls; }

  submit(){
    switch (this.f['entryType'].value){
      case '0':
        this.timeEntryService.newEntry(this.f['dateStamp'].value, this.f['timeStamp'].value).subscribe(() => this.router.navigate(["/home"]));
        break;
      case '1':
        this.timeEntryService.newDay(this.f['dateDay'].value, this.f['timeBeginDay'].value, this.f['timeEndDay'].value).subscribe(created =>{
          if(created){
            this.router.navigate(["/home"])
          }
          else {
            console.log("Failure")
          }
        });
        break;
      case '2':
        break;
      case '3':
        break;
    }
  }

}
