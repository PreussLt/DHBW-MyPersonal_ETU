import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {AuthService} from "../../services/auth/auth.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-change-pw',
  templateUrl: './change-pw.component.html',
  styleUrls: ['./change-pw.component.css']
})
export class ChangePwComponent implements OnInit {

  changePwForm: FormGroup;
  invalid: boolean;
  notMatching: boolean;
  invalidForm: boolean;

  constructor(private formBuilder: FormBuilder, private authService: AuthService, private router: Router){

  }

  ngOnInit(): void {
    this.changePwForm = this.formBuilder.group({
      oldPW: ['', Validators.required],
      newPW: ['', Validators.required],
      confirm: ['', Validators.required]
    })
  }

  get f() { return this.changePwForm.controls; }

  logout() {
    // console.log('logout');
    this.authService.logout();
    this.router.navigate(['/login']);
  }

  changePassword(): void {
    if(this.changePwForm.valid) {
      this.invalidForm = false;
      if (this.f['newPW'].value === this.f['confirm'].value) {
        this.notMatching = false;
        this.authService.isPwMatching(this.f['oldPW'].value).subscribe(data => {
          if (!data) {
            this.invalid = true;
          } else {
            this.invalid = false;
            this.authService.changePW(this.f['newPW'].value).subscribe(dta => {
              if (dta) {
                this.logout()
              }
            })
          }
        })
      } else this.notMatching = true;
    }
    else this.invalidForm = true;
  }


}
