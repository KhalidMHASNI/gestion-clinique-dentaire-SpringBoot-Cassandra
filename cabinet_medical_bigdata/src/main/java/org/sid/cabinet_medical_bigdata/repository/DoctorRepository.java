package org.sid.cabinet_medical_bigdata.repository;

import org.sid.cabinet_medical_bigdata.entities.Doctor;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorRepository extends CassandraRepository<Doctor, Integer> {

}