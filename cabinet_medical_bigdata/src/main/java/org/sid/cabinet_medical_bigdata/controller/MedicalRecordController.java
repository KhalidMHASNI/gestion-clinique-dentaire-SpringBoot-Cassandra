package org.sid.cabinet_medical_bigdata.controller;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.BoundStatement;
import com.datastax.oss.driver.api.core.cql.PreparedStatement;
import com.datastax.oss.driver.api.core.cql.ResultSet;
import com.datastax.oss.driver.api.core.cql.Row;
import com.datastax.oss.driver.api.core.data.UdtValue;
import com.datastax.oss.driver.api.core.type.UserDefinedType;
import org.sid.cabinet_medical_bigdata.entities.MedicalRecord;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class MedicalRecordController {

    private final CqlSession session;

    public MedicalRecordController(CqlSession session) {
        this.session = session;
    }

    @GetMapping("/medical-records")
    public List<MedicalRecord> getAllMedicalRecords() {
        List<MedicalRecord> medicalRecords = new ArrayList<>();
        ResultSet rs = session.execute("SELECT patient_id, medical_records FROM patients ALLOW FILTERING");

        for (Row row : rs) {
            int patientId = row.getInt("patient_id");
            Map<Integer, UdtValue> medicalRecordsMap = row.getMap("medical_records", Integer.class, UdtValue.class);

            for (Map.Entry<Integer, UdtValue> entry : medicalRecordsMap.entrySet()) {
                UdtValue medicalRecordUdt = entry.getValue();

                MedicalRecord medicalRecord = new MedicalRecord();
                medicalRecord.setMedical_record_id(medicalRecordUdt.getInt("medical_record_id"));
                medicalRecord.setDiagnosis(medicalRecordUdt.getString("diagnosis"));
                medicalRecord.setPrescriptions(medicalRecordUdt.getList("prescriptions", String.class));
                medicalRecord.setTreatment_history(medicalRecordUdt.getString("treatment_history"));

                medicalRecords.add(medicalRecord);
            }
        }

        return medicalRecords;
    }

    @GetMapping("/medical-records/{patientId}")
    public List<MedicalRecord> getMedicalRecordsByPatientId(@PathVariable int patientId) {
        List<MedicalRecord> medicalRecords = new ArrayList<>();
        PreparedStatement preparedStatement = session.prepare("SELECT medical_records FROM patients WHERE patient_id = ?");
        BoundStatement boundStatement = preparedStatement.bind(patientId);

        ResultSet rs = session.execute(boundStatement);

        for (Row row : rs) {
            Map<Integer, UdtValue> medicalRecordsMap = row.getMap("medical_records", Integer.class, UdtValue.class);

            for (Map.Entry<Integer, UdtValue> entry : medicalRecordsMap.entrySet()) {
                UdtValue medicalRecordUdt = entry.getValue();

                MedicalRecord medicalRecord = new MedicalRecord();
                medicalRecord.setMedical_record_id(medicalRecordUdt.getInt("medical_record_id"));
                medicalRecord.setDiagnosis(medicalRecordUdt.getString("diagnosis"));
                medicalRecord.setPrescriptions(medicalRecordUdt.getList("prescriptions", String.class));
                medicalRecord.setTreatment_history(medicalRecordUdt.getString("treatment_history"));

                medicalRecords.add(medicalRecord);
            }
        }

        return medicalRecords;
    }

    @PostMapping("/medical-records/{patientId}")
    public ResponseEntity<String> addMedicalRecordForPatient(@PathVariable int patientId, @RequestBody MedicalRecord medicalRecord) {
        try {
            UserDefinedType medicalRecordType = session.getMetadata().getKeyspace("cabinet_medical").flatMap(ks -> ks.getUserDefinedType("medical_record")).orElseThrow();

            UdtValue medicalRecordUdt = medicalRecordType.newValue()
                    .setInt("medical_record_id", medicalRecord.getMedical_record_id())
                    .setString("diagnosis", medicalRecord.getDiagnosis())
                    .setString("treatment_history", medicalRecord.getTreatment_history());


            List<String> prescriptions = medicalRecord.getPrescriptions();
            String prescriptionsString = "";
            if (prescriptions != null) {
                prescriptionsString = prescriptions.stream()
                        .map(p -> "'" + p + "'")
                        .collect(Collectors.joining(","));
            }

            System.out.println(prescriptions);
            System.out.println(prescriptionsString);
            String cql = String.format(
                    "UPDATE patients SET medical_records = medical_records + {%d: {medical_record_id: %d, diagnosis: '%s', prescriptions: [%s], treatment_history: '%s'}} WHERE patient_id = %d",
                    medicalRecord.getMedical_record_id(),
                    medicalRecord.getMedical_record_id(),
                    medicalRecord.getDiagnosis(),
                    prescriptionsString,
                    medicalRecord.getTreatment_history(),
                    patientId
            );

            System.out.println(cql);
            //PreparedStatement preparedStatement = session.prepare(cql);
            //BoundStatement boundStatement = preparedStatement.bind(patientId);
            session.execute(cql);

            return ResponseEntity.status(HttpStatus.CREATED).body("Medical record added successfully for patient with ID: " + patientId);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error adding medical record: " + e.getMessage());
        }
    }

    @DeleteMapping("/medical-records/{patientId}/{medicalRecordId}")
    public ResponseEntity<String> deleteMedicalRecordForPatient(@PathVariable int patientId, @PathVariable int medicalRecordId) {
        try {
            ResultSet rs = session.execute("SELECT medical_records FROM patients WHERE patient_id = ?", patientId);
            Row row = rs.one();

            if (row != null) {
                Map<Integer, UdtValue> medicalRecords = row.getMap("medical_records", Integer.class, UdtValue.class);

                if (medicalRecords.containsKey(medicalRecordId)) {
                    medicalRecords.remove(medicalRecordId);

                    PreparedStatement preparedStatement = session.prepare("UPDATE patients SET medical_records = ? WHERE patient_id = ?");
                    BoundStatement boundStatement = preparedStatement.bind(medicalRecords, patientId);
                    session.execute(boundStatement);

                    return ResponseEntity.status(HttpStatus.OK).body("Medical record deleted successfully for patient with ID: " + patientId);
                } else {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Medical record with ID " + medicalRecordId + " not found for patient with ID: " + patientId);
                }
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Patient with ID: " + patientId + " not found");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting medical record: " + e.getMessage());
        }
    }

    @PutMapping("/medical-records/patient/{patientId}")
    public ResponseEntity<String> updateMedicalRecordForPatient(@PathVariable int patientId, @RequestBody MedicalRecord medicalRecord) {
        try {
            UserDefinedType medicalRecordType = session.getMetadata().getKeyspace("cabinet_medical").flatMap(ks -> ks.getUserDefinedType("medical_record")).orElseThrow();

            UdtValue medicalRecordUdt = medicalRecordType.newValue()
                    .setInt("medical_record_id", medicalRecord.getMedical_record_id())
                    .setString("diagnosis", medicalRecord.getDiagnosis())
                    .setString("treatment_history", medicalRecord.getTreatment_history());
            List<String> prescriptions = medicalRecord.getPrescriptions();
            String prescriptionsString = "";
            if (prescriptions != null) {
                prescriptionsString = prescriptions.stream()
                        .map(p -> "'" + p + "'")
                        .collect(Collectors.joining(","));
            }

            String cql = String.format(
                            "UPDATE patients SET medical_records[%d] = " +
                            "{medical_record_id: %d, diagnosis: '%s', prescriptions: [%s], treatment_history: '%s'} WHERE patient_id = %d;",
                    medicalRecord.getMedical_record_id(),
                    medicalRecord.getMedical_record_id(),
                    medicalRecord.getDiagnosis(),
                    prescriptionsString,
                    medicalRecord.getTreatment_history(),
                    patientId
            );

            System.out.println(cql);
            session.execute(cql);

            return ResponseEntity.status(HttpStatus.OK).body("Medical record updated successfully for patient with ID: " + patientId);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating medical record: " + e.getMessage());
        }
    }

}

