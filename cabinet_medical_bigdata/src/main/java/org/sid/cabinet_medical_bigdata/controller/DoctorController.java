package org.sid.cabinet_medical_bigdata.controller;

import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.ResultSet;
import com.datastax.oss.driver.api.core.cql.Row;
import com.datastax.oss.driver.api.querybuilder.select.Select;
import org.sid.cabinet_medical_bigdata.entities.Doctor;
import org.sid.cabinet_medical_bigdata.entities.Patient;
import org.sid.cabinet_medical_bigdata.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200")
public class DoctorController {

    @Autowired
    private DoctorRepository doctorsRepository;
    private final CqlSession session;
    public DoctorController(CqlSession session){
        this.session = session;
    }

    @GetMapping("/doctors")
    public List<Doctor> getAllDoctors() {
        return doctorsRepository.findAll();
    }

    @GetMapping("/doctors/{id}")
    public ResponseEntity<Doctor> getDoctorById(@PathVariable("id") Long id) {
        Optional<Doctor> doctorOptional = doctorsRepository.findById(Math.toIntExact(id));
        return doctorOptional.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Autowired
    private CassandraTemplate cassandraTemplate; // CassandraTemplate or any appropriate Cassandra driver

    @PostMapping("/doctors")
    public ResponseEntity<Doctor> createDoctor(@RequestBody Doctor doctor) {
        int lastId = getLastDoctorIdFromDatabase(); // Get the last ID from the database
        int newId = lastId + 1; // Increment the ID for the new Doctor
        doctor.setDoctorId(newId);
        Doctor newDoctor = doctorsRepository.save(doctor);
        return ResponseEntity.status(HttpStatus.CREATED).body(newDoctor);
    }

    private int getLastDoctorIdFromDatabase() {
        ResultSet resultSet = session.execute("SELECT MAX(doctor_id) AS doctor_id FROM doctors");

        Row row = resultSet.one();
        if (row != null) {
            System.out.println(row.getInt("doctor_id"));
            return row.getInt("doctor_id");
        } else {
            return 0;
        }
    }

    @PutMapping("/doctors/{id}")
    public ResponseEntity<Doctor> updateDoctor(@PathVariable("id") Long id, @RequestBody Doctor doctorDetails) {
        Optional<Doctor> doctorOptional = doctorsRepository.findById(Math.toIntExact(id));
        if (doctorOptional.isPresent()) {
            Doctor existingDoctor = doctorOptional.get();

            existingDoctor.setName(doctorDetails.getName());
            existingDoctor.setSpecialization(doctorDetails.getSpecialization());
            existingDoctor.setContactInformation(doctorDetails.getContactInformation());

            Doctor updatedDoctor = doctorsRepository.save(existingDoctor);
            return ResponseEntity.ok(updatedDoctor);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/doctors/{id}")
    public ResponseEntity<Void> deleteDoctor(@PathVariable("id") Long id) {
        Optional<Doctor> doctorOptional = doctorsRepository.findById(Math.toIntExact(id));
        if (doctorOptional.isPresent()) {
            doctorsRepository.deleteById(Math.toIntExact(id));
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
