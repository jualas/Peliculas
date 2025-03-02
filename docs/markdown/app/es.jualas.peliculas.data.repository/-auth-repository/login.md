//[app](../../../index.md)/[es.jualas.peliculas.data.repository](../index.md)/[AuthRepository](index.md)/[login](login.md)

# login

[androidJvm]\
suspend fun [login](login.md)(email: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-string/index.html), password: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-string/index.html)): [Result](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-result/index.html)&lt;[User](../../es.jualas.peliculas.data.model/-user/index.md)&gt;

Inicia sesión con email y contraseña.

#### Return

[Result](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-result/index.html) que contiene un objeto [User](../../es.jualas.peliculas.data.model/-user/index.md) si la autenticación es exitosa,     o un error encapsulado si falla.

#### Parameters

androidJvm

| | |
|---|---|
| email | Dirección de correo electrónico del usuario. |
| password | Contraseña del usuario. |
