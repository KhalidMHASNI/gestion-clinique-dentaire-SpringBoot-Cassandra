import { Component , OnInit } from '@angular/core';
import { DoctorServiceService } from '../appah/doctor-service.service';
import { Router } from '@angular/router';
import { FormBuilder } from '@angular/forms';
declare var $: any;

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss']
})


export class NavbarComponent implements OnInit {
  userName:string="";
  constructor(){
    
  }
  ngOnInit(): void {
    throw new Error('Method not implemented.');
  }
  // doctors : any;
  // isFormOpen: boolean = false;
  // constructor(private  doctorservice:DoctorServiceService, private  router : Router, private formBuilder: FormBuilder) { }
  // ngOnInit(): void {
  //   this.doctorservice.getDoctors().subscribe(
  //     { next:(data)=>{
  //         this.doctors = data;
  //       },
  //       error : (err)=>{}
  //     });
  // }

}


