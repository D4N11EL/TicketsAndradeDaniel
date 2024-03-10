package com.ticketssoporteUsuario.ticketssoporteUsuario.service;

import com.ticketssoporteUsuario.ticketssoporteUsuario.entity.Ticket;

import java.util.List;

public interface ITicketService {
    public List<Ticket> findAll();

    public void save (Ticket ticket);

    public Ticket findOne(Long id);
    public  void delete(Long id);
}
