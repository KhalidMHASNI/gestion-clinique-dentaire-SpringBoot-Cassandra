package org.sid.cabinet_medical_bigdata.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
//@Entity
public class Appointment {
    @Id
    private int appointment_id;
    private int doctor_id;
    private Timestamp appointment_date;
    private int patient_id;
    private String reason_for_visit;

}
