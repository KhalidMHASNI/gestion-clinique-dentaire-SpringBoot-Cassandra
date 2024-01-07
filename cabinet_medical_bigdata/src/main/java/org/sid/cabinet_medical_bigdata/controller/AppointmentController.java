package org.sid.cabinet_medical_bigdata.controller;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.ResultSet;
import com.datastax.oss.driver.api.core.cql.Row;
import com.datastax.oss.driver.api.core.data.UdtValue;
import org.sid.cabinet_medical_bigdata.entities.Appointment;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200")
public class AppointmentController {

    private final CqlSession session;

    public AppointmentController(CqlSession session) {
        this.session = session;
    }

    @GetMapping("/appointments")
    public List<Appointment> getAllMedicalRecords() {
        List<Appointment> appointments = new ArrayList<>();
        ResultSet rs = session.execute("SELECT patient_id, appointments FROM patients ALLOW FILTERING");

        for (Row row : rs) {

            Map<Integer, UdtValue> appointmentsMap = row.getMap("appointments", Integer.class, UdtValue.class);

            assert appointmentsMap != null;
            for (Map.Entry<Integer, UdtValue> entry : appointmentsMap.entrySet()) {
                UdtValue appointmentUdt = entry.getValue();

                Appointment appointment = new Appointment();
                appointment.setAppointment_id(appointmentUdt.getInt("appointment_id"));
                appointment.setDoctor_id(appointmentUdt.getInt("doctor_id"));
                appointment.setAppointment_date(Timestamp.from(appointmentUdt.get("appointment_date", Instant.class)));
                appointment.setPatient_id(appointmentUdt.getInt("patient_id"));
                appointment.setReason_for_visit(appointmentUdt.getString("reason_for_visit"));

                appointments.add(appointment);
            }
        }

        return appointments;
    }

    @GetMapping("/appointments/doctor/{doctorId}")
    public List<Appointment> getAllAppointmentsByDoctorId(@PathVariable int doctorId) {
        List<Appointment> appointments = new ArrayList<>();
        String cql = String.format("SELECT doctor_id, appointments FROM doctors WHERE doctor_id = %s ALLOW FILTERING",doctorId);
        ResultSet rs = session.execute(cql);

        for (Row row : rs) {

            Map<Integer, UdtValue> appointmentsMap = row.getMap("appointments", Integer.class, UdtValue.class);

            assert appointmentsMap != null;
            for (Map.Entry<Integer, UdtValue> entry : appointmentsMap.entrySet()) {
                UdtValue appointmentUdt = entry.getValue();

                Appointment appointment = new Appointment();
                appointment.setAppointment_id(appointmentUdt.getInt("appointment_id"));
                appointment.setDoctor_id(appointmentUdt.getInt("doctor_id"));
                appointment.setAppointment_date(Timestamp.from(appointmentUdt.get("appointment_date", Instant.class)));
                appointment.setPatient_id(appointmentUdt.getInt("patient_id"));
                appointment.setReason_for_visit(appointmentUdt.getString("reason_for_visit"));

                appointments.add(appointment);
            }
        }

        return appointments;
    }

    @GetMapping("/appointments/patient/{patientId}")
    public List<Appointment> getAllAppointmentsByPatientId(@PathVariable int patientId) {
        List<Appointment> appointments = new ArrayList<>();
        String cql = String.format("SELECT patient_id, appointments FROM patients WHERE patient_id = %s ALLOW FILTERING",patientId);
        ResultSet rs = session.execute(cql);

        for (Row row : rs) {

            Map<Integer, UdtValue> appointmentsMap = row.getMap("appointments", Integer.class, UdtValue.class);

            assert appointmentsMap != null;
            for (Map.Entry<Integer, UdtValue> entry : appointmentsMap.entrySet()) {
                UdtValue appointmentUdt = entry.getValue();

                Appointment appointment = new Appointment();
                appointment.setAppointment_id(appointmentUdt.getInt("appointment_id"));
                appointment.setDoctor_id(appointmentUdt.getInt("doctor_id"));
                appointment.setAppointment_date(Timestamp.from(appointmentUdt.get("appointment_date", Instant.class)));
                appointment.setPatient_id(appointmentUdt.getInt("patient_id"));
                appointment.setReason_for_visit(appointmentUdt.getString("reason_for_visit"));

                appointments.add(appointment);
            }
        }

        return appointments;
    }

