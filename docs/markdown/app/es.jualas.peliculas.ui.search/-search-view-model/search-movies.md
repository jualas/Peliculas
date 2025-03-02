//[app](../../../index.md)/[es.jualas.peliculas.ui.search](../index.md)/[SearchViewModel](index.md)/[searchMovies](search-movies.md)

# searchMovies

[androidJvm]\
fun [searchMovies](search-movies.md)(query: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-string/index.html))

Realiza una búsqueda de películas en Firestore basada en el texto proporcionado.

La búsqueda se realiza tanto por título (películas cuyo título comienza con la consulta) como por género (películas que contienen el género especificado).

#### Parameters

androidJvm

| | |
|---|---|
| query | Texto de búsqueda introducido por el usuario |
