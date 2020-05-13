# JavaEventApp
Java Spring, JUnit
# Events Organiser Application

### Purpose  
A program that will take several events, from a CSV file and organise them into a calendar of events, based on certain rules/constraints. 

### Application Overview
The Web Application (in MVC Spring) developed consists of 2 pages:
- [Agenda Page to show the Calendar for the Events] (http://localhost:8080/Agenda) (https://i.imgur.com/AI6wH1v.png)
- [Upload Page to upload the CSV File. ] (http://localhost:8080/fileUploadForm) (https://i.imgur.com/7adJuZb.png)

#### Steps
* The Event Organiser goes to the Upload Page and uploads the csv file which is simply saved on the system. 
* The participants go to the Agenda Page to see the resulting calendar from the uploaded CSV File. During that step, in the background, the uploaded CSV is parsed and processed by the algorithm to get the calendar. 

### Project Technical Planning

- Method to Arrange Events - &#x2611;
- Method to read the CSV - &#x2611;
- Data Model Class for the CSV Input - &#x2611;
- Data Model Class for the Output - &#x2611;
- Set Time Table (Time Slots) for the Output - &#x2611;
- Display on a Web Page, in a tabular form, similar to the https://meeting.afrinic.net/afrinic-29/agenda/programme - &#x2611;
- Write Error Handling - &#x2611;
- Write Unit Test Methods - &#x2611;
- Web Page to upload the CSV File - &#x2611;


### Algorithm Pseudocode for Ordering the Events for the Calendar 
- Get the following Times : 
	* Time the day Starts, 
	* Time the day ends,
	* Time morning Events Starts, 
	* Time morning Events Ends, 
	* Time Afternoon Events Starts, 
	* Time Afternoon Events Ends. 
- Calculate the Total Amount of Time available for Events each Day (Based on what time morning and afternoon events start and end) -dayDuration
- Calculate the Total Duration of All the Events from the csv File. - totalDuration 
- Get the maximum Number of Days that the events will span into: Divide totalDuration/dayDuration
- Keep a count of the morning = currAmDuration. 
- Keep a count of the afternoon = currPmDuration. 
- For each Day, 
	* LOOP  through the events ordered in Descending order. If the event Duration is less than the currDuration for the morning Add
those to the list of events for the Calendar Output.
	* Update the currAmDuration: currAmDuration -=event duration added 
	* LOOP through events ordered in Ascending Order. If the event duration is less than the currDuration for the Afternoon, add those to the
list of events for the Calendar Output
	* Set the duration value in the list of input events to 0
	* Update the currPmDuration: currPmDuration -=event duration added 
	* Set the duration value in the list of input events to 0
- When all the events are added the currAmDuration and currPmDuration will be 0
- In between, add the slot for the lunch 
- At the end of each day, add the slot for Networking Session.
- In the loop keep track of the current Time : currTime, to get Start Time for each Event. currTime = currTime + Duration of Each Event. 
CurrTime is the End Time of each Event and Start Time of next Event. 
- At the end of the 2 LOOPS, the calendar will be filled out with the events in the right order with the Start and End Times. 

Note: The events are sorted in Descending order for the morning sessions and ascending order for afternoon sessions so that events of longer duration are held in the morning. 

### Testing Done
- Unit Test - Test for Invalid CSV Data : Test Files can be found under [/Uploads/TestFiles]. Unit Tests Done here : afrieventsapp/src/test/java/com/afri/junit5


### To Run the Project in Ubuntu
The Project has been created a maven Project. You can import the folder in Eclipse, Right Click on the Project > Run as > Maven Build (tomcat7:run)
Or 
```
cd  afrieventsapp
mvn tomcat7:run
```
If you encounter the following error Java Bind Exception: https://i.imgur.com/FpvUgsg.png
run 
```
pkill -9 -f tomcat
```

###  Future Enhancement
- Create Login Page before accessing the UploadFileForm Page to restrict access to the page. 
- Cater for more breaks (Tea Breaks)
- When the file is uploaded, parse and Save the data of the EventTime Class in Database. Use Rest Services to fetch the Data on the Agenda Page. 
- MVC Spring Unit Tests 

