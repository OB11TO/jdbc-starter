package com.ob11to.jdbc.starter;

import com.ob11to.jdbc.starter.util.ConnectionManager;
import org.postgresql.Driver;

import java.sql.SQLException;

public class JdbcRunner {
    public static void main(String[] args) throws SQLException {
        Class<Driver> driverClass = Driver.class;


        try (var connection = ConnectionManager.open()) {
            System.out.println(connection.getTransactionIsolation()); // по умолчанию TRANSACTION_READ_COMMITTED

        }


    }
}
