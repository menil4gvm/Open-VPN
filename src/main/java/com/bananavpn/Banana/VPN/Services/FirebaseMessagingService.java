package com.bananavpn.Banana.VPN.Services;

import org.springframework.stereotype.Service;

import com.bananavpn.Banana.VPN.Model.Note;
import com.google.firebase.messaging.*;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;

@Service
public class FirebaseMessagingService {
	 private final FirebaseMessaging firebaseMessaging;

	    public FirebaseMessagingService(FirebaseMessaging firebaseMessaging) {
	        this.firebaseMessaging = firebaseMessaging;
	    }


	    public String sendNotification(Note note, String token) throws FirebaseMessagingException {

	        Notification notification = Notification
	                .builder()
	                .setTitle(note.getsubject())
	                .setBody(note.getcontent())
	                .build();

	        Message message = Message
	                .builder()
	                .setToken(token)
	                .setNotification(notification)
	                .putAllData(note.getdata())
	                .build();

	        return firebaseMessaging.send(message);
	    }
}
