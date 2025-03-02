//[app](../../../index.md)/[es.jualas.peliculas.data.repository](../index.md)/[AuthRepository](index.md)

# AuthRepository

[androidJvm]\
class [AuthRepository](index.md)

Repositorio que gestiona la autenticación de usuarios mediante Firebase Authentication.

Esta clase proporciona métodos para realizar operaciones comunes de autenticación como inicio de sesión, registro, recuperación de contraseña, cierre de sesión y obtención del usuario actual.

## Constructors

| | |
|---|---|
| [AuthRepository](-auth-repository.md) | [androidJvm]<br>constructor() |

## Functions

| Name | Summary |
|---|---|
| [getCurrentUser](get-current-user.md) | [androidJvm]<br>fun [getCurrentUser](get-current-user.md)(): [User](../../es.jualas.peliculas.data.model/-user/index.md)?<br>Obtiene el usuario actualmente autenticado. |
| [login](login.md) | [androidJvm]<br>suspend fun [login](login.md)(email: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-string/index.html), password: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-string/index.html)): [Result](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-result/index.html)&lt;[User](../../es.jualas.peliculas.data.model/-user/index.md)&gt;<br>Inicia sesión con email y contraseña. |
| [logout](logout.md) | [androidJvm]<br>fun [logout](logout.md)()<br>Cierra la sesión del usuario actual. |
| [recoverPassword](recover-password.md) | [androidJvm]<br>suspend fun [recoverPassword](recover-password.md)(email: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-string/index.html)): [Result](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-result/index.html)&lt;[Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-unit/index.html)&gt;<br>Envía un correo electrónico para restablecer la contraseña. |
| [register](register.md) | [androidJvm]<br>suspend fun [register](register.md)(email: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-string/index.html), password: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-string/index.html), name: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-string/index.html)): [Result](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-result/index.html)&lt;[User](../../es.jualas.peliculas.data.model/-user/index.md)&gt;<br>Registra un nuevo usuario con email, contraseña y nombre. |
