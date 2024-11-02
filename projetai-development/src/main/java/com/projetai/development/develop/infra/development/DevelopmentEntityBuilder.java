package com.projetai.development.develop.infra.development;

import com.projetai.core.infra.ticket.TicketEntity;
import com.projetai.development.develop.domain.development.status.DevelopmentStatus;
import com.projetai.development.develop.domain.development.type.DevelopmentType;

import java.time.LocalDateTime;

public class DevelopmentEntityBuilder {

    private final DevelopmentEntity developmentEntity = new DevelopmentEntity();

    public DevelopmentEntityBuilder withType(DevelopmentType developmentType) {
        developmentEntity.setType(developmentType);
        return this;
    }

    public DevelopmentEntityBuilder withStatus(DevelopmentStatus developmentStatus) {
        developmentEntity.setStatus(developmentStatus);
        return this;
    }

    public DevelopmentEntityBuilder withDeveloperId(Long developerId) {
        developmentEntity.setDeveloperId(developerId);
        return this;
    }

    public DevelopmentEntityBuilder withStartedTimeAt(LocalDateTime startedTime) {
        developmentEntity.setStartedTime(startedTime);
        return this;
    }

    public DevelopmentEntityBuilder withFinishedTimeAt(LocalDateTime finishedTime) {
        developmentEntity.setFinishedTime(finishedTime);
        return this;
    }

    public DevelopmentEntityBuilder withTicket(TicketEntity ticketEntity) {
        developmentEntity.setTicketEntity(ticketEntity);
        return this;
    }

    public DevelopmentEntity build() {
        return developmentEntity;
    }
}
