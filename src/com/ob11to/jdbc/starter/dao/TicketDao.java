package com.ob11to.jdbc.starter.dao;


import com.ob11to.jdbc.starter.entity.Ticket;
import com.ob11to.jdbc.starter.exception.DaoException;
import com.ob11to.jdbc.starter.util.ConnectionManager;

import java.sql.*;

//Должен быть Singleton
public class TicketDao {  // не делать final, так как в H S используют Proxy
    private static TicketDao INSTANCE;
    private static final String DELETE_SQL = """   
            DELETE FROM task26.ticket
            WHERE id = ?
            """;
    private static final String SAVE_SQL = """
            INSERT INTO task26.ticket (passenger_no, passenger_name, flight_id, seat_no, cost)
            VALUES (?,?,?,?,?);
            """;

    private TicketDao() {
    }

    public static TicketDao getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new TicketDao();
        }
        return INSTANCE;
    }

    public Ticket save(Ticket ticket){
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1,ticket.getPassenger_no());
            preparedStatement.setString(2,ticket.getPassenger_name());
            preparedStatement.setInt(3,ticket.getFlight_id());
            preparedStatement.setString(4,ticket.getSeat_no());
            preparedStatement.setBigDecimal(5,ticket.getCost());

            preparedStatement.executeUpdate();

            var generatedKeys = preparedStatement.getGeneratedKeys();
            if(generatedKeys.next()){
                ticket.setId(generatedKeys.getLong("id"));
            }
            return ticket; //вернем тикет
        } catch (SQLException throwable) {
            throw new DaoException(throwable);
        }
    }


    public boolean delete(Long id) {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(DELETE_SQL)) {
            preparedStatement.setLong(1, id);

            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException throwable) {
            throw new DaoException(throwable);
        }
    }
}

