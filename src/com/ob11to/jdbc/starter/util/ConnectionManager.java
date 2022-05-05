package com.ob11to.jdbc.starter.util;

import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public final class ConnectionManager {

    private static final String URL_KEY = "db.url";
    private static final String USER_KEY = "db.user";
    private static final String PASSWORD_KEY = "db.password";
    private static final String POOL_SIZE_KEY = "db.pool";
    private static final Integer DEFAULT_POOL_SIZE = 10;
    private static BlockingQueue<Connection> pool; //очередь соединений
    private static List<Connection> sourceConnection;

    private ConnectionManager() {
    }

    static {
        loadDriver();
        initConnectionPool();
    }

    private static void loadDriver() { // код, который будет работать для старых версий < Java1.8
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void initConnectionPool() {  //дописать пул соединений использую BlockingQueue(прочитать фул версию)
        var poolSize = PropertiesUtil.get(POOL_SIZE_KEY);
        int size = poolSize == null ? DEFAULT_POOL_SIZE : Integer.valueOf(poolSize);
        pool = new ArrayBlockingQueue<>(size);
        sourceConnection = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            var connection = open(); // открыли и добавили соединения в pool
            var proxyConnection = (Connection) //создаем проки, для того, чтобы вернуть connection
                    Proxy.newProxyInstance(ConnectionManager.class.getClassLoader(), new Class[]{Connection.class},
                    (proxy, method, args) -> method.getName().equals("close")  // если connection закрыт
                            ? pool.add((Connection) proxy)  // то заново добавляем его в pool
                            : method.invoke(connection, args));
            pool.add(proxyConnection);
            sourceConnection.add(connection); //весь список соединений
        }
    }

    public static Connection get() {
        try {
            return pool.take(); // вернет 1 соединение, если оно есть, иначе ждет
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private static Connection open() {  //открывает соединение, сделал private, чтобы не могли получить к нему доступ
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

    public static void closePool() {
        try{
            for(Connection connection : sourceConnection) {
                connection.close(); // закрываем
            }
        }catch (SQLException e){
            throw new RuntimeException(e);
        }

    }
}

