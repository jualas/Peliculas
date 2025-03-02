//[app](../../../index.md)/[es.jualas.peliculas.data.repository](../index.md)/[MovieRepository](index.md)/[getMovies](get-movies.md)

# getMovies

[androidJvm]\
suspend fun [getMovies](get-movies.md)(): [Result](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-result/index.html)&lt;[List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin.collections/-list/index.html)&lt;[Movie](../../es.jualas.peliculas.data.model/-movie/index.md)&gt;&gt;

Obtiene todas las películas disponibles en la base de datos.

#### Return

[Result](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-result/index.html) que contiene una lista de objetos [Movie](../../es.jualas.peliculas.data.model/-movie/index.md) en caso de éxito,     o una excepción en caso de error.
