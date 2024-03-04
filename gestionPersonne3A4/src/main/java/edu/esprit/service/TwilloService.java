package edu.esprit.service;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
public class TwilloService {
    private static final String ACCOUNT_SID = "AC2f3af2fedc80caeb5929143299643122";
    private static final String AUTH_TOKEN = "303527d7f1927f676c2eecb6640bb708";
    private static final String FROM_NUMBER = "+18149850317";

    public static void sendSms(String toNumber, String messageText) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

        Message message = Message.creator(
                        new PhoneNumber(toNumber),
                        new PhoneNumber(FROM_NUMBER),
                        messageText)
                .create();

        System.out.println("Message SID: " + message.getSid());
    }
}