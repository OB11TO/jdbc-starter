package com.ob11to.jdbc.starter.dto;

import lombok.Value;

@Value
public class TicketFilter {
    int limit;
    int offset;
    String passenger_name;
    String seat_no;
}
