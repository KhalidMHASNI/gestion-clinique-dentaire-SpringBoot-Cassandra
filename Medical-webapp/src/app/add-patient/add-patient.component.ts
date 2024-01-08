import { Component } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import { OnInit } from '@angular/core';
import { PatientServiceService } from '../appah/patient-service.service';
import { Patient } from '../model/patient.model';import {Router} from "@angular/router";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
@Component({
  selector: 'app-add-patient',
  templateUrl: './add-patient.component.html',
  styleUrls: ['./add-patient.component.scss']
})



export class AddPatientComponent implements OnInit{
  patients : any;
  private savedPatient!: Object;
  isFormOpen: boolean = false;
    constructor(private  patientservice:PatientServiceService, private  router : Router, private formBuilder: FormBuilder) { }

    ngOnInit(): void {
      this.patientservice.getPatients().subscribe(
        { next:(data)=>{
            this.patients = data;
          },
          error : (err)=>{}
        });
      this.initFormBuilder();
    }
  getpatientsdetails(i: any) {
    this.router.navigateByUrl("/patients/"+i.id)

  }
  deletepatients(id: any) {
    // Get confirmation from the user
    if (confirm("Are you sure you want to delete this Doctor?")) {
      // Delete the infraction
      this.patientservice.deletePatient(id).subscribe({
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



  openForm() {
    this.isFormOpen = true;
  }

  closeForm() {
    this.isFormOpen = false;
  }
  public newPatForm!: FormGroup;

  saveNewPatient() {
    let name = this.newPatForm.get('name')?.value;
    let dateOfBirth = this.newPatForm.get('dateOfBirth')?.value;
    let contactNumber = this.newPatForm.get('contactNumber')?.value;
  
    let patient: Patient = {
      patientId: 0, // Set it to 0; the server will assign the correct ID
      name: name,
      dateOfBirth: dateOfBirth,
      contactNumber: contactNumber,
      doctorId: 0, // Set it to 0 or the default value
      medicalRecords: {}, // Set it to an empty object or the default value
      appointments: {}, // Set it to an empty object or the default value
    };
  
    if (confirm('Are you sure you want to add this Patient?')) {
      this.patientservice.savePatient(patient).subscribe({
        next: (data: Patient) => {
          this.savedPatient = data;
          // Show success message
          window.alert('Patient added successfully!');
  
          // Refresh the page
          window.location.reload();
        },
        error: (err) => console.log(err),
      });
    }
  }
  
    

  private initFormBuilder() {
    this.newPatForm = this.formBuilder.group({
      name: this.formBuilder.control('', [Validators.required]),
      dateOfBirth: this.formBuilder.control('', [Validators.required]),
      contactNumber: this.formBuilder.control('', [Validators.required]),
    });
  }

}
