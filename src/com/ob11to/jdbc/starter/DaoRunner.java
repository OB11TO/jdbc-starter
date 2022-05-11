package com.ob11to.jdbc.starter;

import com.ob11to.jdbc.starter.dao.TicketDao;
import com.ob11to.jdbc.starter.dto.TicketFilter;
import com.ob11to.jdbc.starter.entity.Ticket;

import java.math.BigDecimal;
import java.util.Optional;

public class DaoRunner {
    public static void main(String[] args) {
       //deleteTest(56L); //saveTest(); // updateTestCost() //findByAllTest(); //findByAllParamTest();

        var ticket = TicketDao.getInstance().findById(5L);
        System.out.println(ticket);

    }

    private static void findByAllParamTest() {
        var ticketDao = TicketDao.getInstance();
        TicketFilter filter = new TicketFilter(10,10, null, null);
        var byAll = ticketDao.findByAll(filter);
        for(Ticket ticket : byAll){
            System.out.println(ticket);
        }
    }

    private static void findByAllTest() {
        var ticketDao = TicketDao.getInstance();
        var byAll = ticketDao.findByAll();
        for(Ticket ticket : byAll){
            System.out.println(ticket);
        }
    }

    private static void updateTestCost() {
        var ticketDao = TicketDao.getInstance();
        var byId = ticketDao.findById(2L);
        System.out.println(byId);

        byId.ifPresent(ticket -> {
            ticket.setCost(BigDecimal.valueOf(188.88));
            ticketDao.update(ticket);
        });
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
       // ticket.setFlight(3);
        ticket.setSeat_no("B3");
        ticket.setCost(BigDecimal.TEN);

        var saveTicket = ticketDao.save(ticket);

        System.out.println(saveTicket);
    }
}
