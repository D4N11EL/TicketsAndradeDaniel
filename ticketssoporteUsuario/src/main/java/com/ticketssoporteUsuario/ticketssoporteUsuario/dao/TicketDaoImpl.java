package com.ticketssoporteUsuario.ticketssoporteUsuario.dao;

import com.ticketssoporteUsuario.ticketssoporteUsuario.entity.Ticket;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class TicketDaoImpl  implements ITicketDao{

    @PersistenceContext
    private EntityManager em;

    @Transactional(readOnly = true)
    @Override
    public List<Ticket> findAll() {
        return  em.createQuery("from Ticket").getResultList();
    }
    @Transactional
    @Override
    public void save(Ticket ticket) {
        if (ticket.getId()!=null && ticket.getId()>0){
            em.merge(ticket);
        }
        else{
            em.persist(ticket);
        }

    }
    @Transactional(readOnly = true)
    @Override
    public Ticket findOne(Long id) {
        return  em.find(Ticket.class,id);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        em.remove(findOne(id));
    }
}
