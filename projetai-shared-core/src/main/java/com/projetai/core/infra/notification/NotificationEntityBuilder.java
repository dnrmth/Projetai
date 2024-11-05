package com.projetai.core.infra.notification;

import com.projetai.core.infra.user.UserEntity;
import com.projetai.core.infra.user.UserInterface;

public class NotificationEntityBuilder<U extends UserEntity & UserInterface> {

    private final NotificationEntity<U> notification = new NotificationEntity<>();

    public NotificationEntityBuilder<U> withTitle(String title) {
        notification.setTitle(title);
        return this;
    }

    public NotificationEntityBuilder<U> withMessage(String message) {
        notification.setMessage(message);
        return this;
    }

    public NotificationEntityBuilder<U> withType(String type) {
        notification.setType(type);
        return this;
    }

    public NotificationEntityBuilder<U> withUserEntity(U user) {
        notification.setUser(user);
        return this;
    }

    public NotificationEntityBuilder<U> withUserId(Long userId) {
        notification.setUserId(userId);
        return this;
    }

    public NotificationEntity<U> build() {
        return notification;
    }

}