    private int getNextAppointmentId(int doctor_id) {
        String cql = String.format("SELECT doctor_id, appointments FROM doctors WHERE doctor_id = %s ALLOW FILTERING",doctor_id);        ResultSet rs = session.execute(cql);
        List<Appointment> appointments = new ArrayList<>();

        for (Row row : rs) {
            Map<Integer, UdtValue> appointmentsMap = row.getMap("appointments", Integer.class, UdtValue.class);

            assert appointmentsMap != null;
            for (Map.Entry<Integer, UdtValue> entry : appointmentsMap.entrySet()) {
                UdtValue appointmentUdt = entry.getValue();

                Appointment appointment = new Appointment();
                appointment.setAppointment_id(appointmentUdt.getInt("appointment_id"));
                // Set other appointment details accordingly
                appointments.add(appointment);
            }
        }
        int maxAppointmentId = 0;

        if (!appointments.isEmpty()) {
            for (Appointment appointment : appointments) {
                int currentAppointmentId = appointment.getAppointment_id();
                if (currentAppointmentId > maxAppointmentId) {
                    maxAppointmentId = currentAppointmentId;
                }
            }
        }

        return maxAppointmentId;
    }


    @PostMapping("appointments/patients/{patientId}/doctors/{doctorId}")
    public ResponseEntity<String> createAppointmentForPatientWithDoctor(
            @PathVariable int patientId,
            @PathVariable int doctorId,
            @RequestBody Appointment appointmentRequest) {
        try {
            int next_appo_id = 0;
            next_appo_id = getNextAppointmentId(doctorId)+1;
            String cql = String.format(
                    "UPDATE patients SET appointments =  appointments + {%d: {appointment_id: %d, doctor_id: %d, appointment_date: '%s', patient_id:%d,  reason_for_visit: '%s'}} WHERE patient_id = %d",
                    next_appo_id,
                    next_appo_id,
                    doctorId,
                    appointmentRequest.getAppointment_date(),
                    patientId,
                    appointmentRequest.getReason_for_visit(),
                    patientId
            );

            System.out.println(cql);
            session.execute(cql);
            cql = String.format(
                    "UPDATE doctors SET appointments =  appointments + {%d: {appointment_id: %d, doctor_id: %d, appointment_date: '%s', patient_id:%d,  reason_for_visit: '%s'}} WHERE doctor_id = %d",
                    next_appo_id,
                    next_appo_id,
                    doctorId,
                    appointmentRequest.getAppointment_date(),
                    patientId,
                    appointmentRequest.getReason_for_visit(),
                    doctorId
            );
            System.out.println(cql);
            session.execute(cql);

            return ResponseEntity.status(HttpStatus.CREATED).body("Medical record added successfully for patient with ID: " + patientId);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error adding medical record: " + e.getMessage());
        }
    }


