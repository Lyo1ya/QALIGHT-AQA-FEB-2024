package org.collections.web.util;

import lombok.SneakyThrows;
import org.collections.web.dto.IConvertible;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DbUtil {

    private final static String ENV_TYPE = System.getProperty("driver.type", "CHROME");
    private final static String ENV_TYPE_JENKINS = "JENKINS";

    private static Connection connection;
    private static Statement statement;
    private final static String SELECT_DEST_BASE = "SELECT City, Price FROM DestinationPrices WHERE City = '%s'";

    @SneakyThrows
    public static void storeInDB(String sql) {
        try {
            getDBConnection();
            statement.execute(sql);
        } catch (Exception e) {
            System.out.println("Failed to execute SQL Query: " + sql);
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }

    @SneakyThrows
    public static List<Object> executeSelectStatement(String selectSQL, IConvertible iConvertible) {
        List<Object> results = new ArrayList<>();
        try {
            getDBConnection();
            ResultSet resultSet = statement.executeQuery(selectSQL);
            while (resultSet.next()) {
                results.add(iConvertible.fromResultSet(resultSet));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        return results;
    }

    @SneakyThrows
    public static Map<String, Float> getCityPriceFromDb(String key) {
        Map<String, Float> cityPriceFromDb = new HashMap<>();
        String getCities = String.format(SELECT_DEST_BASE, key);
        try {
            getDBConnection();
            ResultSet resultSet = statement.executeQuery(getCities);
            while (resultSet.next()) {
                String city = resultSet.getString("City");
                Float price = resultSet.getFloat("Price");
                cityPriceFromDb.put(city, price);
            }
        } catch (Exception e) {
            System.out.println("Failed to execute SQL Query: " + getCities);
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
        return cityPriceFromDb;
    }

    private static void getDBConnection() throws SQLException, ClassNotFoundException {
        String hostName;

        if (ENV_TYPE_JENKINS.equals(ENV_TYPE)) {
            hostName = "jdbc:mysql://mysql-db-1:3306/db";
        } else {
            hostName = "jdbc:mysql://localhost:3306/db";
        }

        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection(
                hostName,
                "user",
                "password"
        );
        statement = connection.createStatement();
    }
}
