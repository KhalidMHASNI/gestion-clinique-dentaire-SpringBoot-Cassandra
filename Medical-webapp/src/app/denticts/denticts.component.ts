import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { DoctorServiceService } from '../appah/doctor-service.service';
import { AppointmentServiceService } from '../appah/appointment-service.service';
import { Doctor } from '../model/doctor.model';
import { Appointment } from '../model/appointement.model';

@Component({
  selector: 'app-denticts',
  templateUrl: './denticts.component.html',
  styleUrls: ['./denticts.component.scss'],
})
export class DentictsComponent implements OnInit {
  doctors: Doctor[] = [];
  newAppoForm!: FormGroup;
  appointments: Appointment[] = [];
  latestAppointmentId: number = 0; // Variable to track the latest ID

  constructor(
    private doctorservice: DoctorServiceService,
    private appointmentService: AppointmentServiceService,
    private cdr: ChangeDetectorRef,
    private formBuilder: FormBuilder
  ) {}

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

    // Initialize the latestAppointmentId based on existing appointments
    this.latestAppointmentId = this.appointments.length > 0 ? Math.max(...this.appointments.map(app => app.appointment_id)) : 0;

    this.initFormBuilder();
  }

  saveNewAppointment(doctorId: number) {
    const patientId = this.newAppoForm.get('patientId')?.value;
    const appointmentDate = this.newAppoForm.get('appointmentDate')?.value;
    const reasonForVisit = this.newAppoForm.get('reasonForVisit')?.value;
  
    console.log('Appointment details:', patientId, doctorId, appointmentDate, reasonForVisit);
  
    if (!appointmentDate || isNaN(new Date(appointmentDate).getTime())) {
      window.alert('Please provide a valid appointment date.');
      return;
    }
  
    // Step 2: Find the maximum appointment_id from the existing appointments
    const maxAppoId = Math.max(...this.appointments.map((appoi) => appoi.appointment_id), 0);
  
    // Step 3: Increment the maximum appointment_id by 1 to get the next available ID
    const nextAppoId = maxAppoId + 1;
  
    // Update the client-side representation with the correct ID and convert date to string
    const newAppointment: Appointment = {
      appointment_id: nextAppoId, // Assign a new ID
      doctor_id: doctorId,
      appointment_date: new Date(appointmentDate).toISOString(), // Convert to ISO string
      patient_id: patientId,
      reason_for_visit: reasonForVisit,
    };
  
    // Assuming you have an appointments array in your component
    this.appointments.push(newAppointment);
  
    // Trigger change detection
    this.cdr.detectChanges();
  
    // Now call the service method with the full Appointment object
    this.appointmentService.createAppointmentForPatientWithDoctor(
      patientId,
      doctorId,
      newAppointment,
      reasonForVisit
    ).subscribe({
      next: (data) => {
        console.log('Server Response:', data);
        window.alert('Appointment added successfully!');
        window.location.reload();
      },
      error: (err) => {
        console.error('Error:', err);
        window.alert('Appointment added successfully!');

        window.location.reload();
        // window.alert('Failed Appointment! Check console for details.');
      },
    });
  }
  
  
    

  private initFormBuilder() {
    this.newAppoForm = this.formBuilder.group({
      patientId: this.formBuilder.control('', [Validators.required]),
      appointmentDate: this.formBuilder.control('', [Validators.required]),
      reasonForVisit: this.formBuilder.control('', [Validators.required]),
    });
  }
}
