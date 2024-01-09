# Final Project
DACD, Degree in Data Science and Engineering, academic year 2023-24, ULPGC, School of Computer Engineering.

## Functionality
This project involves an intelligent travel planning application for the Canary Islands. It combines weather data obtained from previous projects with new event data to showcase to customers, among the best hotels in the selected location, those offering the best prices for a 3-night stay. The project consists of four main modules. The first module (prediction-provider) retrieves weather data from the OpenWeatherMap API, sends it to a message broker using Java Message Service (JMS). Also the hotel-price-provider retrieves hotel data from the Xotelo API, sends it to a message broker using Java Message Service (JMS) module. Then, the datalake-builder module subscribes to the broker(durable), processes the received messages, and stores them into a datalake. And Finally, canary-insigths-business-unit module subscribes to the broker(durable) processes the received messages, and stores them into a datamart, furthermore in this module the aplication interact with the client through CLI. 

## How to use it
When running the main class, when prompted to choose an island, you must enter the name of one of the 8 Canary Islands, grammatically correct. Before that, you the other modules must be runned and the events must be ready to be consumed.

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

*MVC:* The MVC pattern separates concerns of presentation, business logic, and event control. The model handles business logic and data manipulation, the view takes care of user presentation, and the controller coordinates interactions between the view and the model.

## Prediction-Provider Class Diagram 
![image](https://github.com/victorrloopezz/practice1V2/assets/145262837/ae60cd67-fbbc-4438-87db-0cb850e2d2aa)



## DataLake-Builder Class Diagram 
![DataLake2](https://github.com/victorrloopezz/practice1V2/assets/145262837/63bd3910-e595-4322-b4f6-4b28c393bbc8)



## Hotel-Price-Provider Class Diagram
![HotelInfo](https://github.com/victorrloopezz/practice1V2/assets/145262837/99f95230-85e1-48a2-8f5f-b6ddaa8a2bf2)



## Canary-Insights-Business-Unit Class Diagram
![image](https://github.com/victorrloopezz/practice1V2/assets/145262837/5ce56640-91c1-4bca-a6b7-55c56c40c41f)






