    import { Component, OnInit } from '@angular/core';
    import {FormBuilder, FormGroup, Validators} from "@angular/forms";
    import {Router} from "@angular/router";
    import {Login} from "../interfaces/login";
    import {AuthserviceService} from "../service/authservice.service";
    import {TranslateService} from "@ngx-translate/core";

    @Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

    model: Login = { userid: "admin", password: "cool" };
    loginForm: FormGroup;
    invalid: boolean;
    returnUrl: string;

    constructor(
    private formBuilder: FormBuilder,
    private router : Router,
    private authService : AuthserviceService,
    private translate: TranslateService
    ) {
      translate.setDefaultLang('de');
      translate.use('de');
      this.invalid = false;
    }

  ngOnInit() {
    this.loginForm = this.formBuilder.group({
      userid: ['', Validators.required],
      password: ['', Validators.required]
    });
    this.returnUrl = '/home';
    this.authService.logout();
  }

  get f() { return this.loginForm.controls; }

  login() {
    // stop here if form is invalid
    if (this.loginForm.invalid) {
      return;
    }
    else {
      this.authService.authenticate(this.f['userid'].value, this.f['password'].value).subscribe(data => {
        if(data){
          sessionStorage.setItem('isLoggedIn', "true");
          sessionStorage.setItem('token', this.f['userid'].value);
          this.router.navigate([this.returnUrl]);
        }
        else {
          this.invalid = true;
        }
      });
    }
  }
  }
