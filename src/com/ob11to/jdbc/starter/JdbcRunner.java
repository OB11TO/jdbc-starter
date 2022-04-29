package com.ob11to.jdbc.starter;

import com.ob11to.jdbc.starter.util.ConnectionManager;
import org.postgresql.Driver;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class JdbcRunner {
    public static void main(String[] args) throws SQLException {
        String flightId = "2 OR '' = '' " ; //плохо! НУЖНО ИСПОЛЬЗОВАТЬ PreparedStatement, чтобы не было таких ситуаций
        var ticketsByFlightId = getTicketsByFlightId(flightId);
        System.out.println(ticketsByFlightId);


    }

    private static List<Long> getTicketsByFlightId(String flightId) throws SQLException {
        List<Long> result = new ArrayList<>();

        //language=PostgresSQL
        String sql = """
                SELECT id
                FROM task26.ticket t
                WHERE t.flight_id = %s
                """.formatted(flightId);

        try (var connection = ConnectionManager.open();
             var statement = connection.createStatement()) {
            var resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
//                result.add(resultSet.getLong("id"));
                result.add(resultSet.getObject("id", Long.class)); // NULL safe

            }
        }
        return result;
    }
}
