// export interface Appointment {
//   appointmentId: number;
//   doctorId: number;
//   appointmentDate: Date | null;
//   patientId: number;
//   reasonForVisit: string | null;
// }

import { MedicalRecord } from "./medicalrecords.model";

// export interface Appointment {
//   appointment_id: number;
//   doctor_id: number;
//   appointment_date: Date;
//   patient_id: number;
//   reason_for_visit: string;
// }

// Assuming your Appointment interface looks like this
export interface Appointment {
  appointment_id: number;
  doctor_id: number;
  appointment_date: string | Date; // Allowing for either string or Date
  patient_id: number;
  reason_for_visit: string;
}


