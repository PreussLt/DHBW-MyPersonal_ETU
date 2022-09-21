import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ChangePwComponent } from './change-pw.component';

describe('ChangePwComponent', () => {
  let component: ChangePwComponent;
  let fixture: ComponentFixture<ChangePwComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ChangePwComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ChangePwComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
