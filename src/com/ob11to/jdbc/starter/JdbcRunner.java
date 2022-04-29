package com.ob11to.jdbc.starter;

import com.ob11to.jdbc.starter.util.ConnectionManager;
import org.postgresql.Driver;

import java.sql.SQLException;


public class JdbcRunner {
    public static void main(String[] args) throws SQLException {
        Class<Driver> driverClass = Driver.class;

        String sql = """
                CREATE TABLE IF NOT EXISTS info
                (
                    id SERIAL PRIMARY KEY,
                    data TEXT NOT NULL
                );
                """;
        try (var connection = ConnectionManager.open();
             var statement = connection.createStatement()) {
            System.out.println(connection.getTransactionIsolation()); // по умолчанию TRANSACTION_READ_COMMITTED
            System.out.println(connection.getSchema()); //получаем схему, где находимся

            var executeStatement = statement.execute(sql); // отправляем запрос в бд
            System.out.println(executeStatement);

        }
    }
}
