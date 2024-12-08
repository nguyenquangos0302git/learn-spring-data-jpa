package com.example.demo.service;

import com.example.demo.entity.BusDetails;

public interface IBookingService {

    public void bookTicket1() throws InterruptedException;

    public void bookTicket2() throws InterruptedException;

    void getBus() throws InterruptedException;

}
