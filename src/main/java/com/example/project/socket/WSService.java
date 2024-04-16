package com.example.project.socket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class WSService {

    private final SimpMessagingTemplate messagingTemplate;
    private final NotificationServiceImpl notificationService;

    @Autowired
    public WSService(SimpMessagingTemplate messagingTemplate, NotificationServiceImpl notificationService) {
        this.messagingTemplate = messagingTemplate;
        this.notificationService = notificationService;
    }


    public void notifyFrontend(final Notify notify) {
        ResponseNotify response = new ResponseNotify(notify.getNotifyContent());
        notificationService.sendGlobalNotification();
        messagingTemplate.convertAndSend("/topic/notify", response);
    }



    public void notifyUser(final Notify notify) {
        ResponseNotify response = new ResponseNotify(notify.getNotifyContent());
        for(String x : notify.getUserNotifyLst()){
            notificationService.sendPrivateNotification(x);
            messagingTemplate.convertAndSendToUser(x, "/topic/private-notify", response);
        }
    }
}
