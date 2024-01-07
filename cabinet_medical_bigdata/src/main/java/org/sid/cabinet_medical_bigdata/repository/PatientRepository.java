package org.sid.cabinet_medical_bigdata.repository;

import org.sid.cabinet_medical_bigdata.entities.Patient;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepository extends CassandraRepository<Patient, Integer> {

}