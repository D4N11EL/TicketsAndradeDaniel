package com.ticketssoporteUsuario.ticketssoporteUsuario.service;

import com.ticketssoporteUsuario.ticketssoporteUsuario.dao.ITicketDao;
import com.ticketssoporteUsuario.ticketssoporteUsuario.entity.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class TicketServiceImpl implements ITicketService{

    @Autowired
    private ITicketDao ticketDao;
    @Override
    public List<Ticket> findAll() {
        return ticketDao.findAll();
    }

    @Override
    public void save(Ticket ticket) {
ticketDao.save(ticket);
    }

    @Override
    public Ticket findOne(Long id) {
        return ticketDao.findOne(id);
    }

    @Override
    public void delete(Long id) {
ticketDao.delete(id);
    }
}
