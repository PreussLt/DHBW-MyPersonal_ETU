import {Component, OnInit} from '@angular/core';
import {ArbeitslisteService} from "../../services/arbeitsliste/arbeitsliste.service";
import {Arbeitstag} from "../../models/arbeitstagliste/arbeitstag";
import {NavigationExtras, Router} from "@angular/router";

@Component({
  selector: 'app-arbeitstagsliste',
  templateUrl: './arbeitstagsliste.component.html',
  styleUrls: ['./arbeitstagsliste.component.css']
})
export class ArbeitstagslisteComponent implements OnInit {
  arbeitstagListe: Arbeitstag[];
  sollarbeitszeit: number;
  calendarWeek: number;
  currentYear: number;
  numbers: number[];
  yearSelect: number;

  constructor(private arbeitstagListeService: ArbeitslisteService, private router: Router) { }

  ngOnInit(): void {
    this.arbeitstagListeService.getCW().subscribe(cw => {
      this.currentYear = Number(cw.substring(0,4));
      this.calendarWeek = Number(cw.substring(6));

      this.yearSelect = this.currentYear;
      let arrayLength = Number(cw.substring(2,4)) + 1;
      this.numbers = Array(arrayLength).fill(3).map((x,i)=> 2000 + i);

      this.arbeitstagListeService.getArbeitstagliste().subscribe(data => {
        this.arbeitstagListeService.getSollarbeitszeit().subscribe(sollarbeitszeit => {
          this.sollarbeitszeit = sollarbeitszeit;
          this.arbeitstagListe = data.sort((a, b) => a.tag.localeCompare(b.tag));
        })
      });
    })
  }

  openEntries(entries: string[][]): void{
    const queryParams: any = {};
    queryParams.entries = JSON.stringify(entries);

    const navigationExtras: NavigationExtras = {
      queryParams
    };

    this.router.navigate(['/overview'], navigationExtras)
  }

  update(): void{
    console.log(this.currentYear)
  }

  decreaseCW(): void{
    if(this.calendarWeek > 1) this.calendarWeek--;
  }

  increaseCW(): void{
    if(this.calendarWeek < 52) this.calendarWeek++;
  }
}
