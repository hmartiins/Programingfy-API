package com.example.logusletterapi.service.lambda;

import io.github.cdimascio.dotenv.Dotenv;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.ses.SesClient;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeBodyPart;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Properties;

import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.services.ses.model.SendRawEmailRequest;
import software.amazon.awssdk.services.ses.model.RawMessage;
import software.amazon.awssdk.services.ses.model.SesException;

public class SESService {
    public static void sendMessage(String message) {
        Dotenv dotenv = Dotenv.configure().load();

        AwsCredentialsProvider credentialsProvider = new AwsCredentialsProvider() {
            @Override
            public AwsCredentials resolveCredentials() {
                return new AwsCredentials() {
                    @Override
                    public String accessKeyId() {
                        return dotenv.get("AWS_ACCESS_KEY");
                    }

                    @Override
                    public String secretAccessKey() {
                        return dotenv.get("AWS_SECRET_KEY");
                    }
                };
            }
        };

        Region region = Region.SA_EAST_1;
        SesClient client = SesClient.builder()
                .credentialsProvider(credentialsProvider)
                .region(region)
                .build();

        String bodyText = "Olá este é um email";

        String bodyHTML = "<html>"
                + "<head></head>"
                + "<body>"
                + "<h1>LogusLetter!</h1>"
                + "<p>Envio de Teste</p>"
                + "</body>"
                + "</html>";

        try {
            send(
                client, "hmartins224@gmail.com",
                "hmartiins224@gmail.com",
                "LogusLetter",
                bodyText,
                bodyHTML
            );
            client.close();

            System.out.println("Email enviado!");

        } catch (IOException | MessagingException e) {
            e.getStackTrace();
        }
    }

    public static void send(
        SesClient client,
        String sender,
        String recipient,
        String subject,
        String bodyText,
        String bodyHTML
    ) throws AddressException, MessagingException, IOException {

        Session session = Session.getDefaultInstance(new Properties());
        MimeMessage message = new MimeMessage(session);

        message.setSubject(subject, "UTF-8");
        message.setFrom(new InternetAddress(sender));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));

        MimeBodyPart textPart = new MimeBodyPart();
        textPart.setContent(bodyText, "text/plain; charset=UTF-8");

        MimeBodyPart htmlPart = new MimeBodyPart();
        htmlPart.setContent(bodyHTML, "text/html; charset=UTF-8");

        MimeMultipart msgBody = new MimeMultipart();
        msgBody.addBodyPart(textPart);
        msgBody.addBodyPart(htmlPart);

        message.setContent(msgBody);

        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            message.writeTo(outputStream);
            ByteBuffer buf = ByteBuffer.wrap(outputStream.toByteArray());

            byte[] arr = new byte[buf.remaining()];
            buf.get(arr);

            SdkBytes data = SdkBytes.fromByteArray(arr);
            RawMessage rawMessage = RawMessage.builder()
                    .data(data)
                    .build();

            SendRawEmailRequest rawEmailRequest = SendRawEmailRequest.builder()
                    .rawMessage(rawMessage)
                    .build();

            client.sendRawEmail(rawEmailRequest);

        } catch (SesException e) {
            System.err.println(e.awsErrorDetails().errorMessage());
        }
    }
}