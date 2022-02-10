# Credit Suisse Logger
Credit Suisse Logger is an application for analysing the log events.

## Installation
Checkout out the code and build the application through the command line using below maven command:
```bash
mvn clean install
```

## Running the application
Once the application build is successful, the application's jar file will be generated in the target folder. To run the application, go to this target folder location in the command line and run below command: 
```bash
java -jar logger-0.0.1-SNAPSHOT.jar logFilePath=<absolute-path-to-file>
```

##Note:
The sample logfile.txt is provided under the application's resources folder.