    @DeleteMapping("appointments/{appointmentId}/patients/{patientId}")
    public ResponseEntity<String> deleteAppointmentForPatient(
            @PathVariable int patientId,
            @PathVariable int appointmentId) {

        try {
            String deleteAppointmentQuery = String.format(
                    "UPDATE patients " +
                            "SET appointments = appointments - {%d} " +
                            "WHERE patient_id = %d;",
                    appointmentId, patientId);

            ResultSet deletionResult = session.execute(deleteAppointmentQuery);

            if (!deletionResult.wasApplied()) {
                return new ResponseEntity<>("Failed to delete the appointment for the patient", HttpStatus.INTERNAL_SERVER_ERROR);
            }

            return new ResponseEntity<>("Appointment deleted successfully for the patient", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Failed to delete appointment", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("appointments/{appointmentId}/doctors/{doctorId}")
    public ResponseEntity<String> deleteAppointmentForDoctor(
            @PathVariable int doctorId,
            @PathVariable int appointmentId) {

        try {
            String deleteAppointmentQuery = String.format(
                    "UPDATE doctors " +
                            "SET appointments = appointments - {%d} " +
                            "WHERE doctor_id = %d;",
                    appointmentId, doctorId);

            ResultSet deletionResult = session.execute(deleteAppointmentQuery);

            if (!deletionResult.wasApplied()) {
                return new ResponseEntity<>("Failed to delete the appointment for the patient", HttpStatus.INTERNAL_SERVER_ERROR);
            }

            return new ResponseEntity<>("Appointment deleted successfully for the patient", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Failed to delete appointment", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/appointments/{appointmentId}/patient/{patientId}")
    public ResponseEntity<String> updateAppointmentForPatient(@PathVariable int patientId, @RequestBody Appointment appointment, @PathVariable int appointmentId) {
        try {
            String appointmentUpdateQuery = String.format(
                    "UPDATE patients " +
                            "SET appointments[%d] = { " +
                            "    appointment_id: %d, " +
                            "    doctor_id: %d, " +
                            "    appointment_date: '%s', " +
                            "    patient_id: %d, " +
                            "    reason_for_visit: '%s' " +
                            "} " +
                            "WHERE patient_id = %d;",
                    appointmentId,
                    appointmentId,
                    appointment.getDoctor_id(),
                    appointment.getAppointment_date(),
                    patientId,
                    appointment.getReason_for_visit(),
                    patientId
            );

            System.out.println(appointmentUpdateQuery);
            session.execute(appointmentUpdateQuery);

            return ResponseEntity.status(HttpStatus.OK).body("Medical record updated successfully for patient with ID: " + patientId);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating medical record: " + e.getMessage());
        }
    }

    @PutMapping("/appointments/{appointmentId}/doctor/{doctorId}")
    public ResponseEntity<String> updateAppointmentForDoctor(@PathVariable int doctorId, @RequestBody Appointment appointment, @PathVariable int appointmentId) {
        try {
            String appointmentUpdateQuery = String.format(
                    "UPDATE doctors " +
                            "SET appointments[%d] = { " +
                            "    appointment_id: %d, " +
                            "    doctor_id: %d, " +
                            "    appointment_date: '%s', " +
                            "    patient_id: %d, " +
                            "    reason_for_visit: '%s' " +
                            "} " +
                            "WHERE doctor_id = %d;",
                    doctorId,
                    appointmentId,
                    appointment.getDoctor_id(),
                    appointment.getAppointment_date(),
                    appointment.getAppointment_id(),
                    appointment.getReason_for_visit(),
                    doctorId
            );

            System.out.println(appointmentUpdateQuery);
            session.execute(appointmentUpdateQuery);

            return ResponseEntity.status(HttpStatus.OK).body("Medical record updated successfully for patient with ID: " + doctorId);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating medical record: " + e.getMessage());
        }
    }


}
/*
*
    private Appointment createAppointmentFromUdt(int doctorId, Timestamp appointmentTimestamp, UdtValue udtValue) {
        return new Appointment(
                udtValue.getInt("appointment_id"),
                udtValue.getInt("doctor_id"),
                Timestamp.from(Objects.requireNonNull(udtValue.get("appointment_date", Instant.class))),
                udtValue.getInt("patient_id"),
                udtValue.getString("reason_for_visit")
        );
    }


    @GetMapping("/appointments")
    public List<Appointment> getAllDoctorAppointments() {
        try {
            return session.execute("SELECT patient_id, appointments FROM patients ALLOW FILTERING").all().stream()
                    .map(row -> {
                        int doctorId = row.getInt("patient_id");
                        Map<Instant, UdtValue> appointmentsMap = row.getMap("appointments", Instant.class, UdtValue.class);
                        return appointmentsMap.entrySet().stream()
                                .map(entry -> createAppointmentFromUdt(doctorId, Timestamp.from(entry.getKey()), entry.getValue()))
                                .collect(Collectors.toList());
                    })
                    .flatMap(List::stream)
                    .collect(Collectors.toList());

        } catch (Exception e) {
            e.printStackTrace();
            // Handle the exception as needed
            return new ArrayList<>(); // Return an empty list in case of an exception
        }
    }

    @GetMapping("/appointments/doctor/{doctorId}")
    public List<Appointment> getAllAppointmentsByDoctorId(@PathVariable int doctorId) {
        try {
            return session.execute("SELECT doctor_id, appointments FROM doctors WHERE doctor_id = ?", doctorId)
                    .all().stream()
                    .map(row -> {
                        Map<Instant, UdtValue> appointmentsMap = row.getMap("appointments", Instant.class, UdtValue.class);
                        return appointmentsMap.entrySet().stream()
                                .map(entry -> createAppointmentFromUdt(doctorId, Timestamp.from(entry.getKey()), entry.getValue()))
                                .collect(Collectors.toList());
                    })
                    .flatMap(List::stream)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
            // Handle the exception as needed
            return new ArrayList<>(); // Return an empty list in case of an exception
        }
    }

    @GetMapping("/appointments/patient/{patientId}")
    public List<Appointment> getAllAppointmentsByPatientId(@PathVariable int patientId) {
        try {
            return session.execute("SELECT patient_id, appointments FROM patients WHERE patient_id = ?", patientId)
                    .all().stream()
                    .map(row -> {
                        Map<Instant, UdtValue> appointmentsMap = row.getMap("appointments", Instant.class, UdtValue.class);
                        return appointmentsMap.entrySet().stream()
                                .map(entry -> createAppointmentFromUdt(patientId, Timestamp.from(entry.getKey()), entry.getValue()))
                                .collect(Collectors.toList());
                    })
                    .flatMap(List::stream)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
            // Handle the exception as needed
            return new ArrayList<>(); // Return an empty list in case of an exception
        }
    }

    @PostMapping("appointments/patients/{patientId}/doctors/{doctorId}")
    public ResponseEntity<String> createAppointmentForPatientWithDoctor(
            @PathVariable int patientId,
            @PathVariable int doctorId,
            @RequestBody Appointment appointmentRequest) {

        try {
            Timestamp appointmentDate = appointmentRequest.getAppointment_date();
            String reasonForVisit = appointmentRequest.getReason_for_visit();
            int appointmentId = appointmentRequest.getAppointment_id();

            /*System.out.println(appointmentRequest);
            /*System.out.println("Creating appointment for Patient ID: " + patientId +
                    " with Doctor ID: " + doctorId +
                    " - Appointment ID: " + appointmentId +
                    ", Date: " + appointmentDate +
                    ", Reason: " + reasonForVisit);*/

    /*String updateQuery = String.format(
            "UPDATE patients SET appointments = appointments + {'%s': {appointment_id: %d, doctor_id: %d, appointment_date: '%s', patient_id: %d, reason_for_visit: '%s'}} WHERE patient_id = %d;",
            appointmentDate, appointmentId, doctorId, appointmentDate, patientId, reasonForVisit, patientId);

            session.execute(updateQuery);

                    updateQuery = String.format(
                    "UPDATE doctors SET appointments = appointments + {'%s': {appointment_id: %d, doctor_id: %d, appointment_date: '%s', patient_id: %d, reason_for_visit: '%s'}} WHERE doctor_id = %d;",
                    appointmentDate, appointmentId, doctorId, appointmentDate, patientId, reasonForVisit, doctorId);

                    session.execute(updateQuery);
                    return new ResponseEntity<>("Appointment created successfully", HttpStatus.CREATED);
        } catch (Exception e) {
        e.printStackTrace();
        return new ResponseEntity<>("Failed to create appointment", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        }

@PutMapping("appointments/patients/{patientId}/doctors/{doctorId}")
public ResponseEntity<String> updateAppointmentForPatientWithDoctor(
@PathVariable int patientId,
@PathVariable int doctorId,
@RequestBody Appointment appointmentRequest) {

        try {
        Timestamp appointmentDate = appointmentRequest.getAppointment_date();
        String reasonForVisit = appointmentRequest.getReason_for_visit();
        int appointmentId = appointmentRequest.getAppointment_id();

        String updateQuery = String.format(
        "UPDATE patients SET appointments['%s'] = {appointment_id: %d, doctor_id: %d, appointment_date: '%s', patient_id: %d, reason_for_visit: '%s'} WHERE patient_id = %d;",
        appointmentDate, appointmentId, doctorId, appointmentDate, patientId, reasonForVisit, patientId);

        session.execute(updateQuery);

        updateQuery = String.format(
        "UPDATE doctors SET appointments['%s'] = {appointment_id: %d, doctor_id: %d, appointment_date: '%s', patient_id: %d, reason_for_visit: '%s'} WHERE doctor_id = %d;",
        appointmentDate, appointmentId, doctorId, appointmentDate, patientId, reasonForVisit, doctorId);

        session.execute(updateQuery);

        return new ResponseEntity<>("Appointment updated successfully", HttpStatus.OK);
        } catch (Exception e) {
        e.printStackTrace();
        return new ResponseEntity<>("Failed to update appointment", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        }


@DeleteMapping("appointments/patients/{patientId}/{appointmentId}")
public ResponseEntity<String> deleteAppointmentForPatient(
@PathVariable int patientId,
@PathVariable int appointmentId) {

        try {
        // Construct the delete query for the appointment of the patient
        String deleteAppointmentQuery = String.format(
        "DELETE appointments['%d'] FROM patients WHERE patient_id = %d IF appointments['%d'].appointment_id = %d;",
        appointmentId, patientId, appointmentId, appointmentId);

        // Execute the delete query
        ResultSet deletionResult = session.execute(deleteAppointmentQuery);

        // Check the deletion status
        if (!deletionResult.wasApplied()) {
        return new ResponseEntity<>("Failed to delete the appointment for the patient", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>("Appointment deleted successfully for the patient", HttpStatus.OK);
        } catch (Exception e) {
        e.printStackTrace();
        return new ResponseEntity<>("Failed to delete appointment", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        }

        */