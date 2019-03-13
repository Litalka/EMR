# Lital Kapon
# Electronic Medical Record System

EMR - RESTful web service using Java and Spring

The service’s data saved on a json file named records_backup located in project's folder.
In case the service shut down or restarted the data will restored from the file.

The class FileExternalStorage handle the access to the file (open, read, write).
FileExternalStorage implements the interface IExternalStorage so if we will want to save the data on DB instead of a file we won't need to change the logic.

Run the application locally and open http://localhost:8080 in your web browser.
You should see an html page that will help you check the functions in the API.

