package com.futi.app

import com.futi.app.domain.User
import com.futi.app.utils.Mailing
import com.google.gson.Gson
import org.junit.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import java.util.*

@SpringBootTest
class AppApplicationTests {


	@Test
	fun mailing() {

		val subject = "Bienvenido a Futi"

		//val body = Mailing.getBody(Mailing.TYPE_WELCOME, "Leonardo", "newpass")
		//val body = Mailing.getBody(Mailing.TYPE_CHANGE_PASSWORD, "Leonardo", "newpass")
		val body = Mailing.getBody(Mailing.TYPE_WELCOME, "Leonardo")

		val mail = "lecasme.598@gmail.com"

		body?.let {
			Mailing.sendMail(subject, it, mail)
		}

	}



	@Test
	fun contextLoads() {
	}

	@Test
	fun passwordEncoder() {
		println("PASSWORD 0: ${BCryptPasswordEncoder().encode("1")}")
		println("PASSWORD 1: ${BCryptPasswordEncoder().encode("12345")}")
		println("PASSWORD 2: ${BCryptPasswordEncoder().encode("")}")
	}

	@Test
	fun userBase64() {

		val user = User().apply {
			email = "sara@gmail.com"
			birthday = "1970/01/31"
			firstName = ""
			lastName = ""
		}

		val json = Gson().toJson(user)
		val b64 = Base64.getEncoder().encodeToString(json.toByteArray())
		val d64 = Base64.getDecoder().decode(b64)

		println("Json: $json")
		println("User: $b64")
		println("Decode: ${String(d64)}")

	}


}
