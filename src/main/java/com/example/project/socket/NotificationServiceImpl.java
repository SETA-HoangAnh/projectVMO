package com.example.project.socket;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class NotificationServiceImpl {

    private final NotifyRepository notifyRepository;

    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    public NotificationServiceImpl(NotifyRepository notifyRepository, SimpMessagingTemplate messagingTemplate) {
        this.notifyRepository = notifyRepository;
        this.messagingTemplate = messagingTemplate;
    }

    public void sendGlobalNotification() {
        ResponseNotify message = new ResponseNotify("Global Notification");

        messagingTemplate.convertAndSend("/topic/global-notifications", message);
    }

    public void sendPrivateNotification(final String userId) {
        ResponseNotify message = new ResponseNotify("Private Notification");

        messagingTemplate.convertAndSendToUser(userId,"/topic/private-notifications", message);
    }
}
