package ru.itis.tjmoney.util;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import ru.itis.tjmoney.models.Reminder;

import java.util.Properties;

public final class EmailSenderUtil {
    public static void sendReminder(Reminder reminder, String to) {
        String from = "tjmoney@mail.ru";
        String host = "smtp.mail.ru";
        String smtpPort = "465";

        Properties props = new Properties();
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", smtpPort);
        props.put("mail.smtp.ssl.enable", "true");
        props.put("mail.smtp.auth", "true");

        Session session = Session.getInstance(
                props,
                new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(from, "nPV7d5RRdsDybKGt80pb");
                    }
                }
        );

        session.setDebug(true);

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.addRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject("Напоминание");
            message.setText("Ничего не забыл? Даже если нет, я напомню. %s; Название: %s".formatted(reminder.getTitle(), reminder.getMessage()));
            Transport.send(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}