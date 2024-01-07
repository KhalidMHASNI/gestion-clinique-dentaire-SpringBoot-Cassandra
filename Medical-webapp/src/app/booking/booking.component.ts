import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AppointmentServiceService } from '../appah/appointment-service.service';
import { Doctor } from '../model/doctor.model';
import { Patient } from '../model/patient.model';
import { Router } from '@angular/router';

@Component({
  selector: 'app-booking',
  templateUrl: './booking.component.html',
  styleUrls: ['./booking.component.scss']
})
export class BookingComponent implements OnInit {
  appointments: any;
  isFormOpen: boolean = false;
  private savedAppointment!: Object;
  doctorService: any;
  patientService: any;

  constructor(
    private appointmentservice: AppointmentServiceService,
    private router: Router,
    private formBuilder: FormBuilder
  ) { }

  ngOnInit(): void {
    this.appointmentservice.getAllAppointments().subscribe({
      next: (data) => {
        this.appointments = data;
      },
      error: (err) => {
        console.error("Error fetching appointments:", err);
      }
    });
    this.initFormBuilder();
  }
  getAppointmentsdetails(i: any) {
    this.router.navigateByUrl("/appointments/" + i.id);
  }

  openForm() {
    this.isFormOpen = true;
  }

  closeForm() {
    this.isFormOpen = false;
  }

  public newAppoForm!: FormGroup;


  saveNewAppointment() {
    if (this.newAppoForm.valid) {
      const patientId = this.newAppoForm.get("patientId")?.value;
      const doctorId = this.newAppoForm.get("doctorId")?.value;
      const reasonForVisit = this.newAppoForm.get("reasonForVisit")?.value;
      const appointmentDate = this.newAppoForm.get("appointmentDate")?.value;
  
      console.log("Data:", reasonForVisit, appointmentDate, doctorId, patientId);
  
      this.appointmentservice.createAppointmentForPatientWithDoctor(patientId, doctorId, appointmentDate, reasonForVisit)
        .subscribe({
          next: (data) => {
            console.log("Success:", data);
            window.alert("Appointment added successfully!");
          },
          error: (err) => {
            console.error('Error creating appointment:', err);
            window.alert('Failed Appointment! ' + err); // Display the error message in the alert
          },
          
          complete: () => {
            console.log("Request completed.");
          }
        });
    } else {
      window.alert("Please fill in all required fields.");
    }
  }
  
  

  private initFormBuilder() {
    this.newAppoForm = this.formBuilder.group({
      patientId: ['', [Validators.required]],
      doctorId: ['', [Validators.required]],
      appointmentDate: ['', [Validators.required]],
      reasonForVisit: ['', [Validators.required]],
    });
  }
}

export { AppointmentServiceService };