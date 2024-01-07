import { Injectable } from '@angular/core';
import { HttpClient } from "@angular/common/http";
import { Observable, catchError, tap, throwError } from "rxjs";
import { Patient } from "../model/patient.model";
import { Doctor } from '../model/doctor.model';

@Injectable({
  providedIn: 'root'
})
export class PatientServiceService {
  apiUrl: any;

  constructor(private http: HttpClient) { }

  public getPatients(): Observable<Patient[]> {
    return this.http.get<Patient[]>("http://localhost:8083/api/patients").pipe(
      tap(data => console.log('Patients:', data)),
      catchError(error => {
        console.error('Error fetching patients:', error);
        return throwError(error);
      })
    );
  }

  getPatientIdByName(patientName: string): Observable<number> {
    return this.http.get<number>(`http://localhost:8083/api/patients/byName/${patientName}`);
  }
  
  getDoctors(): Observable<Doctor[]> {
    return this.http.get<Doctor[]>(`${this.apiUrl}/doctors`);
  }


  public getPatientById(patientId: any): Observable<Patient> {
    return this.http.get<Patient>("http://localhost:8083/api/patients/" + patientId);
  }

  savePatient(patient: Patient): Observable<any> {
    return this.http.post("http://localhost:8083/api/patients", patient);
  }

  deletePatient(patientId: any): Observable<any> {
    return this.http.delete("http://localhost:8083/api/patients/" + patientId);
  }
}
