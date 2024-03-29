import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './components/app.component';
import {TimeEntryService} from "./services/time-entry/time-entry.service";
import {HttpClient, HttpClientModule} from "@angular/common/http";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {TranslateLoader, TranslateModule} from '@ngx-translate/core';
import {TranslateHttpLoader} from '@ngx-translate/http-loader';
import {HomeComponent} from './components/home/home.component';
import {LoginComponent} from './components/login/login.component';
import {OverviewTimeentriesComponent} from './components/overview-timeentries/overview-timeentries.component';
import {NewentryfieldComponent} from './components/newentryfield/newentryfield.component';
import {ChangePwComponent} from './components/change-pw/change-pw.component';
import {ArbeitstagslisteComponent} from './components/arbeitstagsliste/arbeitstagsliste.component';
import {EditEntryComponent} from "./components/edit-entry/edit-entry.component";
import {SettingsComponent} from './components/settings/settings/settings.component';
import { GrenzwertSettingsComponent } from './components/grenzwert-settings/grenzwert-settings.component';
import { LoadingComponent } from './components/loading/loading.component';
import { NewuserComponent } from './components/newuser/newuser.component';
import { GleitzeitComponent } from './components/gleitzeit/gleitzeit.component';
import { DeletebufferComponent } from './components/deletebuffer/deletebuffer.component';
import { RegisterdeviceComponent } from './components/registerdevice/registerdevice.component';

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    LoginComponent,
    OverviewTimeentriesComponent,
    NewentryfieldComponent,
    ChangePwComponent,
    ArbeitstagslisteComponent,
    EditEntryComponent,
    SettingsComponent,
    GrenzwertSettingsComponent,
    LoadingComponent,
    NewuserComponent,
    GleitzeitComponent,
    DeletebufferComponent,
    RegisterdeviceComponent,
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
