package dacd.lopez.control;

import dacd.lopez.model.Location;
import dacd.lopez.model.Weather;

import java.sql.*;
import java.time.Instant;

public class SqliteWeatherStore implements WeatherStore {
    @Override
    public void save(Weather weather) {

    }

    @Override
    public void load(Location location, Instant instant) {
        WeatherProvider weatherProvider = new OpenWeatherMapProvider();
        Weather weather = weatherProvider.getWeather(location, instant);
        if (weather != null) {
            try {
                Connection connection = DriverManager.getConnection("jdbc:sqlite:C:/Users/victo/Documents/DOCUMENTOS UNI/CURSO2/DACD/primerTrabajo/weather_data.db");
                String createTableSQL = "CREATE TABLE IF NOT EXISTS weather (" +
                        "name TEXT ," +
                        "clouds INTEGER," +
                        "wind REAL," +
                        "temperature REAL," +
                        "humidity INTEGER," +
                        "instant TEXT," +
                        "pop REAL" +
                        ")";
                System.out.println("Tabla creada");
                Statement statement = connection.createStatement();
                statement.executeUpdate(createTableSQL);

                String insertWeatherSQL = "INSERT INTO weather (name, clouds, wind, temperature, humidity, instant, pop) VALUES (?, ?, ?, ?, ?, ?,?)";
                PreparedStatement preparedStatement = connection.prepareStatement(insertWeatherSQL);

                preparedStatement.setString(1, location.getName());
                preparedStatement.setInt(2, weather.getAll());
                preparedStatement.setDouble(3, weather.getSpeed());
                preparedStatement.setDouble(4, weather.getTemp());
                preparedStatement.setInt(5, weather.getHumidity());
                preparedStatement.setString(6, weather.getTs().toString());
                preparedStatement.setDouble(7, weather.getRain());
                preparedStatement.executeUpdate();

                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException(e);

            }catch (NullPointerException exception){
                exception.printStackTrace();

            }
        } else {
            System.out.println("No weather data found for ");
        }

    }
}
