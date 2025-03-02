//[app](../../../index.md)/[es.jualas.peliculas.data.model](../index.md)/[Movie](index.md)

# Movie

[androidJvm]\
data class [Movie](index.md)(val id: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-string/index.html) = &quot;&quot;, val title: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-string/index.html) = &quot;&quot;, val description: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-string/index.html) = &quot;&quot;, val imageUrl: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-string/index.html) = &quot;&quot;, val releaseYear: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-int/index.html) = 0, val rating: [Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-float/index.html) = 0.0f, val isFavorite: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-boolean/index.html) = false)

Modelo de datos que representa una película en la aplicación.

Esta clase contiene toda la información relevante sobre una película, incluyendo sus metadatos básicos como título, descripción, año de lanzamiento, así como información relacionada con la interfaz de usuario como la URL de la imagen y el estado de favorito para el usuario actual.

## Constructors

| | |
|---|---|
| [Movie](-movie.md) | [androidJvm]<br>constructor(id: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-string/index.html) = &quot;&quot;, title: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-string/index.html) = &quot;&quot;, description: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-string/index.html) = &quot;&quot;, imageUrl: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-string/index.html) = &quot;&quot;, releaseYear: [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-int/index.html) = 0, rating: [Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-float/index.html) = 0.0f, isFavorite: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-boolean/index.html) = false) |

## Properties

| Name | Summary |
|---|---|
| [description](description.md) | [androidJvm]<br>val [description](description.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-string/index.html)<br>Sinopsis o descripción de la trama de la película. |
| [id](id.md) | [androidJvm]<br>val [id](id.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-string/index.html)<br>Identificador único de la película. |
| [imageUrl](image-url.md) | [androidJvm]<br>val [imageUrl](image-url.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-string/index.html)<br>URL de la imagen del póster o carátula de la película. |
| [isFavorite](is-favorite.md) | [androidJvm]<br>val [isFavorite](is-favorite.md): [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-boolean/index.html) = false<br>Indica si la película está marcada como favorita por el usuario actual. |
| [rating](rating.md) | [androidJvm]<br>val [rating](rating.md): [Float](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-float/index.html) = 0.0f<br>Puntuación de la película en una escala de 0 a 10. |
| [releaseYear](release-year.md) | [androidJvm]<br>val [releaseYear](release-year.md): [Int](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-int/index.html) = 0<br>Año de lanzamiento de la película. |
| [title](title.md) | [androidJvm]<br>val [title](title.md): [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-string/index.html)<br>Título de la película. |
