package com.ob11to.jdbc.starter.dao;

import com.ob11to.jdbc.starter.dto.TicketFilter;
import com.ob11to.jdbc.starter.entity.Ticket;

import java.util.List;
import java.util.Optional;

public interface Dao<K,E> {

    boolean delete(K id);

    Ticket save(E ticket);

    void update(E ticket);

    List<E> findByAll();

    Optional<E> findById(K id);


}
