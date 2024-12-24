package com.notification;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class NotificationConsumer {

   /* @Autowired
    private NotificationService notificationService;

    @KafkaListener(topics = "commande-topic", groupId = "notification-group")
    public void consommerMessage(String message) {
        Notification notification = new Notification();
        notification.setCommandeId(Long.parseLong(message.split(":")[0]));
        notification.setMessage("Nouvelle commande créée avec ID " + notification.getCommandeId());
        notificationService.envoyerNotification(notification);
    }*/

    @Autowired
    private CommandeFeignClient commandeFeignClient;

    @Autowired
    private NotificationService notificationService;

    @KafkaListener(topics = "commande-topic", groupId = "notification-group")
    public void consommerMessage(String message) {
        Long commandeId = Long.parseLong(message);

        CommandeFeignClient.CommandeResponse commande = commandeFeignClient.getCommandeById(commandeId);

        Notification notification = new Notification();
        notification.setCommandeId(commandeId);
        notification.setMessage("Votre commande de produit ID " + commande.getProduitId() + " est " + commande.getStatus());

        notificationService.envoyerNotification(notification);
    }

}

