package com.ob11to.jdbc.starter;

import com.ob11to.jdbc.starter.util.ConnectionManager;


import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class JdbcRunner {
    public static void main(String[] args) throws SQLException {
        Long flightId = 2L;
        var ticketsByFlightId = getTicketsByFlightId(flightId);
        System.out.println(ticketsByFlightId + "\n");

        var flightsBetween = getFlightsBetween(LocalDate.of(2020, 1, 1).atStartOfDay(), LocalDateTime.now());

        System.out.println(flightsBetween);

        checkMetaData();
    }

    private static void checkMetaData() throws SQLException {
        try (var connection = ConnectionManager.open()) {
            var metaData = connection.getMetaData();
            var driverName = metaData.getDriverName();
            System.out.println(driverName);

            var catalogs = metaData.getCatalogs();
            while (catalogs.next()){
                var catalog = catalogs.getString("TABLE_CAT"); //получить имя каталога
                System.out.println(catalog); //flight_repository

                var schemas = metaData.getSchemas();
                System.out.println(schemas);
                while (schemas.next()){
                    var schema = schemas.getString("TABLE_SCHEM");  // получить имя схемы
                    System.out.println("--------------");
                    System.out.println(schema);
                    var tables = metaData.getTables(catalog, schema, "%", new String[]{"TABLE"});
                    while (tables.next()){
                        var table_name = tables.getString("TABLE_NAME");
                        System.out.println(table_name);

                    }
                }
            }
        }

    }


    private static List<Long> getFlightsBetween(LocalDateTime start, LocalDateTime end) throws SQLException {
        List<Long> result = new ArrayList<>();

        String sql = """
                SELECT id
                FROM task26.flight
                WHERE departure_date BETWEEN ? AND ?
                """;

        try (var connection = ConnectionManager.open();
             var preparedStatement = connection.prepareStatement(sql)) {

            System.out.println(preparedStatement);
            preparedStatement.setTimestamp(1, Timestamp.valueOf(start));

            System.out.println(preparedStatement);
            preparedStatement.setTimestamp(2, Timestamp.valueOf(end));
            System.out.println(preparedStatement);

            preparedStatement.setFetchSize(50); // итерационно будем считывать данные с бд
            preparedStatement.setQueryTimeout(10); //в течение которых драйвер будет ожидать выполнения объекта
            preparedStatement.setMaxRows(100); //Устанавливает ограничение на максимальное количество строк, которое может содержать любой объект ResultSe
            var resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
//                result.add(resultSet.getObject("id", Long.class)
//                );
                result.add(resultSet.getLong("id"));
            }

        }

        return result;
    }


    private static List<Long> getTicketsByFlightId(Long flightId) throws SQLException {
        List<Long> result = new ArrayList<>();

        //language=PostgresSQL
        String sql = """
                SELECT id
                FROM task26.ticket t
                WHERE t.flight_id = ?
                """;

        try (var connection = ConnectionManager.open();
             var prepareStatement = connection.prepareStatement(sql)) {

            prepareStatement.setLong(1, flightId);

            var resultSet = prepareStatement.executeQuery();

            while (resultSet.next()) {
//                result.add(resultSet.getLong("id"));
                result.add(resultSet.getObject("id", Long.class)); // NULL safe

            }
        }
        return result;
    }
}

