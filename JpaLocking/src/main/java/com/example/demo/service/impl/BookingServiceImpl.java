package com.example.demo.service.impl;

import com.example.demo.entity.BusDetails;
import com.example.demo.entity.Ticket;
import com.example.demo.repository.IBusRepository;
import com.example.demo.repository.ITicketRepository;
import com.example.demo.service.IBookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class BookingServiceImpl implements IBookingService {

    @Autowired
    private IBusRepository iBusRepository;

    @Autowired
    private ITicketRepository iTicketRepository;

    private void saveTicket(String firstName, String lastName, String gender, BusDetails busDetails) {
        if (busDetails.getCapacity() <= busDetails.getTickets().size()) {
            throw new RuntimeException("Seat not available");
        }

        Ticket ticket = new Ticket();
        ticket.setFirstName(firstName);
        ticket.setLastName(lastName);
        ticket.setGender(gender);

        busDetails.addTicket(ticket);

        iTicketRepository.save(ticket);
    }

    @Override
    @Transactional
    public void bookTicket1() throws InterruptedException {
        Optional<BusDetails> busDetails = iBusRepository.findWithLockingById(1L);

        if (busDetails.isPresent()) {
            saveTicket("John", "Allen", "Male", busDetails.get());
        }

        Thread.sleep(1000);
    }

    @Override
    @Transactional
    public void bookTicket2() throws InterruptedException {
        Optional<BusDetails> busDetails = iBusRepository.findWithLockingById(1L);

        if (busDetails.isPresent()) {
            saveTicket("Maria", "Allen", "Female", busDetails.get());
        }

        Thread.sleep(1000);
    }

    @Override
    @Transactional(readOnly = true)
    public void getBus() throws InterruptedException {
        BusDetails busDetails = iBusRepository.findById(1L).get();
        System.out.println(busDetails.getId());
        Thread.sleep(1000);
    }

}
