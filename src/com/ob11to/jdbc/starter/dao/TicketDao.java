package com.ob11to.jdbc.starter.dao;


import com.ob11to.jdbc.starter.entity.Ticket;
import com.ob11to.jdbc.starter.exception.DaoException;
import com.ob11to.jdbc.starter.util.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    private static final String UPDATE_SQL = """
            UPDATE task26.ticket
            SET passenger_no = ?,
                passenger_name = ?,
                flight_id = ?,
                seat_no = ?,
                cost = ?
            WHERE id = ?;
            """;
    private static final String FIND_BY_ALL = """
            SELECT id,
            passenger_no,
            passenger_name,
            flight_id,
            seat_no,
            cost
            FROM task26.ticket
            """;
    private static final String FIND_BY_ID = FIND_BY_ALL + "WHERE id = ?";

    private TicketDao() {
    }

    public static TicketDao getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new TicketDao();
        }
        return INSTANCE;
    }

    public Optional<Ticket> findById(Long id) {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(FIND_BY_ID)) {
            preparedStatement.setLong(1, id);

            var resultSet = preparedStatement.executeQuery();
            Ticket ticket = null;

            if (resultSet.next()) {
                ticket = buildTicket(resultSet);
            }

            return Optional.ofNullable(ticket);
        } catch (SQLException throwable) {
            throw new RuntimeException(throwable);
        }
    }

    public List<Ticket> findByAll(){
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(FIND_BY_ALL)) {

            List<Ticket> allTicket = new ArrayList<>();
            var resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                allTicket.add(buildTicket(resultSet));
            }

            return allTicket;
        } catch (SQLException throwable) {
            throw new DaoException(throwable);
        }
    }

    public void update(Ticket ticket) {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(UPDATE_SQL)) {
            preparedStatement.setString(1, ticket.getPassenger_no());
            preparedStatement.setString(2, ticket.getPassenger_name());
            preparedStatement.setInt(3, ticket.getFlight_id());
            preparedStatement.setString(4, ticket.getSeat_no());
            preparedStatement.setBigDecimal(5, ticket.getCost());
            preparedStatement.setLong(6, ticket.getId());

            preparedStatement.executeUpdate();

        } catch (SQLException throwable) {
            throw new RuntimeException(throwable);
        }
    }

    public Ticket save(Ticket ticket) {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, ticket.getPassenger_no());
            preparedStatement.setString(2, ticket.getPassenger_name());
            preparedStatement.setInt(3, ticket.getFlight_id());
            preparedStatement.setString(4, ticket.getSeat_no());
            preparedStatement.setBigDecimal(5, ticket.getCost());

            preparedStatement.executeUpdate();

            var generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
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

    private Ticket buildTicket(ResultSet resultSet) throws SQLException {
        return new Ticket(
                resultSet.getLong("id"),
                resultSet.getString("passenger_no"),
                resultSet.getString("passenger_name"),
                resultSet.getInt("flight_id"),
                resultSet.getString("seat_no"),
                resultSet.getBigDecimal("cost"));
    }
}

