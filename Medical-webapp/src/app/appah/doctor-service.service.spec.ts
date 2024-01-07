import { TestBed } from '@angular/core/testing';

import { DoctorServiceService } from './doctor-service.service';

describe('DoctorServiceService', () => {
  let appah: DoctorServiceService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    appah = TestBed.inject(DoctorServiceService);
  });

  it('should be created', () => {
    expect(appah).toBeTruthy();
  });
});
