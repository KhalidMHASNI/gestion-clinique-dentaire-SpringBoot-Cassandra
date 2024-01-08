import { Component, OnInit } from '@angular/core';
import { DoctorServiceService } from '../appah/doctor-service.service';
import { Doctor } from '../model/doctor.model';
import { Appointment } from '../model/appointement.model';
import { FormGroup, FormBuilder, Validators, NgForm } from '@angular/forms';
import { of } from 'rxjs';

@Component({
  selector: 'app-doctor-details',
  templateUrl: './doctor-details.component.html',
  styleUrls: ['./doctor-details.component.scss']
})
export class DoctorDetailsComponent implements OnInit{
  doctors: Doctor[] = [];
  showPopup: boolean = false;
  isFormOpen: boolean = false;
  d:any;

  updateForm: FormGroup; // Add this line

  constructor(private doctorservice: DoctorServiceService, private fb: FormBuilder) {
    // Initialize the form group in the constructor
    this.updateForm = this.fb.group({
      updatedName: ['', Validators.required],
      updatedSpecialization: ['', Validators.required],
      updatedContactInformation: ['', Validators.required],
    });
  }


  ngOnInit(): void {
    this.doctorservice.getDoctors().subscribe({
      next: (data) => {

        this.doctors = data.sort((a, b) => a.doctorId - b.doctorId);
        console.log('doctors Data:', this.doctors);

      },
      error: (err) => {
        console.error('Error fetching patients:', err);
      },
    });

    
  }

  deleteDoctors(id: any) {
    // Get confirmation from the user
    if (confirm("Are you sure you want to delete this Doctor?")) {
      // Delete the infraction
      this.doctorservice.deleteDoctor(id).subscribe({
        next: () => {
          // Show success message
          window.alert("Doctor deleted successfully!");

          // Refresh the page
          window.location.reload();
        },
        error: err => console.log(err)
      });
    }
  }
  
  
  getDoctorAppointments(appointments: { [key: string]: Appointment }): Appointment[] {
    const appointmentArray: Appointment[] = Object.values(appointments);
    // Convert the date strings to Date objects
    return appointmentArray.map(appointment => {
      return { ...appointment, appointment_date: new Date(appointment.appointment_date) };
    });
  }
  
  updateDoctor(id: any, form: NgForm) {
    console.log('Updating doctor with ID:', id);
        const updatedDoctorData = form.value;
        console.log('Updated data:', updatedDoctorData);


    this.doctorservice.updateDoctor(id, updatedDoctorData).subscribe({
        next: (data) => {
            // Handle success
            console.log('Doctor updated successfully:', data);

            // Refresh the page or update the doctors array
            this.loadDoctors(); // You may need to implement a function to reload doctors

            // Close the form
            this.closeForm();
        },
        error: (err) => {
            console.error('Error updating doctor:', err);
        },
        
    });
    return of('Update success');
    
}

  
  

  loadDoctors() {
    this.doctorservice.getDoctors().subscribe((doctors) => {
      this.doctors = doctors;
    });
  }
  

  openForm(doctors:any) {
    this.isFormOpen = true;
    this.d=this.doctors;
  }
  
  closeForm() {
    this.isFormOpen = false;
  }
  private initFormBuilder() {

  }

}

