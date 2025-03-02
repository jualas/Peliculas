//[app](../../../index.md)/[es.jualas.peliculas.data.repository](../index.md)/[AuthRepository](index.md)/[recoverPassword](recover-password.md)

# recoverPassword

[androidJvm]\
suspend fun [recoverPassword](recover-password.md)(email: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-string/index.html)): [Result](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-result/index.html)&lt;[Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-unit/index.html)&gt;

Envía un correo electrónico para restablecer la contraseña.

#### Return

[Result](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-result/index.html) que contiene [Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-unit/index.html) si el correo se envía correctamente,     o un error encapsulado si falla.

#### Parameters

androidJvm

| | |
|---|---|
| email | Dirección de correo electrónico del usuario que desea restablecer su contraseña. |
