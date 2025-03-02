//[app](../../../index.md)/[es.jualas.peliculas.viewmodel](../index.md)/[RecoverViewModel](index.md)/[recoverPassword](recover-password.md)

# recoverPassword

[androidJvm]\
fun [recoverPassword](recover-password.md)(email: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-string/index.html))

Inicia el proceso de recuperación de contraseña para el correo electrónico proporcionado.

Este método envía una solicitud a Firebase Authentication para enviar un correo electrónico de restablecimiento de contraseña al usuario. Actualiza los estados de carga, éxito y error según corresponda.

#### Parameters

androidJvm

| | |
|---|---|
| email | Dirección de correo electrónico del usuario que desea recuperar su contraseña. |
