package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "bus_details")
public class BusDetails {

    @Id
    @GeneratedValue
    private Long id;

    private LocalDateTime departDateTime;

    private Integer capacity;

    private String number;

    @Version
    private Long version;

    @OneToMany(mappedBy = "busDetails")
    private List<Ticket> tickets;

    public void addTicket(Ticket ticket) {
        ticket.setBusDetails(this);
        getTickets().add(ticket);
    }

}
