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
  invalidPW: boolean;

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
    console.log('logout');
    this.authService.logout();
    this.router.navigate(['/login']);
  }

  isPWValid():boolean{
    this.invalid = false;
    if(this.isPwMatching()){
      let uppercase = false;
      let lowercase = false;
      let number = false;
      let pw = this.f['newPW'].value;
      let length = pw.length >= 12;

      for (var i = 0; i < pw.length; i++){
        if(Number(pw.charAt(i)) >= 0 && Number(pw.charAt(i))<= 9) number = true;
        else if(pw.charAt(i) === pw.charAt(i).toUpperCase()) uppercase = true;
        else if(pw.charAt(i) === pw.charAt(i).toLowerCase()) lowercase = true;
      }
      let isValid = uppercase && lowercase && number && length;
      this.invalidPW = !isValid;
      return isValid;
    }else return false;

  }

  isPwMatching(): boolean{
    if (this.f['newPW'].value === this.f['confirm'].value) {
      this.notMatching = false;
      return true;
    }
    else {
      this.notMatching = true;
      return false;
    }
  }

  changePassword(): void {
    if(this.changePwForm.valid) {
      this.invalidForm = false;
      if (this.isPWValid()) {
        this.invalidPW = false;
        this.authService.isPwMatching(this.f['oldPW'].value).subscribe(data => {
          if (!data) {
            this.invalid = true;
          } else {
            this.invalid = false;

            this.authService.changePW(this.f['newPW'].value).subscribe(changed => {
              if (changed) {
                this.logout()
              }
            })

          }
        })

      }
    }
    else this.invalidForm = true;
  }


}
