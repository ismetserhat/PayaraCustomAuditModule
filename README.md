# Payara Custom Audit Module Example Project

The project contains an example of custom audit module with jdbc realm in the java application created on the Payara application server.
This project was created in Java 8 standard in Eclipse environment.

Custom audit module inserts audits with jdbc or log4j integration into the Oracle database. 

Database Creating Scripts:


  CREATE TABLE APP_LOGS 
   (	LOG_ID VARCHAR2(100 BYTE), 
		ENTRY_DATE TIMESTAMP (6), 
		LOGGER VARCHAR2(100 BYTE), 
		LOG_LEVEL VARCHAR2(100 BYTE), 
		MESSAGE VARCHAR2(1000 BYTE), 
		LOG_EXCEPTION VARCHAR2(1000 BYTE), 
		PRIMARY KEY (LOG_ID)
   );


  CREATE TABLE APP_AUDIT 
   (	LOG_ID VARCHAR2(100 BYTE), 
		ENTRY_DATE TIMESTAMP (6), 
		LOGGER VARCHAR2(100 BYTE), 
		LOG_LEVEL VARCHAR2(100 BYTE), 
		MESSAGE VARCHAR2(1000 BYTE), 
		PRIMARY KEY (LOG_ID)
   );


# Creating Custom Audit Module


Custom Audit Module is actived for server "server-config" shold be used. If Custom Audit Module is actived for domain,
"server-config" shold be used.

1. Step:

server-config -> Security -> Enable Audit Modules -> Enabled


2. Step:


server-config -> Security -> Audit Modules -> New Custom Audit Module

name : CustomAuditModule
class: com.payara.custom.audit.module.CustomAuditModule

3. Step: 

mvn install "payara-custom-audit-module" project and copy target "CustomAuditModule-1.0.0.jar" and paste to 
   {PAYARA LOCATION}\glassfish\lib folder
   
4. Step:

Payara Restart


After all step, custom audit-module is active.

 
