# Practice2
DACD, Degree in Data Science and Engineering, academic year 2023-24, ULPGC, School of Computer Engineering.

## Functionality
The project consists of two main modules. The first module (prediction-provider) retrieves weather data from the OpenWeatherMap API, sends it to a message broker using Java Message Service (JMS). The second module(event-store-builder) subscribes to the broker, processes the received messages, and stores them in files.

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
*Single Responsibility Principle (SRP):* Each class has a specific responsibility, adhering to the SRP. For instance, OpenWeatherMapProvider handles weather data retrieval, WeatherController manages application flow, and JmsWeatherStore handles broker storage.

*Dependency Inversion Principle (DIP):* Code follows DIP as WeatherController depends on the WeatherProvider and WeatherStore interfaces, allowing dependency injection for testability and extensibility.

*Interface Usage (WeatherProvider, WeatherStore, and Listener):* Interfaces provide abstraction, allowing different provider, store, and event listener implementations without modifying client code.

### Design Patterns:
*Observer Pattern (AMQTopicSubscriber):* Used the Observer pattern by implementing MessageListener to listen for messages on a specific topic. When a new message arrives, observers (in this case, the Listener) are notified.

*Composition and Aggregation (WeatherController):* The controller is composed of instances of WeatherProvider and WeatherStore, providing flexibility when changing implementations without modifying the controller.

## Prediction-Provider Class Diagram 
![image](https://github.com/victorrloopezz/practice1V2/assets/145262837/ae60cd67-fbbc-4438-87db-0cb850e2d2aa)



## Event-Store-Builder Class Diagram 
![image](https://github.com/victorrloopezz/practice1V2/assets/145262837/8226b332-871d-4355-a6d2-28b0db32f770)



