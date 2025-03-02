//[app](../../../index.md)/[es.jualas.peliculas.data.repository](../index.md)/[MovieRepository](index.md)/[getFavoriteMovies](get-favorite-movies.md)

# getFavoriteMovies

[androidJvm]\
suspend fun [getFavoriteMovies](get-favorite-movies.md)(userId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-string/index.html)): [Result](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-result/index.html)&lt;[List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin.collections/-list/index.html)&lt;[Movie](../../es.jualas.peliculas.data.model/-movie/index.md)&gt;&gt;

Obtiene todas las películas marcadas como favoritas por un usuario específico.

#### Return

[Result](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-result/index.html) que contiene una lista de objetos [Movie](../../es.jualas.peliculas.data.model/-movie/index.md) marcados como favoritos,     o una lista vacía si no hay favoritos, o una excepción en caso de error.

#### Parameters

androidJvm

| | |
|---|---|
| userId | ID único del usuario cuyos favoritos se quieren obtener. |
