import { TestBed } from '@angular/core/testing';

import { TimeEntryService } from './time-entry.service';

describe('TimeEntryService', () => {
  let service: TimeEntryService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(TimeEntryService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
