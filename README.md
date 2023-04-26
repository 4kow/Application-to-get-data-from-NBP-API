This application allows the user to get access to particular data in nbp.api.pl

Application is created using the Java Spring Boot technology and it consists of several classes. 

Dependencies used to create this application are:
"Spring boot starter web" which is a starter for building web applications using Spring MVC
"Jackson databind" which is used for JSON parsing and serialization in Java
"Spring boot DevTools" which is a module that provides fast application restarts, LiveReload, and configurations for enhanced 
development experience
"Spring boot starter test" which is used for testing Spring boot applications

The main three classes that were designed for the purpose of this application are "NbpCurrency.java", "NbpApiService.java" and 
"NbpApiController.java". First class contains several subclasses that represent NBP currencies with different properties. The second
class is used to provide methods for retrieving and processing data from the NBP API. It uses the RestTemplate class from Spring to 
send HTTP requests to the API and retrieve the response data as JSON strings.
And the third class is the controller class which defines endpoints that handle HTTP GET requests and return data in JSON format.

The instruction of how to use this application with postman:
1. Open postman 
2. Create new request
3. Make sure to use the GET method
4. Use the following URL:

For first endpoint:

http://localhost:8080/api/exchange-rates/axr?date=2023-04-25&code=USD

Make sure to change the date and the code at the end of the URL to suit your needs.
Using this URL will give the output of the average exchange rate of the chosen currency for the chosen date.


For second endpoint:

http://localhost:8080/api/exchange-rates/min-max-average?code=USD&lastQuotations=30

Make sure to change the currency code and the number of last quotations (max 255) to suit your needs
Using this URL will give the output of the maximum and minimum value of the exchange rate for the chosen currency within the amount of
days chosen when sending the request


For third endpoint:

http://localhost:8080/api/exchange-rates/buy-sell-diff?code=USD&lastQuotations=30

Make sure to change the currency code and the number of last quotations (max 255) to suit your needs
Using this URL will give the value of the difference between the buy and ask rate for every day within the amount of days chosen when 
sending the request

