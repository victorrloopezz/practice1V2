package dacd.lopez.control;

import dacd.lopez.model.Location;
import dacd.lopez.model.Weather;

import java.sql.*;
import java.time.Instant;

public class SqliteWeatherStore implements WeatherStore {
    @Override
    public void save(Location location, Instant instant) {
        WeatherProvider weatherProvider = new OpenWeatherMapProvider();
        Weather weather = weatherProvider.getWeather(location, instant);

        if (weather != null) {
            try {
                Connection connection = DriverManager.getConnection("jdbc:sqlite:C:/Users/victo/Documents" +
                        "/DOCUMENTOS UNI/CURSO2/DACD/primerTrabajo/weatherDatabase.db");

                String tableName = "weather_" + location.getName().toLowerCase().replace(" ", "_");
                String createTableSQL = "CREATE TABLE IF NOT EXISTS " + tableName + " (" +
                        "clouds INTEGER," +
                        "wind REAL," +
                        "temperature REAL," +
                        "humidity INTEGER," +
                        "instant TEXT," +
                        "pop REAL" +
                        ")";

                System.out.println("Creada tabla para " + location.getName());
                Statement statement = connection.createStatement();
                statement.executeUpdate(createTableSQL);

                if (doesWeatherRecordExist(connection, tableName, instant.toString())) {
                    String updateWeatherSQL = "UPDATE " + tableName + " SET clouds=?, wind=?, temperature=?, humidity=?, pop=? " +
                            "WHERE instant=?";
                    PreparedStatement updateStatement = connection.prepareStatement(updateWeatherSQL);

                    updateStatement.setInt(1, weather.getAll());
                    updateStatement.setDouble(2, weather.getSpeed());
                    updateStatement.setDouble(3, weather.getTemp());
                    updateStatement.setInt(4, weather.getHumidity());
                    updateStatement.setDouble(5, weather.getRain());
                    updateStatement.setString(6, instant.toString());

                    updateStatement.executeUpdate();
                } else {
                    String insertWeatherSQL = "INSERT INTO " + tableName + " (clouds, wind, temperature, humidity, instant, pop)" +
                            " VALUES (?, ?, ?, ?, ?, ?)";
                    PreparedStatement insertStatement = connection.prepareStatement(insertWeatherSQL);

                    insertStatement.setInt(1, weather.getAll());
                    insertStatement.setDouble(2, weather.getSpeed());
                    insertStatement.setDouble(3, weather.getTemp());
                    insertStatement.setInt(4, weather.getHumidity());
                    insertStatement.setString(5, instant.toString());
                    insertStatement.setDouble(6, weather.getRain());

                    insertStatement.executeUpdate();
                }

                connection.close();

            } catch (SQLException | NullPointerException exc) {
                exc.printStackTrace();
            }
        } else {
            System.out.println("No weather data found for " + location.getName() + " at " + instant);
        }
    }

    private static boolean doesWeatherRecordExist(Connection connection, String tableName, String instant)
            throws SQLException {
        String checkSQL = "SELECT COUNT(*) FROM " + tableName + " WHERE instant = ?";
        PreparedStatement checkStatement = connection.prepareStatement(checkSQL);
        checkStatement.setString(1, instant);
        ResultSet resultSet = checkStatement.executeQuery();

        return resultSet.getInt(1) > 0;
    }
}
