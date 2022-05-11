package com.ob11to.jdbc.starter.dao;

import com.ob11to.jdbc.starter.entity.Flight;
import com.ob11to.jdbc.starter.entity.Ticket;
import com.ob11to.jdbc.starter.exception.DaoException;
import com.ob11to.jdbc.starter.util.ConnectionManager;


import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class FlightDao implements Dao<Long,Flight>{
    private static final String FIND_BY_ID_SQL = """
            SELECT flight_repository.task26.flight.id,
            flight_repository.task26.flight.flight_no,
            flight_repository.task26.flight.departure_date,
            flight_repository.task26.flight.departure_airport_code,
            flight_repository.task26.flight.arrival_date,
            flight_repository.task26.flight.arrival_airport_code,
            flight_repository.task26.flight.aircraft_id,
            flight_repository.task26.flight.status
            FROM flight_repository.task26.flight
            WHERE id = ?
            """;
    private static  FlightDao INSTANCE;

    private FlightDao(){
    }

    public static FlightDao getInstance(){
        if(INSTANCE == null){
            INSTANCE = new FlightDao();
        }
        return INSTANCE;
    }


    @Override
    public boolean delete(Long id) {
        return false;
    }

    @Override
    public Ticket save(Flight ticket) {
        return null;
    }

    @Override
    public void update(Flight ticket) {

    }

    @Override
    public List<Flight> findByAll() {
        return null;
    }

    @Override
    public Optional<Flight> findById(Long id) {
        try (var connection = ConnectionManager.get();
             var preparedStatement = connection.prepareStatement(FIND_BY_ID_SQL)) {

            preparedStatement.setLong(1, id);
            var resultSet = preparedStatement.executeQuery();
            Flight flight = null;
            if(resultSet.next()){
                flight = new Flight(
                        resultSet.getInt("id"),
                        resultSet.getString("flight_no"),
                        resultSet.getTimestamp("departure_date").toLocalDateTime(),
                        resultSet.getString("departure_airport_code"),
                        resultSet.getTimestamp("arrival_date").toLocalDateTime(),
                        resultSet.getString("arrival_airport_code"),
                        resultSet.getInt("aircraft_id"),
                        resultSet.getString("status")
                );
            }
            return Optional.ofNullable(flight);

        } catch (SQLException throwable) {
            throw new DaoException(throwable);
        }

    }
}
