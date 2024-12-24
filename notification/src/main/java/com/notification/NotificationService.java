package com.notification;


import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    public void envoyerNotification(Notification notification) {
        System.out.println("Notification envoyée : " + notification.getMessage());
        // (email later !!! tfkri)
    }
}
