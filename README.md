Spring rest kotlin tips

This is the accompanying project of the blog post https://blog.codecentric.de/en/?p=42870

Build with maven: mvn clean install
Execute application:
cd target
java -jar spring-rest-kotlin-tips-0.0.1-SNAPSHOT.jar

Create a new customer:

POST http://localhost:8080/customers
    body: {
          "name": "John Doe",
          "dateOfBirth": "1979-05-07"
          }

Get a customer with id 42:
GET http://localhost:8080/customers/42

Get all customers:
GET http://localhost:8080/customers

Delete customer with id 42
DELETE http://localhost:8080/customers/42

Update the name of customer 42:
PUT http://localhost:8080/customers
body: {
"id":42,
"name":{"value": "Aristotle the wise"}
}

Update the date of birth of customer 42 to null:
PUT http://localhost:8080/customers
body: {
"id":42,
"name":{"value": "null}
}

Note that this sample application has no web security or persistent storage.