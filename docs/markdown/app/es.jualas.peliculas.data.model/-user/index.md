//[app](../../../index.md)/[es.jualas.peliculas.data.model](../index.md)/[User](index.md)

# User

[androidJvm]\
data class [User](index.md)(val uid: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-string/index.html) = &quot;&quot;, val email: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-string/index.html) = &quot;&quot;, val name: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-string/index.html) = &quot;&quot;, val favoriteMovies: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin.collections/-list/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-string/index.html)&gt; = listOf())

Modelo de datos que representa un usuario en la aplicación.

Esta clase contiene la información básica de un usuario autenticado, incluyendo su identificador único, correo electrónico, nombre y una lista de películas favoritas.

## Constructors

| | |
|---|---|
| [User](-user.md) | [androidJvm]<br>constructor(uid: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-string/index.html) = &quot;&quot;, email: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-string/index.html) = &quot;&quot;, name: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-string/index.html) = &quot;&quot;, favoriteMovies: [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin.collections/-list/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-string/index.html)&gt; = listOf()) |

## Properties

| Name | Summary |
|---|---|
| [email](email.md) | [androidJvm]<br>val [email](email.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-string/index.html)<br>Dirección de correo electrónico del usuario. |
| [favoriteMovies](favorite-movies.md) | [androidJvm]<br>val [favoriteMovies](favorite-movies.md): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin.collections/-list/index.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-string/index.html)&gt;<br>Lista de identificadores de películas marcadas como favoritas por el usuario. |
| [name](name.md) | [androidJvm]<br>val [name](name.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-string/index.html)<br>Nombre del usuario. |
| [uid](uid.md) | [androidJvm]<br>val [uid](uid.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-string/index.html)<br>Identificador único del usuario proporcionado por Firebase Authentication. |
