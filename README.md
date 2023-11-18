# Practice1V2(WeatherProvider)
DACD, Grado en Ciencia e Ingeniería de Datos, curso 2023-24, ULPGC, Escuela de Ingeniería Informática.

## Funcionalidad
El proyecto implementa un sistema automatizado para obtener datos meteorológicos de diversas ubicaciones utilizando la API de OpenWeatherMap, donde un controlador (WeatherController) coordina la obtención de datos, almacenándolos en una base de datos SQLite. 
El programa principal (Main) utiliza un temporizador para ejecutar esta tarea periódicamente y las clases Location y Weather modelan la información de ubicación y datos meteorológicos, respectivamente.

## Recursos empleados
### Entorno de desarrollo y herramientas de control: 
El proyecto esta desarrollado en IntelliJ IDEA empleando la herramienta de control de versiones Git.

### Gestión de Dependencias:
El proyecto utiliza Maven como sistema de gestión de proyectos y construcción. El archivo POM (pom.xml) define la configuración del proyecto, incluyendo las dependencias necesarias para su ejecución.

### Bibliotecas y Frameworks:
Se utiliza la biblioteca GSON para el procesamiento de JSON.
La librería Jsoup se emplea para realizar solicitudes web y procesar respuestas HTML.

### Base de Datos:
Se utiliza SQLite para almacenar datos meteorológicos.

### Planificación Temporal:
Se emplea la clase Timer para ejecutar tareas periódicas, en este caso, la obtención y almacenamiento de datos meteorológicos.

## Diseño
### Principios de diseño:
Se estructura en 2 paquetes principales(control y model).

*Responsabilidad Única:*
Cada clase tiene una responsabilidad única y específica.

*Abierto/Cerrado:* El código está diseñado para ser extendido sin necesidad de modificarlo.

*Inversión de Dependencia:* Se utilizan interfaces para desacoplar componentes y facilitar la sustitución.

### Patrones de diseño:
*Observer:* Se emplea en la clase WeatherController para coordinar operaciones en diferentes ubicaciones e instantes.

*Singleton (enfoque):* La clase OpenWeatherMapProvider utiliza un método estático para obtener la clave de la API.

*Diagrama de clases:*

![image](https://github.com/victorrloopezz/practice1V2/assets/145262837/a71cdfc4-b044-4a27-8496-7c90b9112721)
