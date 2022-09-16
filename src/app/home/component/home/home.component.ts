import { Component, OnInit } from '@angular/core';
import {TranslateService} from "@ngx-translate/core";
import {Router} from "@angular/router";
import {AuthserviceService} from "../../../login/service/authservice.service";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit{
  title = 'DHBW-Fallstudie-MyPersonal';
  id: string|null;
  constructor(private translate: TranslateService, private router: Router, private authService: AuthserviceService ) {
    translate.setDefaultLang('de');
    translate.use('de');
  }

  useLanguage(language: string): void {
    this.translate.use(language);
  }


  ngOnInit() {
    this.id = sessionStorage.getItem('token');
    //console.log(this.id);
  }

  logout() {
    console.log('logout');
    this.authService.logout();
    this.router.navigate(['/login']);
  }
}
