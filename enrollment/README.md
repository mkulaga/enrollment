**Prerequisites**:

You must have a local instance of MongoDb running before calling any endpoints.

The settings for connecting to MongoDb are located in the application.properties file, they are defaulted to the default values of a fresh install.

The server port settings are located in the application.properties file, it is defaulted to 8080.

# Enrollment Application

> A microservice for tracking the status of enrollees in a health care program.

Run the application using **gradlew bootRun**

Run the unit tests using **gradlew cleanTest test**

Alternatively, the Gradle window in your IDE should have the "bootRun" and "test" tasks available to run

## Project Requirements

* Add a new enrollee
* Modify an existing enrollee
* Remove an enrollee entirely
* Add dependents to an enrollee
* Remove dependents from an enrollee
* Modify existing dependents


* Enrollees must have an id, name, and activation status (true or false), and a birth date
* Enrollees may have a phone number (although they do not have to supply this)
* Enrollees may have zero or more dependents
* Each of an enrollee's dependents must have an id, name, and birth date

## Important Links
Retrieve Enrollees: http://localhost:8080/enrollees
 
Swagger-UI: http://localhost:8080/swagger-ui.html

## Endpoints

Endpoint | Http Method | Description
--- | --- | --- 
/enrollees | GET | Retrieves all Enrollees
/enrollees | POST | Adds a new Enrollee
/enrollees/{enrolleeId} | GET | Retrieves an Enrollee by Id
/enrollees/{enrolleeId} | PATCH | Modifies an Enrollee
/enrollees/{enrolleeId} | DELETE | Deletes an Enrollee
/enrollees/{enrolleeId}/dependents | POST | Adds a new Dependent to an Enrollee
/enrollees/{enrolleeId}/dependents/{dependentId} | PATCH | Modifies a Dependent
/enrollees/{enrolleeId}/dependents/{dependentId} | DELETE | Deletes a Dependent

## Example Requests

#### GET - /enrollees

No Request Body

#### POST - /enrollees

```
{
  "id": "1",
  "name": "John Smith",
  "activationStatus": true,
  "dateOfBirth": "1980-10-10",
  "phoneNumber": "1-234-5678",
  "dependents": [
    {
      "id": "1",
      "name": "John Smith Jr",
      "dateOfBirth": "2000-01-01"
    },
    {
      "id": "2",
      "name": "John Smith III",
      "dateOfBirth": "2000-10-01"
    }   
  ]
}
```

#### GET - /enrollees/1

No Request Body

#### PATCH - /enrollees/1

```
{
  "id": "1",
  "name": "Jonathon Smith",
  "activationStatus": true,
  "dateOfBirth": "1980-10-10",
  "dependents": [
    {
      "id": "2",
      "name": "John Smith IV",
      "dateOfBirth": "2000-10-01"
    }   
  ]
}
```

#### DELETE - /enrollees/1

No Request Body

#### POST - /enrollees/1/dependents

```
{
  "id": "3",
  "name": "Jane Smith",
  "dateOfBirth": "2001-09-01"
}
```

#### PATCH - /enrollees/1/dependents/3

```
{
  "id": "3",
  "name": "Jane Smith",
  "dateOfBirth": "2001-09-02"
}
```

#### DELETE - /enrollees/1/dependents/3

No Request Body