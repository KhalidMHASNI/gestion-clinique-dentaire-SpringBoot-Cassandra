import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError, map } from 'rxjs/operators';
import { Appointment } from '../model/appointement.model';

@Injectable({
  providedIn: 'root'
})
export class AppointmentServiceService {

  apiUrl = 'http://localhost:8083/api';

  constructor(private http: HttpClient) { }

  getAllAppointments(): Observable<Appointment[]> {
    return this.http.get<Appointment[]>(`${this.apiUrl}`);
  }

  getAppointmentsByDoctorId(doctorId: number): Observable<Appointment[]> {
    return this.http.get<Appointment[]>(`${this.apiUrl}/doctor/${doctorId}`);
  }

  getAppointmentsByPatientId(patientId: number): Observable<Appointment[]> {
    return this.http.get<Appointment[]>(`${this.apiUrl}/patient/${patientId}`);
  }

  createAppointmentForPatientWithDoctor(
    patientId: number,
    doctorId: number,
    appointment: Appointment,
    reasonForVisit: string
  ): Observable<Appointment> {
    const url = `${this.apiUrl}/appointments/patients/${patientId}/doctors/${doctorId}`;
    const body = {
      appointment_date: appointment.appointment_date,
      reason_for_visit: reasonForVisit // Use the provided reasonForVisit parameter
    };
  
    return this.http.post(url, body, { observe: 'response' }).pipe(
      map(response => {
        // Assuming the response body contains the created appointment
        return response.body as Appointment;
      }),
      catchError(error => {
        console.error('Error creating appointment:', error);
        return throwError(error);
      })
    );
  }
  


    
    
    


    deleteAppointmentForPatient(patientId: number, appointmentId: number): Observable<string> {
      return this.http.delete<string>(`${this.apiUrl}/${appointmentId}/patients/${patientId}`);
    }

    deleteAppointmentForDoctor(doctorId: number, appointmentId: number): Observable<string> {
      return this.http.delete<string>(`${this.apiUrl}/${appointmentId}/doctors/${doctorId}`);
    }

    updateAppointmentForPatientWithDoctor(
      patientId: number,
      doctorId: number,
      appointment: any
    ): Observable<string> {
      return this.http.put<string>(
        `${this.apiUrl}/patients/${patientId}/doctors/${doctorId}`,
        appointment
      );
    }

    private handleError(error: any) {
      console.error('An error occurred', error);
      return throwError(error.message || 'Something went wrong');
    }
  }
