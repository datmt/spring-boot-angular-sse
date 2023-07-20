import { TestBed } from '@angular/core/testing';

import { FortuneTellerService } from './fortune-teller.service';

describe('FortuneTellerService', () => {
  let service: FortuneTellerService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(FortuneTellerService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
