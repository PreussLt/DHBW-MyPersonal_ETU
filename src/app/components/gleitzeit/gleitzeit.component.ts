import { Component, OnInit } from '@angular/core';
import {ArbeitslisteService} from "../../services/arbeitsliste/arbeitsliste.service";
import {GrenzwertService} from "../../services/grenzwert/grenzwert.service";
import {Grenzwerte} from "../../models/grenzwerte/grenzwerte";

@Component({
  selector: 'app-gleitzeit',
  templateUrl: './gleitzeit.component.html',
  styleUrls: ['./gleitzeit.component.css']
})
export class GleitzeitComponent implements OnInit {

  gleitzeitstand: string;
  loading: boolean;
  grenzwerte:Grenzwerte;
  danger: number;

  constructor(private arbeitstagService: ArbeitslisteService, private  grenzwertService: GrenzwertService) { }

  ngOnInit(): void {
    this.getGleitzeit();

  }

  getGleitzeit(): void{
    this.loading = true;
    this.arbeitstagService.getGleitzeit().subscribe(gleitzeit => {
      this.grenzwertService.getGrenzwerteFor().subscribe(grenzwerte => {
        this.gleitzeitstand = gleitzeit.toFixed(2);
        this.grenzwerte = grenzwerte;
        this.getDanger();
        this.loading = false;
      });

    });
  }

  getDanger(): void{
    let gleitzeitstand = Number(this.gleitzeitstand);
    let yellow = Number(this.grenzwerte.yellow);
    let red = Number(this.grenzwerte.red);

    if (gleitzeitstand < yellow) this.danger = 0;
    else if (gleitzeitstand < red && gleitzeitstand >= yellow) this.danger = 1;
    else if (gleitzeitstand >= red) this.danger = 2;
  }

}
