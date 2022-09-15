import { Component, OnInit } from '@angular/core';
import {TranslateService} from "@ngx-translate/core";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit{
  title = 'DHBW-Fallstudie-MyPersonal';
  constructor(private translate: TranslateService) {
    translate.setDefaultLang('de');
    translate.use('de');
  }

  useLanguage(language: string): void {
    this.translate.use(language);
  }

  ngOnInit(): void {
  }
}
