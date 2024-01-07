import { Component } from '@angular/core';
import { AppointmentServiceService } from 'src/app/appah/appointment-service.service';
import { DoctorServiceService } from 'src/app/appah/doctor-service.service';
import { PatientServiceService } from 'src/app/appah/patient-service.service';
import { Appointment } from 'src/app/model/appointement.model';
import { Doctor } from 'src/app/model/doctor.model';
import { Patient } from 'src/app/model/patient.model';

@Component({
  selector: 'app-call-us',
  templateUrl: './call-us.component.html',
  styleUrls: ['./call-us.component.scss']
})
export class CallUsComponent {

  doctors: Doctor[] = [];
  patients: Patient[]=[];
  appointments:Appointment[]=[];

  constructor(private doctorservice: DoctorServiceService,private patientservice:PatientServiceService,private appoiservice:AppointmentServiceService ) {}

  ngOnInit(): void {
    this.doctorservice.getDoctors().subscribe({
      next: (data) => {
        this.doctors = data;
        console.log(this.doctors); // Log the data to the console
      },
      error: (err) => {
        console.error('Error fetching doctors:', err);
      },
    });
    this.patientservice.getPatients().subscribe({
      next: (data) => {
        this.patients = data;
        console.log(this.patients); // Log the data to the console
      },
      error: (err) => {
        console.error('Error fetching patients:', err);
      },
    });
  }



}
