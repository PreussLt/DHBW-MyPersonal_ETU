import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GleitzeitComponent } from './gleitzeit.component';

describe('GleitzeitComponent', () => {
  let component: GleitzeitComponent;
  let fixture: ComponentFixture<GleitzeitComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ GleitzeitComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(GleitzeitComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
