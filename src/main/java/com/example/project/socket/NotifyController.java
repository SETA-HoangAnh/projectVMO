package com.example.project.socket;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;

import java.security.Principal;


@RestController
@RequestMapping("${apiPrefix}/notify")
public class NotifyController {

    private final NotifyServiceImpl notifyServiceImpl;

    private final NotificationServiceImpl notificationServiceImpl;

    public NotifyController(NotifyServiceImpl notifyServiceImpl, NotificationServiceImpl notificationServiceImpl) {
        this.notifyServiceImpl = notifyServiceImpl;
        this.notificationServiceImpl = notificationServiceImpl;
    }


    @MessageMapping("/messages")
    @SendTo("/topic/notify")
    public ResponseNotify getMessage(final Notify notify) throws InterruptedException {
        notificationServiceImpl.sendGlobalNotification();
        return new ResponseNotify(HtmlUtils.htmlEscape(notify.getNotifyContent()));
    }


    @MessageMapping("/private-messages")
    @SendToUser("/topic/private-notify")
    public ResponseNotify getPrivateMessage(final Notify notify,
                                            final Principal principal){
        notificationServiceImpl.sendPrivateNotification(principal.getName());
        return new ResponseNotify(HtmlUtils.htmlEscape(
                "Sending private message to user " + principal.getName() + ": "
                        + notify.getNotifyContent())
        );
    }


    @GetMapping
    public ResponseEntity<?> findAllNotify(@RequestParam(value = "userId") Long userId, @RequestParam(value = "language") String language) {
        return new ResponseEntity<>(notifyServiceImpl.findAll(userId, language), HttpStatus.OK);
    }


    @GetMapping("{id}")
    public ResponseEntity<NotifyDTO> findNotifyById(@PathVariable("id") Long notifyId) {
        return new ResponseEntity<>(notifyServiceImpl.findById(notifyId), HttpStatus.OK);
    }
}
