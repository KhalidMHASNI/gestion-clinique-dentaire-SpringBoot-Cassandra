package org.sid.cabinet_medical_bigdata;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(CassandraConfig.class)
public class CabinetMedicalBigdataApplication {

    public static void main(String[] args) {
        SpringApplication.run(CabinetMedicalBigdataApplication.class, args);
    }

}
