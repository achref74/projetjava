package edu.esprit.services;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
public class SmsSenderReclamation {

    public static void sendSms(String message) {
        try {
            Twilio.init(TwilioConfigReclamation.ACCOUNT_SID, TwilioConfigReclamation.AUTH_TOKEN);

            Message.creator(
                    new PhoneNumber("whatsapp:+21655756823"),
                    new PhoneNumber("whatsapp:+14155238886"),
                    message
            ).create();

            System.out.println("whatsapp msg sent successfully!");
        } catch (Exception e) {
            System.err.println("Error sending SMS: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
