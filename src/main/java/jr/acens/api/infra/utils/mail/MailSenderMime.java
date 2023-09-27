package jr.acens.api.infra.utils.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import jr.acens.api.infra.exceptions.EmailSendingException;

@Component
public class MailSenderMime {
    @Value("${spring.mail.username}")
    private String fromMail;

    @Autowired
    private JavaMailSender javaMailSender;

    public String sendMail(MailDTO data) {
        try {
            var mimeMessage = javaMailSender.createMimeMessage();
            var mimeMessageHelper = new MimeMessageHelper(mimeMessage, false);

            mimeMessageHelper.setFrom(fromMail);
            mimeMessageHelper.setTo(data.to());
            mimeMessageHelper.setSubject(data.subject());
            mimeMessageHelper.setText(data.Body());

            javaMailSender.send(mimeMessage);

            return "Email enviado com sucesso";
        } catch (Exception e) {
            throw new EmailSendingException("Erro ao enviar email", e);
        }
    }
}