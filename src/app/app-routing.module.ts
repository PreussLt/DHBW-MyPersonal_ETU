import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {LoginComponent} from "./components/login/login.component";
import {HomeComponent} from "./components/home/home.component";
import {AuthGuard} from "./guards/auth/auth.guard";
import {AppComponent} from "./components/app.component";
import {OverviewTimeentriesComponent} from "./components/overview-timeentries/overview-timeentries.component";
import {NewentryfieldComponent} from "./components/newentryfield/newentryfield.component";
import {ArbeitstagslisteComponent} from "./components/arbeitstagsliste/arbeitstagsliste.component";
import {EditEntryComponent} from "./components/edit-entry/edit-entry.component";
import {SettingsComponent} from "./components/settings/settings/settings.component";

//Für verschiedene Request-Urls Ergebniskomponenten setzen
const routes: Routes = [
  {path: 'login', component: LoginComponent},
  {path: 'home', component: HomeComponent, canActivate:[AuthGuard]},
  {path: 'overview', component: OverviewTimeentriesComponent, canActivate:[AuthGuard]},
  {path: 'newEntry', component: NewentryfieldComponent, canActivate:[AuthGuard]},
  {path: 'arbeitstage', component: ArbeitstagslisteComponent, canActivate:[AuthGuard]},
  {path: 'settings', component: SettingsComponent, canActivate:[AuthGuard]},
  {path: 'editEntry/:id', component: EditEntryComponent, canActivate:[AuthGuard]},
  {path: '', component: AppComponent, canActivate:[AuthGuard]}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
