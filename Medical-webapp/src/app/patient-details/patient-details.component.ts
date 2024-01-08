import { Component, OnInit } from '@angular/core';
import { PatientServiceService } from '../appah/patient-service.service';
import { Patient } from '../model/patient.model';
import { Doctor } from '../model/doctor.model';
import { switchMap } from 'rxjs/operators';

@Component({
  selector: 'app-patient-details',
  templateUrl: './patient-details.component.html',
  styleUrls: ['./patient-details.component.scss']
})
export class PatientDetailsComponent implements OnInit {
  patients: Patient[] = [];
  doctors: Doctor[] = [];
  images: number[] = [1,2,3,4,5]; 
  showPopup: boolean = false;
  doctorservice: any;

  constructor(private patientservice: PatientServiceService) {}

  ngOnInit(): void {
    this.patientservice.getPatients().subscribe({
      next: (data) => {
        // this.patients = data;
        // console.log('Patients Data:', this.patients);
        // Sort patients by ID before assigning them
      this.patients = data.sort((a, b) => a.patientId - b.patientId);
      console.log('Patients Data:', this.patients);

      },
      error: (err) => {
        console.error('Error fetching patients:', err);
      },
    });

    
  }
  getMedicalRecords(medicalRecords: { [key: string]: any }): any[] {
    return medicalRecords ? Object.values(medicalRecords) : [];
  }
  
  getAppointments(appointments: { [key: string]: any }): any[] {
    return appointments ? Object.values(appointments) : [];
  }
  
  
  // getDoctorName(doctorId: number): string {
  //   const doctor = this.doctors.find(d => d.doctorId === doctorId);
  //   return doctor ? doctor.name : 'Unknown Doctor';
  // }

  deletepatients(id: any) {
    // Get confirmation from the user
    if (confirm("Are you sure you want to delete this Patient?")) {
      // Delete the infraction
      this.patientservice.deletePatient(id).subscribe({
        next: () => {
          // Show success message
          window.alert("Patient deleted successfully!");

          // Refresh the page
          window.location.reload();
        },
        error: err => console.log(err)
      });
    }
  }

}
