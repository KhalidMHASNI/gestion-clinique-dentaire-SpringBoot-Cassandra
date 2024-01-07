import { TestBed } from '@angular/core/testing';

import { PatientServiceService } from './patient-service.service';

describe('PatientServiceService', () => {
  let appah: PatientServiceService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    appah = TestBed.inject(PatientServiceService);
  });

  it('should be created', () => {
    expect(appah).toBeTruthy();
  });
});
