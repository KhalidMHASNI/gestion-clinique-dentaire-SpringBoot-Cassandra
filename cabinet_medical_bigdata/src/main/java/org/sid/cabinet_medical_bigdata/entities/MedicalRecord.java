package org.sid.cabinet_medical_bigdata.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MedicalRecord {
    private int medical_record_id;
    private String diagnosis;
    private List<String> prescriptions;
    private String treatment_history;
}
