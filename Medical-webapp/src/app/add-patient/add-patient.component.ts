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
    // Step 1: Get the list of existing patients
    this.patientservice.getPatients().subscribe({
      next: (patients: Patient[]) => {
        // // Step 2: Find the maximum PatientId from the existing patients
        // const maxpatientId = Math.max(...patients.map((pat) => pat.patientId), 1);
  
        // // Step 3: Increment the maximum PatientId by 1 to get the next available ID
        // const nextpatientId = maxpatientId + 1;
  
        // Step 4: Use the next available ID when saving the new patient
        let patientId = this.newPatForm.get("patientId")?.value;
        let name = this.newPatForm.get("name")?.value;
        let dateOfBirth = this.newPatForm.get("dateOfBirth")?.value;
        let contactNumber = this.newPatForm.get("contactNumber")?.value;
        let appointments = this.newPatForm.get("appointments")?.value;
        let doctorId = this.newPatForm.get("doctorId")?.value;
        let medicalRecords = this.newPatForm.get("medicalRecords")?.value;
  
        let patient: Patient = {
          patientId: patientId,
          name: name,
          dateOfBirth: dateOfBirth,
          contactNumber: contactNumber,
          doctorId:doctorId,
          medicalRecords:medicalRecords,
          appointments:appointments,
        };
        if (confirm("Are you sure you want to add this Patient?")) {
          this.patientservice.savePatient(patient).subscribe({
          
            next: (data: Patient) => {
              this.savedPatient = data;
                        // Show success message
            window.alert("Patient added successfully!");
  
            // Refresh the page
            window.location.reload();
            },
            error: (err) => console.log(err),
          });

        }
        
      },
      error: (err) => console.log(err),
    });

  }
  
  



  private initFormBuilder() {
    this.newPatForm = this.formBuilder.group({
      name: this.formBuilder.control('', [Validators.required]),
      dateOfBirth: this.formBuilder.control('', [Validators.required]),
      contactNumber: this.formBuilder.control('', [Validators.required]),
    });
  }

}
