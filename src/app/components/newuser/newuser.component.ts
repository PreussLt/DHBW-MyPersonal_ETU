import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {User} from "../../models/user/user";
import {AuthService} from "../../services/auth/auth.service";

@Component({
  selector: 'app-newuser',
  templateUrl: './newuser.component.html',
  styleUrls: ['./newuser.component.css']
})
export class NewuserComponent implements OnInit {
  newUserForm: FormGroup;
  notMatching: boolean;
  invalid: boolean;
  pressed: boolean;
  showModal: boolean;
  showSuccessModal: boolean;
  showFailureModal: boolean;

  //Elemente als HTML Element
  @ViewChild('arbeitsmodellSelect', {static:false}) arbeitsmodellSelect: ElementRef<HTMLSelectElement>;
  @ViewChild('uKlasseSelect', {static:false}) uKlasseSelect: ElementRef<HTMLSelectElement>;


  constructor(
    private authService: AuthService,
    private formBuilder: FormBuilder
  ) { }

  ngOnInit(): void {
    this.newUserForm  = this.formBuilder.group({
      vorname: ['' ,Validators.required],
      nachname: ['' ,Validators.required],
      personalnummer:['' ,Validators.required],
      geburtstag: ['' ,Validators.required],
      passwort:['' ,Validators.required],
      passwortBestaetigen:['' ,Validators.required],
      arbeitsmodell: [1 ,Validators.required],
      uKlasse: [2 ,Validators.required]
    });}

  createUser():void{
    this.notMatching = false;
    this.authService.createUser(this.setUserData()).subscribe(created => this.success(created))
  }

  generatePW(){
    var length = 12,
      charset = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789",
      randomstring = "";
    for (var i = 0, n = charset.length; i < length; ++i) {
      randomstring += charset.charAt(Math.floor(Math.random() * n));
    }

    this.f['passwort'].setValue(randomstring);
    this.f['passwortBestaetigen'].setValue(randomstring);
  }

  isPWValid():boolean{
    this.invalid = false;
    if(this.isPwMatching()){
      let uppercase = false;
      let lowercase = false;
      let number = false;
      let pw = this.f['passwort'].value;
      let length = pw.length >= 12;

      for (var i = 0; i < pw.length; i++){
        if(Number(pw.charAt(i)) >= 0 && Number(pw.charAt(i))<= 9) number = true;
        else if(pw.charAt(i) === pw.charAt(i).toUpperCase()) uppercase = true;
        else if(pw.charAt(i) === pw.charAt(i).toLowerCase()) lowercase = true;
      }
      return uppercase && lowercase && number && length;

    }else return false;
  }

  isPwMatching(): boolean{
    if (this.f['passwort'].value === this.f['passwortBestaetigen'].value) {
      this.notMatching = false;
      return true;
    }
    else {
      this.notMatching = true;
      return false;
    }
  }

  setUserData(): User{
    let vorname = this.f['vorname'].value;
    let nachname = this.f['nachname'].value;
    let passwort = this.f['passwort'].value;
    let personalnummer = this.f['personalnummer'].value;
    let gebDatum = this.f['geburtstag'].value;
    let uKlasse = this.f['uKlasse'].value;
    let arbeitsmodell = this.f['arbeitsmodell'].value;
    return new User(vorname, nachname, personalnummer, passwort, arbeitsmodell, Number(uKlasse), gebDatum);
  }

  get f() { return this.newUserForm.controls; }

  show() {
    this.pressed = true;
    if(this.newUserForm.valid) {
      if(this.isPWValid()) {
        this.showModal = !this.showModal;
      }else{
          this.invalid = true;
      }
    }
  }

  close(){
    this.showModal = !this.showModal;
  }

  success(created:boolean){
    this.close();
    if(created) this.toggleSuccessModal();
    else this.toggleFailureModal();
  }

  toggleSuccessModal(){
    this.showSuccessModal = !this.showSuccessModal
  }

  toggleFailureModal(){
    this.showFailureModal = !this.showFailureModal;
  }
  get arbeitsmodell() {
    return this.arbeitsmodellSelect.nativeElement.options[this.arbeitsmodellSelect.nativeElement.selectedIndex].text
  }

  get uKlasse(){
    return this.uKlasseSelect.nativeElement.options[this.uKlasseSelect.nativeElement.selectedIndex].text
  }
}
