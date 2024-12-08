package com.example.demo.controller;

import com.example.demo.entity.BusDetails;
import com.example.demo.entity.Ticket;
import com.example.demo.repository.IBusRepository;
import com.example.demo.service.IBookingService;
import org.apache.commons.lang3.function.FailableRunnable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@RestController
@RequestMapping("/api")
public class LockingDemoController {

    @Autowired
    private IBookingService iBookingService;

    @Autowired
    private IBusRepository iBusRepository;

    @GetMapping("/bookTicket")
    public void bookTicket() {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        executorService.execute(run(iBookingService::bookTicket1));
        executorService.execute(run(iBookingService::bookTicket2));
        executorService.shutdown();
    }

    @GetMapping("/addBus")
    @Transactional
    public void addBus(@RequestParam String number, @RequestParam int capacity) {
        BusDetails busDetails = new BusDetails();
        busDetails.setCapacity(capacity);
        busDetails.setNumber(number);
        busDetails.setDepartDateTime(LocalDateTime.now());

        List<Ticket> ticketList = new ArrayList<>();
        Ticket ticket = new Ticket();
        ticket.setGender("1");
        ticketList.add(ticket);
        for (Ticket ticket1 : ticketList) {
            ticket1.setBusDetails(busDetails);
        }

        BusDetails busDetails1 = iBusRepository.save(busDetails);

        busDetails1.setVersion(3L);
        iBusRepository.save(busDetails1);

        iBusRepository.findById(1L);

//        List<Ticket> ticketList2 = new ArrayList<>();
//        Ticket ticket2 = new Ticket();
//        ticket2.setGender("1");
//        ticketList.add(ticket2);
//        Ticket ticket3 = new Ticket();
//        ticket2.setGender("1");
//        ticketList.add(ticket3);
//        for (Ticket ticket1 : ticketList2) {
//            ticket1.setBusDetails(busDetails1);
//        }
//
//        busDetails1.setTickets(ticketList2);

//        iBusRepository.findById(busDetails1.getId());

    }

    @GetMapping("/getBus")
    public void getBus() throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        executorService.execute(run(iBookingService::getBus));
        executorService.shutdown();
    }

    private Runnable run(FailableRunnable<Exception> runnable) {
        return () -> {
            try {
                runnable.run();
            } catch (Exception e) {
                e.printStackTrace();
            }
        };
    }

}
