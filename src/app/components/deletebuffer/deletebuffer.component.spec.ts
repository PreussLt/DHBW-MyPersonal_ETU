import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DeletebufferComponent } from './deletebuffer.component';

describe('DeletebufferComponent', () => {
  let component: DeletebufferComponent;
  let fixture: ComponentFixture<DeletebufferComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DeletebufferComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DeletebufferComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
