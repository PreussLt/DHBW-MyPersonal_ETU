import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RegisterdeviceComponent } from './registerdevice.component';

describe('RegisterdeviceComponent', () => {
  let component: RegisterdeviceComponent;
  let fixture: ComponentFixture<RegisterdeviceComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RegisterdeviceComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RegisterdeviceComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
