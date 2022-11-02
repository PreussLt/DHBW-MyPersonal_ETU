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
  arbeitstageCurrentWeek: Arbeitstag[];
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

  loading: boolean;
  countArbeitstageCurrentWeek: number;

  constructor(private arbeitstagListeService: ArbeitslisteService,
              private router: Router,
              private vacationService: VacationService
              ) {
    this.countArbeitstageCurrentWeek = 0;
  }

  ngOnInit(): void {
    this.getCW();
    this.getSollarbeitszeit();
    this.loadEntries();
    this.getArbeitstageCurrentWeek();
  }

  getSollarbeitszeit(): void {
  this.arbeitstagListeService.getSollarbeitszeit().subscribe(sollarbeitszeit => {
    this.sollarbeitszeit = sollarbeitszeit;
  })
  }

  loadEntries():void{
    this.loading = true;
    this.arbeitstagListeService.getArbeitstagliste().subscribe(data => {
      this.arbeitstagListe = data.sort((a, b) => a.tag.localeCompare(b.tag));
      this.loading = false;
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

  getArbeitstageCurrentWeek(): void{
    this.arbeitstageCurrentWeek = [];
    this.countArbeitstageCurrentWeek = 0;
    for (let i = 0; i < this.arbeitstagListe.length; i++) {
      if(this.arbeitstagListe[i].calendarWeek == this.calendarWeek && this.arbeitstagListe[i].calendarYear == this.yearSelect){
        this.arbeitstageCurrentWeek.push(this.arbeitstagListe[i]);
        this.countArbeitstageCurrentWeek++;
      }
    }
    // console.log(this.arbeitstageCurrentWeek)
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
        this.router.navigate(["/deletebuffer"])
      }
      else {
        this.toggleModal();
        this.toggleFailureModal();
      }
    });
  }
  decreaseCW(): void{
    if(this.calendarWeek > 1) this.calendarWeek--;
    this.getArbeitstageCurrentWeek()
  }

  greatDecreaseCW(){
    if(this.calendarWeek - 10 < 1) this.calendarWeek = 1;
    else this.calendarWeek -= 10;
    this.getArbeitstageCurrentWeek()
  }

  increaseCW(): void{
    if(this.calendarWeek < 52) this.calendarWeek++;
    this.getArbeitstageCurrentWeek()
  }

  greatIncreaseCW() {
    if(this.calendarWeek + 10 > 52) this.calendarWeek = 52;
    else this.calendarWeek += 10;
    this.getArbeitstageCurrentWeek()
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

  getGleitzeit(arbeitszeit: number, sollarbeitszeit:number): string{
    let temp = arbeitszeit - sollarbeitszeit;
    let gleitzeit;
    if(temp > 0){
      gleitzeit = "+"+temp.toFixed(2)+"h";
      return gleitzeit;
    }
    else return temp.toFixed(2).toString()+"h";
  }

}
