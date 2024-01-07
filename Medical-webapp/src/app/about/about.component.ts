import { Component, OnInit } from '@angular/core';
import { DoctorServiceService } from '../appah/doctor-service.service';
import { Doctor } from '../model/doctor.model';
import { Patient } from '../model/patient.model';
import { PatientServiceService } from '../appah/patient-service.service';
import { AppointmentServiceService } from '../appah/appointment-service.service';
import { Appointment } from '../model/appointement.model';

@Component({
  selector: 'app-about',
  templateUrl: './about.component.html',
  styleUrls: ['./about.component.scss']
})
export class AboutComponent implements OnInit {
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

    this.appoiservice.getAllAppointments().subscribe({
      next: (data) => {
        this.appointments = data;
        console.log(this.appointments); // Log the data to the console
      },
      error: (err) => {
        console.error('Error fetching appointments:', err);
      },
    });
  }

}
