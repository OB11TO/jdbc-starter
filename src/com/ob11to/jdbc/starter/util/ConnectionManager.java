package com.ob11to.jdbc.starter.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class ConnectionManager {

    private static final String URL = "jdbc:postgresql://localhost:5432/flight_repository";
    private static final String USER = "postgres";
    private static final String PASSWORD = "postgres";

    private ConnectionManager() {
    }

    static { // код, который будет работать для старых версий < Java1.8
        loadDriver();
    }

    private static void loadDriver() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Connection open(){  //открывает соединение
        try {
            return DriverManager.getConnection(URL,USER,PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e); //исключение обернули в Runtime
        }
    }
}

