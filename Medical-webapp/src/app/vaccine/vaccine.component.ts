import { Component } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import { OnInit } from '@angular/core';
import { DoctorServiceService } from '../appah/doctor-service.service';
import { Doctor } from '../model/doctor.model';import {Router} from "@angular/router";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";

@Component({
  selector: 'app-vaccine',
  templateUrl: './vaccine.component.html',
  styleUrls: ['./vaccine.component.scss']
})
export class VaccineComponent implements OnInit{
  doctors : any;
  private savedDoctor!: Object;
  isFormOpen: boolean = false;
    constructor(private  doctorService:DoctorServiceService, private  router : Router, private formBuilder: FormBuilder) { }

    ngOnInit(): void {
      this.doctorService.getDoctors().subscribe(
        { next:(data)=>{
            this.doctors = data;
          },
          error : (err)=>{}
        });
      this.initFormBuilder();
    }
  getDoctorsdetails(i: any) {
    this.router.navigateByUrl("/doctors/"+i.id)

  }
  deleteDoctors(id: any) {
    // Get confirmation from the user
    if (confirm("Are you sure you want to delete this Doctor?")) {
      // Delete the infraction
      this.doctorService.deleteDoctor(id).subscribe({
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
  public newDocForm!: FormGroup;
  saveNewDoctor() {
    // Step 1: Get the list of existing doctors
    this.doctorService.getDoctors().subscribe({
      next: (doctors: Doctor[]) => {
        // Step 2: Find the maximum doctorId from the existing doctors
        const maxDoctorId = Math.max(...doctors.map((doc) => doc.doctorId), 0);
  
        // Step 3: Increment the maximum doctorId by 1 to get the next available ID
        const nextDoctorId = maxDoctorId + 1;
  
        // Step 4: Use the next available ID when saving the new doctor
        let name = this.newDocForm.get("name")?.value;
        let specialization = this.newDocForm.get("specialization")?.value;
        let contactInformation = this.newDocForm.get("contactInformation")?.value;
        let appointments = this.newDocForm.get("appointments")?.value;
  
        let doctor: Doctor = {
          doctorId: nextDoctorId,
          name: name,
          specialization: specialization,
          contactInformation: contactInformation,
          appointments: appointments,
          patients: function (patients: any): unknown {
            throw new Error('Function not implemented.');
          }
        };
        if (confirm("Are you sure you want to add this Doctor?")) {
          this.doctorService.saveDoctor(doctor).subscribe({
          
            next: (data: Doctor) => {
              this.savedDoctor = data;
                        // Show success message
            window.alert("Doctor added successfully!");
  
            // Refresh the page
            window.location.reload();
            // Navigate back to home
            // this.router.navigate(['/doctors']);
            },
            error: (err) => console.log(err),
          });

        }
        
      },
      error: (err) => console.log(err),
    });

  }
  


  



  private initFormBuilder() {
    this.newDocForm = this.formBuilder.group({
      name: this.formBuilder.control('', [Validators.required]),
      specialization: this.formBuilder.control('', [Validators.required]),
      contactInformation: this.formBuilder.control('', [Validators.required]),
    });
  }

}
