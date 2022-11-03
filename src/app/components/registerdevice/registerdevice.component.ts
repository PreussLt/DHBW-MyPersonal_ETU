import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {AuthService} from "../../services/auth/auth.service";
import {User} from "../../models/user/user";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";

@Component({
  selector: 'app-registerdevice',
  templateUrl: './registerdevice.component.html',
  styleUrls: ['./registerdevice.component.css']
})
export class RegisterdeviceComponent implements OnInit {
  registerDeviceForm: FormGroup
  showModal: boolean;
  showSuccessModal: boolean;
  showFailureModal: boolean;
  users: User[]
  @ViewChild('userSelect', {static:false}) userSelect: ElementRef<HTMLSelectElement>;

  invalid: boolean;
  constructor(
    private authService: AuthService,
    private formBuilder: FormBuilder
  ) { }

  ngOnInit(): void {
    this.registerDeviceForm  = this.formBuilder.group({
      windows: ['' ,Validators.required],
      domain: ['' ,Validators.required],
      user:[-1 ,Validators.required],
    });
    this.authService.getUsers().subscribe(data => {
      this.users = data;
    })
  }

  close(){
    this.invalid = false;
    if(Number(this.f['user'].value)>=0 && this.registerDeviceForm.valid){
      this.showModal = !this.showModal;
    } else this.invalid = true;
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

  register() {
    this.authService.registerDevice(this.f['user'].value, this.f['windows'].value, this.f['domain'].value).subscribe(success => {
      this.success(success)
    })
  }
  get userSelected() {
    return this.userSelect.nativeElement.options[this.userSelect.nativeElement.selectedIndex].text
  }

  get f() { return this.registerDeviceForm.controls; }
}
