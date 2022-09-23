import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ArbeitstagslisteComponent } from './arbeitstagsliste.component';

describe('ArbeitstagslisteComponent', () => {
  let component: ArbeitstagslisteComponent;
  let fixture: ComponentFixture<ArbeitstagslisteComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ArbeitstagslisteComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ArbeitstagslisteComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
