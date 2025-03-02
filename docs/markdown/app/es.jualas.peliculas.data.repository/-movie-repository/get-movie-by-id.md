//[app](../../../index.md)/[es.jualas.peliculas.data.repository](../index.md)/[MovieRepository](index.md)/[getMovieById](get-movie-by-id.md)

# getMovieById

[androidJvm]\
suspend fun [getMovieById](get-movie-by-id.md)(movieId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-string/index.html)): [Result](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-result/index.html)&lt;[Movie](../../es.jualas.peliculas.data.model/-movie/index.md)&gt;

Obtiene una película específica por su ID.

#### Return

[Result](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-result/index.html) que contiene el objeto [Movie](../../es.jualas.peliculas.data.model/-movie/index.md) en caso de éxito,     o una excepción en caso de error o si la película no existe.

#### Parameters

androidJvm

| | |
|---|---|
| movieId | ID único de la película a buscar. |
