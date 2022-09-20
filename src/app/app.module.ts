import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { EntryListComponent } from './entry-list/entry-list.component';
import {TimeEntryService} from "./timeEntry/service/time-entry.service";
import {HttpClientModule} from "@angular/common/http";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {TranslateLoader, TranslateModule} from '@ngx-translate/core';
import {TranslateHttpLoader} from '@ngx-translate/http-loader';
import {HttpClient} from '@angular/common/http';
import { HomeComponent } from './home/component/home/home.component';
import { LoginComponent } from './login/component/login.component';
import { OverviewTimeentriesComponent } from './overview-timeentries/component/overview-timeentries.component';
import { NewentryfieldComponent } from './newentryfield/component/newentryfield.component';

@NgModule({
  declarations: [
    AppComponent,
    EntryListComponent,
    HomeComponent,
    LoginComponent,
    OverviewTimeentriesComponent,
    NewentryfieldComponent
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
