package com.ob11to.jdbc.starter.entity;

import java.math.BigDecimal;

public class Ticket {
    private Long id;
    private String passenger_no;
    private String passenger_name;
    private Integer flight_id;
    private String seat_no;
    private BigDecimal cost;

    public Ticket(Long id, String passenger_no, String passenger_name, Integer flight_id, String seat_no, BigDecimal cost) {
        this.id = id;
        this.passenger_no = passenger_no;
        this.passenger_name = passenger_name;
        this.flight_id = flight_id;
        this.seat_no = seat_no;
        this.cost = cost;
    }

    public Ticket(){} //обязательно во всех сущностях должен быть такой конструктор

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPassenger_no() {
        return passenger_no;
    }

    public void setPassenger_no(String passenger_no) {
        this.passenger_no = passenger_no;
    }

    public String getPassenger_name() {
        return passenger_name;
    }

    public void setPassenger_name(String passenger_name) {
        this.passenger_name = passenger_name;
    }

    public Integer getFlight_id() {
        return flight_id;
    }

    public void setFlight_id(Integer flight_id) {
        this.flight_id = flight_id;
    }

    public String getSeat_no() {
        return seat_no;
    }

    public void setSeat_no(String seat_no) {
        this.seat_no = seat_no;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    @Override
    public String toString() {
        return "Ticket{" + "id=" + id +
                ", passenger_no='" + passenger_no + '\'' +
                ", passenger_name='" + passenger_name + '\'' +
                ", flight_id=" + flight_id +
                ", seat_no='" + seat_no + '\'' +
                ", cost=" + cost +
                '}';
    }
}
