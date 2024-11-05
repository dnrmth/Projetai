package com.projetai.quality.ticket.application;

import com.projetai.core.infra.notification.NotificationRepository;
import com.projetai.core.infra.ticket.TicketEnum.TicketStatus;
import com.projetai.core.infra.user.UserEntity;
import com.projetai.core.infra.user.developer.DeveloperEntity;
import com.projetai.core.infra.user.developer.DeveloperRepository;
import com.projetai.core.infra.user.support.SupportEntity;
import com.projetai.core.infra.user.support.SupportRepository;
import com.projetai.quality.ticket.application.dto.GetAllTicketsData;
import com.projetai.quality.ticket.domain.Ticket;
import com.projetai.core.infra.ticket.TicketEntity;
import com.projetai.core.infra.ticket.TicketRepository;
import com.projetai.quality.ticket.domain.parameters.AnalyzeTicket;
import com.projetai.quality.ticket.domain.parameters.TicketParametersDto;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class TicketService {

    private final TicketRepository ticketRepository;
    private final DeveloperRepository devRepository;
    private final NotificationRepository<UserEntity> userNotificationRepository;

    private final SupportRepository supportRepository;

    @Autowired
    public TicketService(TicketRepository ticketRepository, DeveloperRepository devRepository,
                         NotificationRepository<UserEntity> userNotificationRepository, SupportRepository supportRepository) {
        this.ticketRepository = ticketRepository;
        this.devRepository = devRepository;
        this.userNotificationRepository = userNotificationRepository;
        this.supportRepository = supportRepository;
    }

    //Return details of a Ticket searching by its id
    public TicketEntity detailTicket(Long id){
        return  ticketRepository.getReferenceById(id);
    }

    //Return a page with 10 first tickets
    public Page<GetAllTicketsData> getAllTickets(Pageable pageable){
        return ticketRepository.findByTicketStatusNot(TicketStatus.FINISHED, pageable).map(GetAllTicketsData::new);
    }

    @Transactional
    public void createTicketParameters(TicketParametersDto parameters) {
        DeveloperEntity developer = getDevForTicket(parameters);
        Ticket ticket = createTicket(parameters, developer);

        userNotificationRepository.save(ticket.makeNotificationToDev());
    }

    private DeveloperEntity getDevForTicket(TicketParametersDto parameters) {
        return new DeveloperEntity(devRepository.findById(parameters.userNotifiedId()).orElseThrow());
    }

    private Ticket createTicket(TicketParametersDto parameters, DeveloperEntity developer) {
        Ticket ticket = new Ticket(parameters, developer);
        ticketRepository.save(ticket.defineTicketParameters(parameters));
        return ticket;
    }

    public void setTicketAsFinished(long id) {
        var ticketEntity = ticketRepository.getReferenceById(id);
        var developer = devRepository.getReferenceById(ticketEntity.getDevId());

        Ticket ticket = new Ticket(ticketEntity, developer);
        ticketRepository.save(ticket.ticketFinished(ticketRepository.getReferenceById(id)));
    }


    public void analyzeTicket(AnalyzeTicket request) {
        TicketEntity ticketEntity = ticketRepository.getReferenceById(request.id());
        SupportEntity support = supportRepository.getReferenceById(request.supportId());

        Ticket ticket =  new Ticket(ticketEntity, support);
        var analyze = ticket.analizeTicket(ticketEntity, request);

        ticketRepository.save(analyze);

        if(analyze.getTicketStatus() == TicketStatus.FINISHED){
            ticket.setClientId(ticketEntity.getClientId());

            userNotificationRepository.save(ticket.makeNotificationToSupport());
            userNotificationRepository.save(ticket.makeNotificationToClient());
        }

    }

}
