package com.ob11to.jdbc.starter;

import com.ob11to.jdbc.starter.util.ConnectionManager;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TransactionRunner {
    public static void main(String[] args) throws SQLException {
        long flightId = 9;
        var deleteFlightSql = "DELETE FROM task26.flight WHERE id = ?";
        var deleteTicketsSql = "DELETE FROM task26.ticket WHERE flight_id = ?";

        Connection connection = null;
        PreparedStatement deleteFlightStatement = null;
        PreparedStatement deleteTicketsStatement = null;

        try {
            connection = ConnectionManager.get();
            deleteFlightStatement = connection.prepareStatement(deleteFlightSql);
            deleteTicketsStatement = connection.prepareStatement(deleteTicketsSql);

            connection.setAutoCommit(false); //убрали auto commit mode

            deleteFlightStatement.setLong(1, flightId);
            deleteTicketsStatement.setLong(1, flightId);

            deleteTicketsStatement.executeUpdate();

            if (true) {
                throw new RuntimeException("Ooops, gg, я обронил исключение:/ F");
            }

            deleteFlightStatement.executeUpdate();

            connection.commit(); //commit транзакции

        } catch (Exception e) {
            if (connection != null) {
                connection.rollback();
            }
            throw e; // пробрасываем исключение дальше
        } finally {
            if (connection != null) {
                connection.close();
            }
            if (deleteFlightStatement != null) {
                deleteFlightStatement.close();
            }
            if (deleteTicketsStatement != null) {
                deleteTicketsStatement.close();
            }
        }
    }
}
