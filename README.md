## Table of Contents

- [Quick start guide: how to use client.](#markdown-header-quick-start-guide-how-to-use-client)  
  1. [Main window](#markdown-header-main-window)  
  2. [POST REQUEST](#markdown-header-post-request)  
  3. [GET REQUEST](#markdown-header-get-request)  
  4. [UPDATE REQUEST](#markdown-header-update-request)  
  5. [DELETE REQUEST](#markdown-header-delete-request)  
  6. [ALL 4 REQUESTS](#markdown-header-all-4-requests)  
  7. [Unset correlationId](#markdown-header-unset-correlationid)  
  8. [Test throughput](#markdown-header-test-throughput)  
  9. [Choose CSV FILE, Reset opened file](#markdown-header-choose-csv-file-reset-opened-file)  
- [Build and run application](#markdown-header-build-and-run-application)

## Quick start guide: how to use client

There are few buttons on the UI, five of them for query execution. Every response time result is saved to a file TestResponse.csv.
Also, there are buttons to choose CSV file, if you want to add the new response time result to the existing file, and button to test throughput. 
Parameter "CorrelationId" is responsible for variable that changes after each thread executes a request and this variable is a parameter that is passed to the POST, GET, PUT, DELETE functions as an ID parameter. It can be reset by pressing the Unset correlationId button.

There are some problem using it with different servers — it is about changing the IP address of the server in the code. Immutable variable *uri* should be changed in the file *CustomerClient.kt* and property *client.defaultUri* in the file *Config.kt*. In the near future I plan to add the ability to change the IP address from the UI. 
So, there is example of using the client:  

 1. #### **Main window**  
 ![enter image description here](imgs/main.png =500x)
 
 2. #### **POST REQUEST**  
 After clicking POST REQUEST button you should enter the number of clients.  
 ![enter image description here](https://bytebucket.org/N1Kk1/kotlinsoapclient/raw/ec141e3eda7e73941457d73621e7d9903a0fbc74/imgs/imgs/POST1.png =500x)  
 Then, push to the ENTER and you will see the response and the time of the response.  
 ![enter image description here](imgs/POST2.png =500x)

 3. #### **GET REQUEST**  
 ![enter image description here](imgs/GET.png =500x)

 4. #### **UPDATE REQUEST**  
 ![enter image description here](imgs/UPDATE.png =500x)  

 5. #### **DELETE REQUEST**  
 ![enter image description here](imgs/DELETE.png =500x)  

 6. #### **ALL 4 REQUESTS**  
 This button executes all previous requests together.  
 ![enter image description here](imgs/ALLFOUR1.png =500x)  
 Press ENTER and you will see response time of the four requests and response of the last (DELETE) request.  
 ![enter image description here](imgs/ALLFOUR2.png =500x)  

 7. #### **Unset correlationId**  
 If you want, you can reset the correlationId.  
 ![enter image description here](imgs/UNSETCORRID.png =500x)  

 8. #### **Test throughput**  
 After you press this button you need to enter the file name.  
 ![enter image description here](imgs/TESTTHROUGHPUT1.png =500x)  
 Then push enter and you will see the received image, its size, response time and throughput. Measurement results will be saved to TestThroughput.csv file.  
 ![enter image description here](imgs/TESTTHROUGHPUT2.png =500x)  
 
 9. #### **Choose CSV file, Reset opened file**  
 When you want to save the measurement results to the existing file, you need to open this file.  
 ![enter image description here](imgs/CHOOSEFILE.png =500x)  
 And if you want to save results to the new file press the Reset opened file button.  

## Build and run application

 Using maven, you can run the application by using: 
     
     ./mvnw clean install
 And then run the JAR file, as follows:
     
     java -jar target/kotlinSOAPClient-0.0.1-SNAPSHOT.jar
                      

