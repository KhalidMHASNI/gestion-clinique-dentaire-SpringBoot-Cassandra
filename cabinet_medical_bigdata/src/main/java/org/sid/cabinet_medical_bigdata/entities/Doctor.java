package org.sid.cabinet_medical_bigdata.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.Map;

@Table("doctors")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Doctor {
    @PrimaryKey("doctor_id")
    private int doctorId;

    @Column("name")
    private String name;

    @Column("specialization")
    private String specialization;

    @Column("contact_information")
    private String contactInformation;

    @Column("appointments")
    private Map<Integer, Appointment> appointments;
}
