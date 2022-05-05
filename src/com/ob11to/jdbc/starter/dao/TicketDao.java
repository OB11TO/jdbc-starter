package com.ob11to.jdbc.starter.dao;


//Должен быть Singleton
public class TicketDao {  // не делать final, так как в H S используют Proxy
    private static TicketDao INSTANCE;
    
    private TicketDao(){}
    
    public static TicketDao getInstance(){
        if(INSTANCE == null){
            INSTANCE = new TicketDao();
        }
        return INSTANCE;
    }
}
