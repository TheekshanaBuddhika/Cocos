package lk.ijse.util;


import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lk.ijse.Model.UserModel;

import java.util.Properties;

public class Service {

    public static String senMail(){

        Properties properties = new Properties();
        properties.put("mail.smtp.auth",true);
        properties.put("mail.smtp.starttls.enable",true);
        properties.put("mail.smtp.port","587");
        properties.put("mail.smtp.host","smtp.gmail.com");


          String user = "dedeuoilmill0525@gmail.com";
          String password = "clqcfxxpqffpezyf";

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(user,password);
            }
        });

        int otp = (int)(Math.random()*90000)+10000;

        String msg = ""
                + "<div style='border:1px solid #e2e2e2; padding:20px'>"
                +"<h3>"
                +"We received a request to get OTP Code for Google Account theekshanaroxx0525@gmail.com."
                +"<br>"
                +"Your OTP Code is , "
                +"<br>"
                +"</h3>"
                +"<p>"
                +"<center>"
                +"<h1>"
                +"<b>"
                +otp
                +"</b>"
                +"</h1>"
                +"</center>"
                +"</p>"
                + "Use this OTP to gain Access ."
                +"</div>";

        try {
            Message message = new MimeMessage(session);
            message.setRecipient(Message.RecipientType.TO,new InternetAddress(UserModel.getEmail()));
            message.setFrom(new InternetAddress(user));
            message.setSubject(" OTP Verification");
            message.setContent(msg,"text/html");

            Transport.send(message);

        }catch (Exception e){
                e.printStackTrace();
        }

        return String.valueOf(otp);

    }

}
