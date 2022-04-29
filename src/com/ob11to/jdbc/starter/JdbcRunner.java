package com.ob11to.jdbc.starter;

import com.ob11to.jdbc.starter.util.ConnectionManager;
import org.postgresql.Driver;

import java.sql.SQLException;


public class JdbcRunner {
    public static void main(String[] args) throws SQLException {
        Class<Driver> driverClass = Driver.class;

//        Пример INSERT
//         String sql = """
//                INSERT INTO info(data)
//                VALUES
//                ('Test1'),
//                ('Test2'),
//                ('Test3'),
//                ('Test4');
//                """;

        String sql = """
                UPDATE info
                SET data = 'newTest'
                WHERE id > 1;
                """;
        try (var connection = ConnectionManager.open();
             var statement = connection.createStatement()) {

            System.out.println(connection.getTransactionIsolation()); // по умолчанию TRANSACTION_READ_COMMITTED
            System.out.println(connection.getSchema()); //получаем схему, где находимся

           // var executeStatement = statement.execute(sql); // отправляем запрос в бд
            var executeStatement = statement.executeUpdate(sql); // отправляет запрос в бд, возвращает int, кол-во изменений


            System.out.println(executeStatement);
            System.out.println(statement.getUpdateCount()); // вернет количество обновлений в бд

        }
    }
}
