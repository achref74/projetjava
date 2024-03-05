package edu.esprit.services;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
public class SmsSender {

    public static void sendSms(String message) {
        try {
            Twilio.init(TwilioConfig.ACCOUNT_SID, TwilioConfig.AUTH_TOKEN);

            Message.creator(
                    new PhoneNumber(TwilioConfig.RECIEVER),
                    new PhoneNumber(TwilioConfig.TWILIO_PHONE_NUMBER),
                    message
            ).create();

            System.out.println("SMS sent successfully!");
        } catch (Exception e) {
            System.err.println("Error sending SMS: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
