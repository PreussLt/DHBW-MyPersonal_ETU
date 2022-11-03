import {Component, OnInit} from '@angular/core';
import {Router} from "@angular/router";
import {TranslateService} from "@ngx-translate/core";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit{
  constructor(private router: Router, private translate:TranslateService) {
    translate.setDefaultLang('de');
    translate.addLangs(['en'])
    window.scrollTo(0,0)
  }

  ngOnInit(): void {
    this.router.navigate(["/home"]);
  }

  useLanguage(lg: string): void{
    this.translate.use(lg);
  }
}
