package org.collections.web.util;

import lombok.SneakyThrows;
import org.collections.web.dto.IConvertible;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DbUtil {

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
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/db",
                "user",
                "password"
        );
        statement = connection.createStatement();
    }
}
