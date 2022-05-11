package com.ob11to.jdbc.starter.entity;

import lombok.Value;

import java.time.LocalDateTime;


@Value
public class Flight {
    int id;
    String flight_no;
    LocalDateTime departure_date;
    String departure_airport_code;
    LocalDateTime arrival_date;
    String arrival_airport_code;
    Integer aircraft_id;
    String status;

}
