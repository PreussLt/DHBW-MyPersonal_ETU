import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup} from "@angular/forms";
import {TimeEntryService} from "../../services/time-entry/time-entry.service";
import {Router} from "@angular/router";
import {VacationService} from "../../services/vacation/vacation.service";

@Component({
  selector: 'app-newentryfield',
  templateUrl: './newentryfield.component.html',
  styleUrls: ['./newentryfield.component.css']
})
export class NewentryfieldComponent implements OnInit {
  newTimeEntryForm: FormGroup;
  showModal: boolean;
  weekend: boolean;
  failure: boolean;
  taken: boolean;

  constructor(
    private formBuilder: FormBuilder,
    private timeEntryService: TimeEntryService,
    private router:Router,
    private vacationService: VacationService
    ) {
    this.showModal = false;
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
      //Zeiteintrag
      case '0':
        this.timeEntryService.newEntry(this.f['dateStamp'].value, this.f['timeStamp'].value).subscribe(created => this.wasSuccessfull(created));
        break;
      //Arbeitstag
      case '1':
        this.timeEntryService.newDay(this.f['dateDay'].value, this.f['timeBeginDay'].value, this.f['timeEndDay'].value).subscribe(created => this.wasSuccessfull(created));
        break;
      //Gleitzeittag
      case '2':
        this.vacationService.setGleitzeittag(this.f['dateFlexibleday'].value).subscribe(created => this.wasSuccessfull(created));
        break;
      //Urlaub
      case '3':
        this.vacationService.setVacation(this.f['dateBeginVacation'].value, this.f['dateEndVacation'].value).subscribe(created => {
          if(created){
            this.router.navigate(["/home"])
          }
          else {
            this.toggleModal();
            this.toggleFailureModal();
          }
        })
        break;
    }
  }

  wasSuccessfull(created: string){
    //Verschiedene Meldungen anzeigen
    if(created.includes("SUCCESS")){
      this.router.navigate(["/home"]);
    }
    else if(created.includes("FAILURE")){
      this.toggleModal();
      this.toggleFailureModal();
    }
    else if(created.includes("WEEKEND")){
      this.toggleModal();
      this.toggleWeekendModal();
    }
    else if(created.includes("TAKEN")){
      this.toggleModal();
      this.toggleTakenModal();
    }
  }

  toggleWeekendModal(): void {
    this.weekend = !this.weekend;
  }

  toggleModal(){
    this.showModal = !this.showModal;
  }

  toggleFailureModal(){
    this.failure = !this.failure;
  }

  toggleTakenModal() {
    this.taken = !this.taken;
  }
}
