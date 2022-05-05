package com.ob11to.jdbc.starter;

import com.ob11to.jdbc.starter.dao.TicketDao;
import com.ob11to.jdbc.starter.entity.Ticket;

import java.math.BigDecimal;

public class DaoRunner {
    public static void main(String[] args) {
       deleteTest(56L);

    }

    private static void deleteTest(Long id) {
        var ticketDao = TicketDao.getInstance();
        var deleteTicket = ticketDao.delete( id);

        System.out.println(deleteTicket);
    }

    private static void saveTest() {
        var ticketDao = TicketDao.getInstance();
        var ticket = new Ticket();
        ticket.setPassenger_no("1234567");
        ticket.setPassenger_name("Test");
        ticket.setFlight_id(3);
        ticket.setSeat_no("B3");
        ticket.setCost(BigDecimal.TEN);

        var saveTicket = ticketDao.save(ticket);

        System.out.println(saveTicket);
    }
}
