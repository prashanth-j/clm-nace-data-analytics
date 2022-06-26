#CLM-NACE-DATA-ANALYTICS


#Introduction :
clm-nace-data-analytics is Spring Boot application implemented in view
of Microservices architecture

#What this tool can do ?
 * Application acts as tool for uploading csv file and store the information
   in database
 * Upon using Open CSV, performance is hugely improved by using the CSV Bean mapper over 
each iteration

# Swagger URL

 * Upon starting your appication on container or JVM on click below swagger url 
1. <a> http://localhost:8080/swagger-ui.html </a>
2. <a> http://localhost:8080 </a>
  
#Technologies 
 1. Java 11
 2. Spring Boot Starter Web
 3. Spring Boot JPA
 4. Spring Fox swagger
 5. Postgres SQL
 6. Open CSV
 7. Swagger
 8. Junit 5
 9. Mockito

#Architecture :

This tool has been implemented using Model View Controller pattern:

* Controller : Attachment Controller
* Service: FileAttachment Service
* Repository Layer :

1.FileAttachmenetRepository 
2.CLMNaceRepository 

TODO : Can be improved by using SOLID principle

# Functionality Implement

 * This api has following endpoints exposed
 1. putNaceDetails (url : /upload/nacedetails) : This has 
capability upload large csv file and save each information in
database and also store the file in blob format.
 2. downloadFile (url : /download/{fileId}) : After upload the large csv file one 
can download the csv file stored in databse
 3. Read Nace Details(url :/readNaceDetails) : This can read the whole data in Json Format 
upon clicking on the link
 4. Read Nace Details By Id(url :/readNaceDetails/{order}) :
  This can read the whole data in Json Format
    upon clicking on the link
 5. Exception Handler using Spring Boot.

# Testing 
1. Code Coverge using Junit with help of Mockito
2. Todo : API Testing: Spring Integration (Not Implemented)

#Improvement
  1. Can be add more flexible exception handler upon identifying the use case

# Suggestion Box

Any suggestion to improve this code would be happy to receive.