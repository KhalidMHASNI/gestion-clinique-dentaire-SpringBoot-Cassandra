package org.sid.cabinet_medical_bigdata.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.time.LocalDate;
import java.util.Map;

@Table("patients")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Patient {
    @PrimaryKey("patient_id")
    private int patientId;

    @Column("name")
    private String name;

    @Column("date_of_birth")
    private LocalDate dateOfBirth;

    @Column("contact_number")
    private String contactNumber;

    @Column("doctor_id")
    private int doctorId;

    @Column("medical_records")
    private Map<Integer, MedicalRecord> medicalRecords;

    @Column("appointments")
    private Map<Integer, Appointment> appointments;
}
