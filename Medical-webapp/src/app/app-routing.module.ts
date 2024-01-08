import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AboutComponent } from './about/about.component';
import { AddPatientComponent } from './add-patient/add-patient.component';
import { BookingComponent } from './booking/booking.component';

import { ContactComponent } from './contact/contact.component';
import { DentictsComponent } from './denticts/denticts.component';
import { DoctorDetailsComponent } from './doctor-details/doctor-details.component';
import { HomeComponent } from './home/home.component';
import { NotfoundComponent } from './notfound/notfound.component';
import { ServiceDetailsComponent } from './service-details/service-details.component';
import { ServicesComponent } from './services/services.component';
import { TestimonialsComponent } from './testimonials/testimonials.component';
import { VaccineComponent } from './vaccine/vaccine.component';
import { PatientDetailsComponent } from './patient-details/patient-details.component';



const routes: Routes = [
  {path:'', redirectTo:"home", pathMatch:"full"},
  {path:'home' , component:HomeComponent},
  {path:'about' , component:AboutComponent},

  {path:'addpatient' , component:AddPatientComponent},
  {path:'services' , component:ServicesComponent},
  {path:'service-details' , component:ServiceDetailsComponent},
  {path:'doctors' , component:DentictsComponent},
  {path:'doctor-details' , component:DoctorDetailsComponent},
  {path:'patient-details' , component:PatientDetailsComponent},
  {path:'booking' , component:BookingComponent},
  {path:'testimonials' , component:TestimonialsComponent},
  {path:'doctor_manage' , component:VaccineComponent},
  {path:'contact' , component:ContactComponent},
  {path:'**' , component:NotfoundComponent},
  {path:'notfound' , component:NotfoundComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
