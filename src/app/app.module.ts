import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './components/app.component';
import {TimeEntryService} from "./services/time-entry/time-entry.service";
import {HttpClientModule} from "@angular/common/http";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {TranslateLoader, TranslateModule} from '@ngx-translate/core';
import {TranslateHttpLoader} from '@ngx-translate/http-loader';
import {HttpClient} from '@angular/common/http';
import { HomeComponent } from './components/home/home.component';
import { LoginComponent } from './components/login/login.component';
import { OverviewTimeentriesComponent } from './components/overview-timeentries/overview-timeentries.component';
import { NewentryfieldComponent } from './components/newentryfield/newentryfield.component';
import { ChangePwComponent } from './components/change-pw/change-pw.component';
import { ArbeitstagslisteComponent } from './components/arbeitstagsliste/arbeitstagsliste.component';
import {EditEntryComponent} from "./components/edit-entry/edit-entry/edit-entry.component";

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    LoginComponent,
    OverviewTimeentriesComponent,
    NewentryfieldComponent,
    ChangePwComponent,
    ArbeitstagslisteComponent,
    EditEntryComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    TranslateModule.forRoot({
      loader: {
        provide: TranslateLoader,
        useFactory: HttpLoaderFactory,
        deps: [HttpClient]
      }
    })
  ],
  providers: [TimeEntryService],
  bootstrap: [AppComponent]
})
export class AppModule { }

// required for AOT compilation
export function HttpLoaderFactory(http: HttpClient): TranslateHttpLoader {
  return new TranslateHttpLoader(http);
}
