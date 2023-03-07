package com.futi.app.utils

import org.springframework.core.io.ClassPathResource
import org.springframework.core.io.Resource
import org.springframework.util.FileCopyUtils
import java.io.IOException
import java.io.InputStream
import java.nio.charset.StandardCharsets
import java.util.*
import javax.mail.Message
import javax.mail.PasswordAuthentication
import javax.mail.Session
import javax.mail.Transport
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage


object Mailing {

    fun sendMail(subject: String, body: String, mail: String){

        val props = Properties()
        props["mail.smtp.auth"] = "true"
        props["mail.smtp.starttls.enable"] = "true"
        props["mail.smtp.host"] = "smtp.gmail.com"
        props["mail.smtp.port"] = "587"

        val session = Session.getInstance(props, object : javax.mail.Authenticator() {
            override fun getPasswordAuthentication(): PasswordAuthentication {
                return PasswordAuthentication(EMAIL, PASSWORD)
            }
        })

        val msg = MimeMessage(session)

        msg.setFrom(InternetAddress(PASSWORD, false))
        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mail))
        msg.subject = subject
        msg.sentDate = Date()

        msg.setContent(body, "text/html")

        Transport.send(msg)

    }

    fun getBody(type: Int, vararg params: String) : String? {

        var template: String? = null
        when(type){
            TYPE_CHANGE_PASSWORD, TYPE_VALIDATION_CODE -> {
                template = getTemplate(type)
                template = template.replace("[param1]", params[0],true)
                template = template.replace("[param2]", params[1],true)
            }
            TYPE_WELCOME ->{
                template = getTemplate(type)
                template = template.replace("[param1]", params[0],true)
            }
        }

        return template

    }

    fun generateCode() = String.format("%04d", Random().nextInt(10000))



    private fun getTemplate(type: Int) : String {

        var path : String = Constants.EMPTY

        when(type){
            TYPE_CHANGE_PASSWORD -> path = "templates/change_password.html"
            TYPE_VALIDATION_CODE -> path = "templates/validation_code.html"
            TYPE_WELCOME -> path = "templates/welcome.html"
        }

        val resource: Resource = ClassPathResource(path)
        val inputStream: InputStream = resource.inputStream

        try {
            val input = FileCopyUtils.copyToByteArray(inputStream)
            return String(input, StandardCharsets.UTF_8)
        } catch (e: IOException) {
            throw IOException()
        }

    }

    const val EMAIL = "apppichanga@gmail.com"
    const val PASSWORD = "pichangueros"

    // Mail Type
    const val TYPE_CHANGE_PASSWORD = 1
    const val TYPE_VALIDATION_CODE = 2
    const val TYPE_WELCOME = 3

}