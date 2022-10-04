import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GrenzwertSettingsComponent } from './grenzwert-settings.component';

describe('GrenzwertSettingsComponent', () => {
  let component: GrenzwertSettingsComponent;
  let fixture: ComponentFixture<GrenzwertSettingsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ GrenzwertSettingsComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(GrenzwertSettingsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
