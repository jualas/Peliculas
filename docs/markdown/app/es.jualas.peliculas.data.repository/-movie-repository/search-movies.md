//[app](../../../index.md)/[es.jualas.peliculas.data.repository](../index.md)/[MovieRepository](index.md)/[searchMovies](search-movies.md)

# searchMovies

[androidJvm]\
suspend fun [searchMovies](search-movies.md)(query: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-string/index.html)): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin.collections/-list/index.html)&lt;[Movie](../../es.jualas.peliculas.data.model/-movie/index.md)&gt;

Busca películas que coincidan con un término de búsqueda en el título o descripción.

Este método realiza una búsqueda insensible a mayúsculas/minúsculas en los campos de título y descripción de las películas.

#### Return

Lista de objetos [Movie](../../es.jualas.peliculas.data.model/-movie/index.md) que coinciden con la búsqueda.

#### Parameters

androidJvm

| | |
|---|---|
| query | Término de búsqueda. |

#### Throws

| | |
|---|---|
| [Exception](https://developer.android.com/reference/kotlin/java/lang/Exception.html) | Si ocurre un error durante la búsqueda. |
