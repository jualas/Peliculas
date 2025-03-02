//[app](../../../index.md)/[es.jualas.peliculas.data.repository](../index.md)/[AuthRepository](index.md)/[register](register.md)

# register

[androidJvm]\
suspend fun [register](register.md)(email: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-string/index.html), password: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-string/index.html), name: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-string/index.html)): [Result](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-result/index.html)&lt;[User](../../es.jualas.peliculas.data.model/-user/index.md)&gt;

Registra un nuevo usuario con email, contraseña y nombre.

#### Return

[Result](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-result/index.html) que contiene un objeto [User](../../es.jualas.peliculas.data.model/-user/index.md) si el registro es exitoso,     o un error encapsulado si falla.

#### Parameters

androidJvm

| | |
|---|---|
| email | Dirección de correo electrónico para el nuevo usuario. |
| password | Contraseña para el nuevo usuario. |
| name | Nombre del usuario. |
