import { Appointment } from "./appointement.model";
import { MedicalRecord } from "./medicalrecords.model";

// export interface Patient {
//   patientId: number;
//   name: string;
//   dateOfBirth: Date | null;
//   contactNumber: string;
//   doctorId: number;
//   medicalRecords: { [key: number]: MedicalRecord };
//   appointments: { [key: number]: Appointment };
// }
export interface Patient {
  patientId: number;
  name: string;
  dateOfBirth: string;
  contactNumber: string;
  doctorId: number;
  doctorName?: string; // Add this line to include doctorName property
  medicalRecords: { [key: string]: MedicalRecord };
  appointments: { [key: string]: Appointment };
}