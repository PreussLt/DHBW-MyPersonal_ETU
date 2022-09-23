import {Component, OnInit} from '@angular/core';
import {ArbeitslisteService} from "../../services/arbeitsliste/arbeitsliste.service";
import {Arbeitstag} from "../../models/arbeitstagliste/arbeitstag";

@Component({
  selector: 'app-arbeitstagsliste',
  templateUrl: './arbeitstagsliste.component.html',
  styleUrls: ['./arbeitstagsliste.component.css']
})
export class ArbeitstagslisteComponent implements OnInit {
  arbeitstagListe: Arbeitstag[];

  constructor(private arbeitstagListeService: ArbeitslisteService) { }

  ngOnInit(): void {
    this.arbeitstagListeService.getArbeitstagliste().subscribe(data => {
      console.log(data);
      this.arbeitstagListe = data
    });
  }

}
