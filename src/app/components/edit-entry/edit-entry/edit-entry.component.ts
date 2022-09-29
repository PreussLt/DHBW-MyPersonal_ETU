import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {ActivatedRoute, Router} from "@angular/router";
import {TimeEntryService} from "../../../services/time-entry/time-entry.service";

@Component({
  selector: 'app-edit-entry',
  templateUrl: './edit-entry.component.html',
  styleUrls: ['./edit-entry.component.css']
})
export class EditEntryComponent implements OnInit {

  editEntry: FormGroup;
  id: string;

  constructor(
    private formBuilder: FormBuilder,
    private activatedRoute: ActivatedRoute,
    private timeEntryService: TimeEntryService,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.editEntry = this.formBuilder.group({
      id: [''],
      date: [''],
      time: ['', Validators.required]
    })
    this.activatedRoute.params.subscribe(params =>{
        this.id = params['id'];
        this.timeEntryService.getEntry(this.id).subscribe(data => {
          this.f['id'].setValue(data.zid);
          this.f['date'].setValue(data.timestamp.substring(0,10));
          this.f['time'].setValue(data.timestamp.substring(11,16));
        });
      }
    )

  }

  get f() { return this.editEntry.controls; }

  edit(): void{
    this.timeEntryService.updateEntry(this.f['id'].value, this.f['date'].value, this.f['time'].value).subscribe(isEdited => {
      if(isEdited){
        this.router.navigate(["/arbeitstage"]);
      }
      else {
        console.log("Failure");
      }
    })
  }

  delete(): void{
    this.timeEntryService.deleteEntry(this.f['id'].value).subscribe(deleted => {
      if(deleted){
        this.router.navigate(["/arbeitstage"]);
      }
      else {
        console.log("Failure");
      }
    })
  }

}
