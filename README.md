# Final Project
DACD, Degree in Data Science and Engineering, academic year 2023-24, ULPGC, School of Computer Engineering.

## Functionality
This project involves an intelligent travel planning application for the Canary Islands. It combines weather data obtained from previous projects with new event data to showcase to customers, among the best hotels in the selected location, those offering the best prices for a 3-night stay. The project consists of four main modules. The first module (prediction-provider) retrieves weather data from the OpenWeatherMap API, sends it to a message broker using Java Message Service (JMS). Also the hotel-price-provider retrieves hotel data from the Xotelo API, sends it to a message broker using Java Message Service (JMS) module. Then, the datalake-builder module subscribes to the broker(durable), processes the received messages, and stores them into a datalake. And Finally, canary-insigths-business-unit module subscribes to the broker(no durable) processes the received messages, and stores them into a datamart, furthermore in this module the aplication interact with the client through CLI. 

## Resources Used
### Development Environment and Version Control Tools:
The project is developed in IntelliJ IDEA using the Git version control tool.

### Dependency Management:
The project uses Maven as a project management and build system. The POM file (pom.xml) defines the project configuration, including the dependencies necessary for its execution.

### Libraries and Frameworks:
The GSON library is used for JSON processing. The Jsoup library is employed for making web requests and processing HTML responses. The Apache ActiveMQ library is used as a messaging broker, implementing the Java Message Service (JMS) API for message-oriented middleware.

### Temporal Planning:
The Timer class is used to execute periodic tasks, in this case, obtaining and storing weather data.

## Design
### Design Principles:
*Single Responsibility Principle (SRP):* Each class has a specific responsibility, adhering to the SRP. For instance, OpenWeatherMapProvider handles weather data retrieval, WeatherController manages application flow, JmsWeatherStore handles broker storage, AMQDataMartSubscriber subscribes to message topics, BusinessUnit handles user interactions and data processing...

*Dependency Inversion Principle (DIP):* Code follows DIP as WeatherController depends on the WeatherProvider and WeatherStore interfaces (The same with the other modules with interfaces), allowing dependency injection for testability and extensibility.

*Interface Usage:* Interfaces provide abstraction, allowing different provider, store, and event listener implementations without modifying client code.

### Design Patterns:
*Observer Pattern (AMQDataLakeSubscriber/AMQDataMartSubscribe):* Used the Observer pattern by implementing MessageListener to listen for messages on a specific topic. When a new message arrives, observers (in this case, the Listener) are notified.

*Composition and Aggregation (Controllers):* The controllers is composed of instances of Providers and Stores, providing flexibility when changing implementations without modifying the controllers.

## Prediction-Provider Class Diagram 
![image](https://github.com/victorrloopezz/practice1V2/assets/145262837/ae60cd67-fbbc-4438-87db-0cb850e2d2aa)



## Event-Store-Builder Class Diagram 
![image](https://github.com/victorrloopezz/practice1V2/assets/145262837/8226b332-871d-4355-a6d2-28b0db32f770)



