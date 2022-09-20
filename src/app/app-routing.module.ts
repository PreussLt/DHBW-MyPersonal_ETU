import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {LoginComponent} from "./login/component/login.component";
import {HomeComponent} from "./home/component/home/home.component";
import {AuthGuard} from "./login/guards/auth.guard";
import {AppComponent} from "./app.component";
import {OverviewTimeentriesComponent} from "./overview-timeentries/component/overview-timeentries.component";
import {NewentryfieldComponent} from "./newentryfield/component/newentryfield.component";

//Für verschiedene Request-Urls Ergebniskomponenten setzen
const routes: Routes = [
  {path: 'login', component: LoginComponent},
  {path: 'home', component: HomeComponent, canActivate:[AuthGuard]},
  {path: 'overview', component: OverviewTimeentriesComponent, canActivate:[AuthGuard]},
  {path: 'newEntry', component: NewentryfieldComponent, canActivate:[AuthGuard]},
  {path: '', component: AppComponent, canActivate:[AuthGuard]}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
