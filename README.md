# gestion-clinique-dentaire-SpringBoot-Cassandra

## 1. Setting up Cassandra (3.11.10) on Windows(10 and 11) :

This version of cassandra only works on Java8 and Python2, so let's see how to set it up:
 
1. Download Java : [![Java8](https://img.shields.io/badge/Java-jdk8-007396.svg)](https://adoptium.net/fr/temurin/archive/?version=8)
2. Download Python : [![Python2](https://img.shields.io/badge/Python-3776AB?style=for-the-badge&logo=python&logoColor=white)](https://www.python.org/ftp/python/2.0/BeOpen-Python-2.0.exe)


After downloading and installing these versions, you'll have to make sure they're a Path variables : 

![image](https://github.com/KhalidMHASNI/gestion-clinique-dentaire-SpringBoot-Cassandra/assets/82038554/7875ef26-a08b-4293-ae97-78337db652fe)

(NB: If you already have Python3 and you don't want to mess your work libraris up with Python2 just rename the python.exe application in the Bin directory to python2.exec, so it will look like this :)
![image](https://github.com/KhalidMHASNI/gestion-clinique-dentaire-SpringBoot-Cassandra/assets/82038554/60c7eb6d-9e3d-49e7-acd3-90c2f9bfefe1)

Now you can download **Cassandra (3.11.10)** (this specific version worked for me) from this link : https://archive.apache.org/dist/cassandra/3.11.10/

After that You can run **Cassandra** on your Windows machine succesfully, by following these steps :
1. Go to the directory of Cassandra (extracted) and go to the Bin (apache-cassandra-3.11.10\bin\) directory then run these commands using **CMD** :<br>
  ```C:\Cassandrat\apache-cassandra-3.11.10\bin>cassandra```<br>
and then in another terminal, run :<br>
  ```C:\Cassandrat\apache-cassandra-3.11.10\bin>python2 -m cqlsh```<br>
![image](https://github.com/KhalidMHASNI/gestion-clinique-dentaire-SpringBoot-Cassandra/assets/82038554/24f4f4e8-a4c6-45ea-8098-5e75f880817d)


**-> Cassandra (3.11.10) is now succefully setup in you Windows machine**

## 2. Setting up SpringBoot and linking it with Cassandra :

### a- Setting up the keyspace and tables of the project :

Firstly, We're going to create the Keyspace we're going to work with named **cabinet_medical**, that has 2 types and 2 tables :

  * medical_record Type
  * appointment Type
  * doctors Table
  * patients Table

![image](https://github.com/KhalidMHASNI/gestion-clinique-dentaire-SpringBoot-Cassandra/assets/82038554/bb7700fe-3005-4721-9e5d-546de41712d3)
    
For more details check **cassDB_Queries.txt** that has more information about the types and tables as well as some isertions for testing

### b- Linking the springboot application with the cassandra keyspace :
Firstly, we're going to create a springboot application in **https://start.spring.io/** with these caracteristics :
* Project Name: cabinet_medical_bigdata
* Version: 0.0.1-SNAPSHOT
* Description: A Spring Boot project for a medical cabinet handling big data.
* Java Version: 17
* Dependencies:
    * Spring Boot 3.2.1
    * Spring Boot Starter
    * Spring Boot Starter Web
    * Spring Boot Starter Test
    * Spring Web 6.1.2
    * Spring Data Cassandra 4.2.1
    * Spring Data REST Core 4.2.1
    * Spring Data REST WebMVC 4.2.1
    * Lombok 1.18.30
    * Jakarta Persistence API 2.2.3 & 3.1.0
    * Eclipse Persistence Core 4.0.0
    * DataStax Java Driver Core 4.17.0 & Query Builder 4.17.0
    * Cassandra Driver Core 3.4.0

 After that we're going to add a configuration file (CassandraConfig.java), this class serves as a Spring configuration class responsible for setting up necessary configurations and beans required for integrating Cassandra with the Spring application. 

![image](https://github.com/KhalidMHASNI/gestion-clinique-dentaire-SpringBoot-Cassandra/assets/82038554/998662ce-e6c4-4bc4-a02b-d17ae2682b46)

After this we're going to make the architecture like so : 

    ├───main
    │   ├───java
    │   │   └───org
    │   │       └───sid
    │   │           └───cabinet_medical_bigdata
    │   │               │   CabinetMedicalBigdataApplication
    │   │               │   
    │   │               ├───controller
    │   │               │       PatientController.java
    │   │               │       DoctorController.java
    │   │               │       AppointmentController.java
    │   │               │       MedicalRecordController.java
    │   │               │       
    │   │               ├───entities
    │   │               │       Patient.java
    │   │               │       Doctor.java
    │   │               │       Appointment.java
    │   │               │       MedicalRecord.java
    │   │               │       
    │   │               ├───repository
    │   │               │       DoctorRepository.java
    │   │               │       PatientRepository.java
    │   │               │       
    │   │                           
    │   └───resources
    │       │   application.properties
    │       │   
    │       │       
    │               
    └───test
        └───java
            └───org
                └───sid
                    └───cabinet_medical_bigdata
                            cabinet_medical_bigdataApplicationTests.java
                            
After setting up these architectures in both Cassandra Database and SpringBoot Application, the app would run smoothly with all the feature and controllers added.
