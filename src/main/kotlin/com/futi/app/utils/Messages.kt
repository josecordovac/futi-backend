package com.futi.app.utils

import org.omg.PortableInterceptor.SUCCESSFUL

object Messages {

    /**
     * Email
     * **/

    const val RECOVER_PASSWORD_SUBJECT =  "Recuperar contraseña"
    const val WELCOME_SUBJECT =  "Bienvenido a Futi"
    const val VERIFIED_CODE_SUBJECT =  "Futi - Código de Validación"
    const val SEND_EMAIL =  "Correo enviado"

    /**
     * Commons
     */

    const val FAILURE_USER_NOT_FOUND = "Usuario no encontrado"
    const val FAILURE_EMAIL_NOT_FOUND = "El correo electrónico que has ingresado no está registrado"



    /**
     * Login
     */

    const val FAILURE_BAD_CREDENTIALS = "Nombre de usuario o contraseña incorrectos"
    const val FAILURE_USER_ALREADY_REGISTERED = "Correo ya registrado, use el cambio de contraseña."
    const val FAILURE_ADD_FROM_FAVOURITE = "No se pudo marcar"
    const val FAILURE_REMOVE_FROM_FAVOURITE = "No se pudieron eliminar los favoritos."
    const val FAILURE_VERIFICATION = "No se pudo validar el correo electrónico. Vuelve a intentarlo más tarde."
    const val FAILURE_VERIFICATION_EXPIRED = "El código de acceso ha caducado. Vuelve a intentarlo."
    const val FAILURE_VERIFICATION_NOT_EQUAL = "El código de acceso es erroneo"
    const val FAILURE_ALREADY_VERIFIED = "El usuario ya ha sido verificado"
    const val FAILURE_NO_MAIL = "El usuario conectado no tiene correo electrónico"
    const val FAILURE_CHANGE_PASSWORD = "No se pudo cambiar contraseña, inténtelo más tarde"
    const val SUCCESSFUL_SEND_EMAIL = "Correo enviado con éxito"
    const val SUCCESSFUL_VERIFIED_CODE = "Verificado"
    const val FAILURE_VERIFIED_CODE = "Código incorrecto"


    /**
     * Configuracion
     */

    const val FAILURE_CLIENT_CONFIG = "No se pudo obtener la configuración del usuario"


}
