import { TestBed } from '@angular/core/testing';
import { AppointmentServiceService } from './appointment-service.service';

describe('PatientServiceService', () => {
  let appah: AppointmentServiceService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    appah = TestBed.inject(AppointmentServiceService);
  });

  it('should be created', () => {
    expect(appah).toBeTruthy();
  });
});