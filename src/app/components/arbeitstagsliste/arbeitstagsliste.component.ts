import {Component, OnInit} from '@angular/core';
import {ArbeitslisteService} from "../../services/arbeitsliste/arbeitsliste.service";
import {Arbeitstag} from "../../models/arbeitstagliste/arbeitstag";
import {NavigationExtras, Router} from "@angular/router";
import {VacationService} from "../../services/vacation/vacation.service";

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
  showModal: boolean;
  showFailureModal: boolean;
  modalTag: string;
  modalUrlaub: boolean;
  modalGleitzeit: boolean;

  constructor(private arbeitstagListeService: ArbeitslisteService,
              private router: Router,
              private vacationService: VacationService
              ) { }

  ngOnInit(): void {
    this.getCW()
    this.getSollarbeitszeit();
    this.loadEntries();
  }

  getSollarbeitszeit(): void {
  this.arbeitstagListeService.getSollarbeitszeit().subscribe(sollarbeitszeit => {
    this.sollarbeitszeit = sollarbeitszeit;
  })
  }

  loadEntries():void{
    this.arbeitstagListeService.getArbeitstagliste().subscribe(data => {
      this.arbeitstagListe = data.sort((a, b) => a.tag.localeCompare(b.tag));
    });
  }

  getCW():void{
    this.arbeitstagListeService.getCW().subscribe(cw => {
      this.currentYear = Number(cw.substring(0, 4));
      this.calendarWeek = Number(cw.substring(6));

      this.yearSelect = this.currentYear;
      let arrayLength = Number(cw.substring(2, 4)) + 1;
      this.numbers = Array(arrayLength).fill(3).map((x, i) => 2000 + i);
    });
  }

  openEntries(entries: string[][]): void{
    const queryParams: any = {};
    queryParams.entries = JSON.stringify(entries);

    const navigationExtras: NavigationExtras = {
      queryParams
    };

    this.router.navigate(['/overview'], navigationExtras)
  }

  deleteFreiertag(date: string){
    this.vacationService.deleteFreiertag(date).subscribe(deleted =>{
      if(deleted){
        this.router.navigate(["/loading"])
      }
      else {
        this.toggleModal();
        this.toggleFailureModal();
      }
    });
  }
  decreaseCW(): void{
    if(this.calendarWeek > 1) this.calendarWeek--;
  }

  greatDecreaseCW(){
    if(this.calendarWeek - 10 < 1) this.calendarWeek = 1;
    else this.calendarWeek -= 10;
  }

  increaseCW(): void{
    if(this.calendarWeek < 52) this.calendarWeek++;
  }

  greatIncreaseCW() {
    if(this.calendarWeek + 10 > 52) this.calendarWeek = 52;
    else this.calendarWeek += 10;
  }

  toggleModal() {
    this.showModal = !this.showModal;
  }

  toggleFailureModal() {
    this.showFailureModal = !this.showFailureModal
  }

  openModal(tag: string, gleitzeittag:boolean, urlaub:boolean) {
    this.toggleModal();
    this.modalTag = tag;
    this.modalGleitzeit = gleitzeittag;
    this.modalUrlaub = urlaub;
  }

}
