import { TestBed } from '@angular/core/testing';

import { GrenzwertService } from './grenzwert.service';

describe('GrenzwertService', () => {
  let service: GrenzwertService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(GrenzwertService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
