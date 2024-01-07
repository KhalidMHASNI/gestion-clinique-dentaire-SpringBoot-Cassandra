import { Injectable } from '@angular/core';
import { HttpClient } from "@angular/common/http";
import { Observable, catchError, tap, throwError } from "rxjs";
import { Doctor } from "../model/doctor.model";

@Injectable({
  providedIn: 'root'
})
export class DoctorServiceService {

  constructor(private http: HttpClient) { }

  public getDoctors(): Observable<Doctor[]> {
    return this.http.get<Doctor[]>("http://localhost:8083/api/doctors").pipe(
      tap(data => console.log('Doctors:', data)),
      catchError(error => {
        console.error('Error fetching doctors:', error);
        return throwError(error);
      })
    );
  }

  public getDoctorById(doctorId: any): Observable<Doctor> {
    return this.http.get<Doctor>("http://localhost:8083/api/doctors/" + doctorId);
  }
  
  updateDoctor(id: any, updatedDoctorData: any): Observable<any> {
    return this.http.put(`http://localhost:8083/api/doctors/${id}`, updatedDoctorData);
  }


  saveDoctor(doctor: Doctor): Observable<any> {
    return this.http.post("http://localhost:8083/api/doctors", doctor);
  }

  deleteDoctor(doctorId: any): Observable<any> {
    return this.http.delete("http://localhost:8083/api/doctors/" + doctorId);
  }
  
  
}
