import { TestBed } from '@angular/core/testing';

import { ArbeitslisteService } from './arbeitsliste.service';

describe('ArbeitslisteService', () => {
  let service: ArbeitslisteService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ArbeitslisteService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
