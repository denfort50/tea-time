package ru.dkalchenko.teatime.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.FileSystemResource
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.stereotype.Service
import ru.dkalchenko.teatime.model.EmailDetails
import java.io.File
import java.util.*
import javax.mail.MessagingException

@Service
class EmailServiceImpl : EmailService {
    @Autowired
    var javaMailSender: JavaMailSender? = null

    @Value("\${spring.mail.username}")
    private val sender: String? = null
    override fun sendSimpleMail(details: EmailDetails): String {
        return try {
            val mailMessage = SimpleMailMessage()
            mailMessage.from = sender
            mailMessage.setTo(details.recipient)
            mailMessage.text = details.msgBody
            mailMessage.subject = details.subject
            javaMailSender!!.send(mailMessage)
            "Mail Sent Successfully..."
        } catch (e: Exception) {
            "Error while Sending Mail"
        }
    }

    override fun sendMailWithAttachment(details: EmailDetails): String {
        val mimeMessage = javaMailSender!!.createMimeMessage()
        val mimeMessageHelper: MimeMessageHelper
        return try {
            mimeMessageHelper = MimeMessageHelper(mimeMessage, true)
            mimeMessageHelper.setFrom(sender)
            mimeMessageHelper.setTo(details.recipient)
            mimeMessageHelper.setText(details.msgBody)
            mimeMessageHelper.setSubject(details.subject)
            val file = FileSystemResource(File(details.attachment))
            mimeMessageHelper.addAttachment(Objects.requireNonNull(file.filename), file)
            javaMailSender!!.send(mimeMessage)
            "Mail sent Successfully"
        } catch (e: MessagingException) {
            "Error while sending mail!!!"
        }
    }
}
