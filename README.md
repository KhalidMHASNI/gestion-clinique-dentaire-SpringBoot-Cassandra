# gestion-clinique-dentaire-SpringBoot-Cassandra

## 1. Setting up Cassandra (3.11.10) on Windows(10 and 11) :

This version of cassandra only works on Java8 and Python2, so let's see how to set it up:
 
1. Download Java8 : https://adoptium.net/fr/temurin/archive/?version=8
2. Download Python2 : https://www.python.org/ftp/python/2.0/BeOpen-Python-2.0.exe

After downloading and installing these versions, you'll have to make sure they're a Path variables : 

![image](https://github.com/KhalidMHASNI/gestion-clinique-dentaire-SpringBoot-Cassandra/assets/82038554/7875ef26-a08b-4293-ae97-78337db652fe)

(NB: If you already have Python3 and you don't want to mess your work libraris up with Python2 just rename the python.exe application in the Bin directory to python2.exec, so it will look like this :)
![image](https://github.com/KhalidMHASNI/gestion-clinique-dentaire-SpringBoot-Cassandra/assets/82038554/60c7eb6d-9e3d-49e7-acd3-90c2f9bfefe1)

Now you can download **Cassandra (3.11.10)** (this specific version worked for me) from this link : https://archive.apache.org/dist/cassandra/3.11.10/

After that You can run **Cassandra** on your Windows machine succesfully, by following these steps :
1. Go to the directory of Cassandra (extracted) and go to the Bin (apache-cassandra-3.11.10\bin\) directory then run these commands using **CMD** :<br>
  ```C:\Cassandrat\apache-cassandra-3.11.10\bin>cassandra```<br>
and then in another terminal, run :<br>
  ```C:\Cassandrat\apache-cassandra-3.11.10\bin>python2 -m cqlsh```
![image](https://github.com/KhalidMHASNI/gestion-clinique-dentaire-SpringBoot-Cassandra/assets/82038554/24f4f4e8-a4c6-45ea-8098-5e75f880817d)

**-> Cassandra (3.11.10) is now succefully setup in you Windows machine**

## 2. Setting up SpringBoot and linking it with Cassandra :
