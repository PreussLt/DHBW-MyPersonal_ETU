import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Router} from "@angular/router";
import {AuthService} from "../../services/auth/auth.service";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

    loginForm: FormGroup;
    ssoActive: boolean;
    invalid: boolean;
    returnUrl: string;

    constructor(
    private formBuilder: FormBuilder,
    private router : Router,
    private authService : AuthService,
    ) {
      this.invalid = false;
      //SSO oder Manueller Login?
      this.getSsoActive();
    }

  ngOnInit() {
    this.loginForm = this.formBuilder.group({
      userid: ['', Validators.required],
      password: ['', Validators.required]
    });
    this.returnUrl = '/home';
    this.authService.logout();
  }
  getSsoActive(): void{
    this.authService.isSsoActive().subscribe(active =>{
      this.ssoActive = active;
      if(this.ssoActive){
        this.login();
      }
    });
  }
  get f() { return this.loginForm.controls; }

  login() {
    //SSO Login
    if(this.ssoActive){
      this.authService.ssoAuth().subscribe(authenticated => {
        console.log(2)
        if(authenticated){
          this.authService.getSso().subscribe(mid => {
            console.log(mid)
            sessionStorage.setItem('isLoggedIn', "true");
            sessionStorage.setItem('mid', mid);
            this.router.navigate([this.returnUrl]);
          })

        }
      })
    }

    //Login über Eingabefelder
    else {
      if (this.loginForm.invalid) {
        return;
      }
      else {
        this.authService.authenticate(this.f['userid'].value, this.f['password'].value).subscribe(data => {
          if(data){
            sessionStorage.setItem('isLoggedIn', "true");
            this.authService.getMId(this.f['userid'].value).subscribe(data => {
              sessionStorage.setItem('mid', data);
              this.router.navigate([this.returnUrl]);
            });
          }
          else {
            this.invalid = true;
          }
        });
      }
    }
  }

}
