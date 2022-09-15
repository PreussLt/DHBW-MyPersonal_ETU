import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {EntryListComponent} from "./entry-list/entry-list.component";
import {LoginComponent} from "./login/component/login.component";
import {HomeComponent} from "./home/component/home/home.component";
import {AuthGuard} from "./login/guards/auth.guard";
import {AppComponent} from "./app.component";

//Für verschiedene Request-Urls Ergebniskomponenten setzen
const routes: Routes = [
  {path: 'entries', component: EntryListComponent},
  {path: 'login', component: LoginComponent},
  {path: 'home', component: HomeComponent, canActivate:[AuthGuard]},
  {path: '', component: AppComponent, canActivate:[AuthGuard]}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
