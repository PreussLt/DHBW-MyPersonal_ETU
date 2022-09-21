import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {TimeEntryService} from "../../timeEntry/service/time-entry.service";

@Component({
  selector: 'app-newentryfield',
  templateUrl: './newentryfield.component.html',
  styleUrls: ['./newentryfield.component.css']
})
export class NewentryfieldComponent implements OnInit {
  newTimeEntryForm: FormGroup;
  failure: boolean | null;
  newEntryUrl: string;

  constructor(private formBuilder: FormBuilder, private timeEntryService: TimeEntryService) {
    this.failure = null;
    this.newEntryUrl = "http://localhost:8080/newEntry"
  }

  ngOnInit(): void {
    this.newTimeEntryForm = this.formBuilder.group({
      date: ['', Validators.required],
      dateEnd: ['', Validators.required],
      timeBegin: ['', Validators.required],
      timeEnd: ['', Validators.required],
    });
  }

  get f() { return this.newTimeEntryForm.controls; }

  submit(){
    let mid = sessionStorage.getItem("mid");
    if(mid != null) {
      let response = this.timeEntryService.newEntry(mid.toString(), this.f['date'].value, this.f['timeBegin'].value);
      response.subscribe(data => {
        this.failure = !data;
        window.location.reload();
      });
    }
  }
}
