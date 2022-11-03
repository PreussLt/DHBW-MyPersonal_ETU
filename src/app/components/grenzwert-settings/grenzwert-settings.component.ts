import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup} from "@angular/forms";
import {GrenzwertService} from "../../services/grenzwert/grenzwert.service";

@Component({
  selector: 'app-grenzwert-settings',
  templateUrl: './grenzwert-settings.component.html',
  styleUrls: ['./grenzwert-settings.component.css']
})
export class GrenzwertSettingsComponent implements OnInit {

  grenzwerteForm: FormGroup;
  failure: boolean;
  //yellow greater red
  yGr: boolean;
  //day greater week
  dGw: boolean;
  success: boolean;

  constructor(
    private formBuilder: FormBuilder,
    private grenzwertService: GrenzwertService
  ) { }

  ngOnInit(): void {
    this.grenzwerteForm = this.formBuilder.group({
      select: [1],
      day: [],
      week: [],
      yellow: [],
      red: []
    });
    this.loadConf();

  }

  get f() { return this.grenzwerteForm.controls; }

  loadConf(): void{
    //Aktuelle Grenzwerte auslesen und in Eingabefelder setzen
    this.grenzwertService.getGrenzwerte(Number(this.f['select'].value)).subscribe(num => {
      this.f['day'].setValue(num[1]);
      this.f['week'].setValue(num[2]);
      this.f['yellow'].setValue(num[3]);
      this.f['red'].setValue(num[4]);
    });
  }

  updateConf(): void{
    this.success = false;
    this.failure = false;
    this.dGw = false;
    this.yGr = false;

    if(Number(this.f['day'].value) > Number(this.f['week'].value)) this.dGw = true;

    if(Number(this.f['yellow'].value) > Number(this.f['red'].value)) this.yGr = true;

    if(!this.dGw && !this.yGr) {
      this.grenzwertService.setGrenzwerte(this.f['select'].value, this.f['day'].value, this.f['week'].value, this.f['yellow'].value, this.f['red'].value).subscribe(success => {
        if (success) {
          this.success = true
          this.loadConf()
        } else {
          this.failure = true;
        }
      })
    }
  }
}
