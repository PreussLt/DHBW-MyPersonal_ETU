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

  constructor(private arbeitstagListeService: ArbeitslisteService, private router: Router) { }

  ngOnInit(): void {
    this.arbeitstagListeService.getArbeitstagliste().subscribe(data => {
      console.log(data);
      this.arbeitstagListe = data
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


}
