package com.projetai.development.develop.domain.development;

import com.projetai.core.infra.notification.NotificationEntity;
import com.projetai.core.infra.notification.NotificationEntityBuilder;
import com.projetai.core.infra.ticket.TicketEntity;
import com.projetai.core.infra.ticket.TicketEnum.TicketStatus;
import com.projetai.core.infra.user.developer.DeveloperEntity;
import com.projetai.development.develop.domain.development.status.DevelopmentStatus;
import com.projetai.development.develop.infra.development.DevelopmentEntity;
import com.projetai.development.develop.infra.development.DevelopmentEntityBuilder;
import com.projetai.development.refinement.infra.user.techLead.TechLeadEntity;

import java.time.LocalDateTime;

public class Development implements DevelopmentInterface {

    private Long id;
    private DevelopmentStatus status;
    private Long developerId;

    private TicketEntity ticketEntity;

    private LocalDateTime startedTime;

    private LocalDateTime finishedTime;

    private Long techLeadId;

    private Integer estimatedHours;

    public Development(Long id,
                       Long developerId,
                       DevelopmentStatus status,
                       LocalDateTime startedTime,
                       TicketEntity ticketEntity,
                       Integer estimatedHours) {
        this.id = id;
        this.status = status;
        this.developerId = developerId;
        this.ticketEntity = ticketEntity;
        this.startedTime = startedTime;
        this.estimatedHours = estimatedHours;
    }

    public Development(Long developerId,
                       DevelopmentStatus status,
                       LocalDateTime startedTime,
                       TicketEntity ticketEntity,
                       Long techLeadId) {
        this.status = status;
        this.developerId = developerId;
        this.ticketEntity = ticketEntity;
        this.startedTime = startedTime;
        this.techLeadId = techLeadId;
    }

    public Development(Long id,
                       DevelopmentStatus status,
                       Long developerId,
                       TicketEntity ticketEntity,
                       LocalDateTime startedTime,
                       LocalDateTime finishedTime,
                       Long techLeadId,
                       Integer estimatedHours) {
        this.id = id;
        this.status = status;
        this.developerId = developerId;
        this.ticketEntity = ticketEntity;
        this.startedTime = startedTime;
        this.finishedTime = finishedTime;
        this.techLeadId = techLeadId;
        this.estimatedHours = estimatedHours;
    }

    public Development(Long developerId,
                       TicketEntity ticketEntity,
                       Integer estimatedHours,
                       DevelopmentStatus status) {
        this.developerId = developerId;
        this.ticketEntity = ticketEntity;
        this.estimatedHours = estimatedHours;
        this.status = status;
    }

    @Override
    public DevelopmentEntity startDevelopment() {
        return new DevelopmentEntityBuilder()
                .withId(id)
                .withDeveloperId(developerId)
                .withStatus(status)
                .withTicket(ticketEntity)
                .withStartedTimeAt(startedTime)
                .withEstimatedHours(estimatedHours)
                .build();
    }

    @Override
    public DevelopmentEntity makeAdjustments() {
        return new DevelopmentEntityBuilder()
                .withDeveloperId(developerId)
                .withStatus(status)
                .withTicket(ticketEntity)
                .withStartedTimeAt(startedTime)
                .build();
    }

    @Override
    public DevelopmentEntity completeDevelopment() {
        return new DevelopmentEntityBuilder()
                .withId(id)
                .withDeveloperId(developerId)
                .withStatus(status)
                .withTicket(ticketEntity)
                .withStartedTimeAt(startedTime)
                .withFinishedTimeAt(finishedTime)
                .withEstimatedHours(estimatedHours)
                .build();
    }

    @Override
    public DevelopmentEntity addEstimatedHours() {
        return new DevelopmentEntityBuilder()
                .withDeveloperId(developerId)
                .withTicket(ticketEntity)
                .withEstimatedHours(estimatedHours)
                .build();
    }

    @Override
    public NotificationEntity<DeveloperEntity> makeNotificationToDev(String necessaryAdjustments, DeveloperEntity developer) {
        String message = "You need to make some adjustments: " + necessaryAdjustments;

        return new NotificationEntityBuilder<DeveloperEntity>()
                .withMessage(message)
                .withTitle("Necessary adjustments!")
                .withType(TicketStatus.NECESSARY_ADJUSTMENTS.name())
                .withUserEntity(developer)
                .build();
    }

    @Override
    public NotificationEntity<TechLeadEntity> makeNotificationToTechLead() {
        String message = "Development is done. Code is ready to code review";

        return new NotificationEntityBuilder<TechLeadEntity>()
                .withMessage(message)
                .withTitle("Task completed")
                .withUserId(techLeadId)
                .withType(ticketEntity.getTicketType().name())
                .build();
    }

    @Override
    public NotificationEntity<TechLeadEntity> makeNotificationToClient() {
        String message = "Required hours of development on this task: " + estimatedHours;

        return new NotificationEntityBuilder<TechLeadEntity>()
                .withMessage(message)
                .withTitle("Hours estimated")
                .withUserId(techLeadId)
                .build();
    }
}
