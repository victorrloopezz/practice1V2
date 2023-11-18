# Practice1V2 (WeatherProvider)
DACD, Degree in Data Science and Engineering, academic year 2023-24, ULPGC, School of Computer Engineering.

## Functionality
The project implements an automated system to obtain weather data from various locations using the OpenWeatherMap API, where a controller (WeatherController) coordinates the data retrieval and stores it in an SQLite database.
The main program (Main) uses a timer to execute this task periodically, and the classes Location and Weather model location information and weather data, respectively.

## Resources Used
### Development Environment and Version Control Tools:
The project is developed in IntelliJ IDEA using the Git version control tool.

### Dependency Management:
The project uses Maven as a project management and build system. The POM file (pom.xml) defines the project configuration, including the dependencies necessary for its execution.

### Libraries and Frameworks:
The GSON library is used for JSON processing. The Jsoup library is employed for making web requests and processing HTML responses.

### Database:
SQLite is used to store weather data.

### Temporal Planning:
The Timer class is used to execute periodic tasks, in this case, obtaining and storing weather data.

## Design
### Design Principles:
It is structured into 2 main packages (control and model).

*Single Responsibility:* Each class has a unique and specific responsibility.

*Open/Closed:* The code is designed to be extended without the need for modification.

*Dependency Inversion:* Interfaces are used to decouple components and facilitate substitution.

### Design Patterns:
*Observer:* Used in the WeatherController class to coordinate operations in different locations and instances.

*Singleton (approach):* The OpenWeatherMapProvider class uses a static method to obtain the API key.

*Class Diagram:*

![image](https://github.com/victorrloopezz/practice1V2/assets/145262837/2a4c8755-87b1-48c8-9e9a-ffc0e1d5cab3)

