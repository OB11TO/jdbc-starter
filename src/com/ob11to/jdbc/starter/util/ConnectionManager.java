package com.ob11to.jdbc.starter.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class ConnectionManager {

    private static final String URL_KEY = "db.url";
    private static final String USER_KEY = "db.user";
    private static final String PASSWORD_KEY = "db.password";

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

    public static Connection open() {  //открывает соединение
        try {
            return DriverManager.getConnection(   // по ключу получаем value
                    PropertiesUtil.get(URL_KEY),
                    PropertiesUtil.get(USER_KEY),
                    PropertiesUtil.get(PASSWORD_KEY)
            );

        } catch (SQLException e) {
            throw new RuntimeException(e); //исключение обернули в Runtime
        }
    }
}

