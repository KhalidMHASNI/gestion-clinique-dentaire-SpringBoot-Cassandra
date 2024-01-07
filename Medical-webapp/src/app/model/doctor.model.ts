import {Appointment} from "./appointement.model";
import { Patient } from "./patient.model";


export interface Doctor {
  patients(patients: any): unknown;
  doctorId: number;
  name: string;
  specialization: string;
  contactInformation: string;
  appointments: { [key: string]: Appointment };

}

