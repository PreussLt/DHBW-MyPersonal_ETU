import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NewentryfieldComponent } from './newentryfield.component';

describe('NewentryfieldComponent', () => {
  let component: NewentryfieldComponent;
  let fixture: ComponentFixture<NewentryfieldComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ NewentryfieldComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(NewentryfieldComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
