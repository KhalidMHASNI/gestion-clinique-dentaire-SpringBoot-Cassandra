package org.sid.cabinet_medical_bigdata.controller;


import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.ResultSet;
import com.datastax.oss.driver.api.core.cql.Row;
import com.datastax.oss.driver.api.core.data.UdtValue;
import org.sid.cabinet_medical_bigdata.entities.Doctor;
import org.sid.cabinet_medical_bigdata.entities.Patient;
import org.sid.cabinet_medical_bigdata.entities.MedicalRecord;
import org.sid.cabinet_medical_bigdata.repository.DoctorRepository;
import org.sid.cabinet_medical_bigdata.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200")
public class PatientController {
    @Autowired
    private PatientRepository patientsRepository;
    private final CqlSession session;

    public PatientController(CqlSession session){
        this.session  = session;
    }
    @GetMapping("/patients")
    public List<Patient> getAllPatients() {
        return patientsRepository.findAll();
    }

    @GetMapping("/patients/{id}")
    public ResponseEntity<Patient> getPatientById(@PathVariable("id") Long id) {
        Optional<Patient> patientOptional = patientsRepository.findById(Math.toIntExact(id));
        return patientOptional.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    private int getLastpatientIdFromDatabase() {
        ResultSet resultSet = session.execute("SELECT MAX(patient_id) AS patient_id FROM patients");

        Row row = resultSet.one();
        if (row != null) {
            System.out.println(row.getInt("patient_id"));
            return row.getInt("patient_id");
        } else {
            return 0;
        }
    }

    @PostMapping("/patients")
    public ResponseEntity<Patient> createPatient(@RequestBody Patient patient) {
        int lastId = getLastpatientIdFromDatabase();
        int newId = lastId + 1;
        System.out.print("last patient_id :");
        System.out.println(newId);
        patient.setPatientId(newId);
        Patient newPatient = patientsRepository.save(patient);
        return ResponseEntity.status(HttpStatus.CREATED).body(newPatient);
    }


    @PutMapping("/patients/{id}")
    public ResponseEntity<Patient> updatePatient(@PathVariable("id") Long id, @RequestBody Patient patientDetails) {
        Optional<Patient> patientOptional = patientsRepository.findById(Math.toIntExact(id));
        if (patientOptional.isPresent()) {
            Patient existingPatient = patientOptional.get();
            existingPatient.setName(patientDetails.getName());
            existingPatient.setDateOfBirth(patientDetails.getDateOfBirth());
            existingPatient.setContactNumber(patientDetails.getContactNumber());
            Patient updatedPatient = patientsRepository.save(existingPatient);
            return ResponseEntity.ok(updatedPatient);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/patients/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable("id") Long id) {
        Optional<Patient> patientOptional = patientsRepository.findById(Math.toIntExact(id));
        if (patientOptional.isPresent()) {
            patientsRepository.deleteById(Math.toIntExact(id));
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }



}
