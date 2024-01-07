import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HeaderComponent } from './header/header.component';
import { NavbarComponent } from './navbar/navbar.component';
import { SliderComponent } from './slider/slider.component';
import { HomeComponent } from './home/home.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { CarouselModule } from 'ngx-owl-carousel-o';
import { AboutComponent } from './about/about.component';
import { ServicesComponent } from './services/services.component';
import { DentictsComponent } from './denticts/denticts.component';
import { DoctorDetailsComponent } from './doctor-details/doctor-details.component';
import { BookingComponent } from './booking/booking.component';
import { TestimonialsComponent } from './testimonials/testimonials.component';
import { PrivacyComponent } from './privacy/privacy.component';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { NotfoundComponent } from './notfound/notfound.component';
import { ContactComponent } from './contact/contact.component';
import { ServiceDetailsComponent } from './service-details/service-details.component';
import { CallUsComponent } from './Components/call-us/call-us.component';
import { AboutPartComponent } from './Components/about-part/about-part.component';
import { FooterComponent } from './footer/footer.component';
import { VaccineComponent } from './vaccine/vaccine.component';
import { DentictsPartComponent } from './Components/denticts-part/denticts-part.component';
import { ScrollComponent } from './Components/scroll/scroll.component';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { ReactiveFormsModule } from '@angular/forms';
import { AddPatientComponent } from './add-patient/add-patient.component';
import { PatientDetailsComponent } from './patient-details/patient-details.component';


@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    NavbarComponent,
    SliderComponent,
    AddPatientComponent,
    HomeComponent,
    AboutComponent,
    ServicesComponent,
    DentictsComponent,
    DoctorDetailsComponent,
    BookingComponent,
    TestimonialsComponent,
    PrivacyComponent,
    LoginComponent,
    RegisterComponent,
    NotfoundComponent,
    ContactComponent,
    ServiceDetailsComponent,
    CallUsComponent,
    AboutPartComponent,
    FooterComponent,
    VaccineComponent,
    DentictsPartComponent,
    ScrollComponent,
    AddPatientComponent,
    PatientDetailsComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    CarouselModule,
    HttpClientModule,
    ReactiveFormsModule,
    FormsModule
  ],
  providers: [
    
  ],
  bootstrap: [AppComponent]

})
export class AppModule { }


