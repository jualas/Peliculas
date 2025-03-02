//[app](../../../index.md)/[es.jualas.peliculas.data.repository](../index.md)/[MovieRepository](index.md)

# MovieRepository

[androidJvm]\
class [MovieRepository](index.md)

Repositorio que gestiona las operaciones relacionadas con películas en Firebase Firestore.

Esta clase proporciona métodos para obtener, buscar y gestionar películas y favoritos almacenados en la base de datos de Firestore.

## Constructors

| | |
|---|---|
| [MovieRepository](-movie-repository.md) | [androidJvm]<br>constructor() |

## Functions

| Name | Summary |
|---|---|
| [getFavoriteMovies](get-favorite-movies.md) | [androidJvm]<br>suspend fun [getFavoriteMovies](get-favorite-movies.md)(userId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-string/index.html)): [Result](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-result/index.html)&lt;[List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin.collections/-list/index.html)&lt;[Movie](../../es.jualas.peliculas.data.model/-movie/index.md)&gt;&gt;<br>Obtiene todas las películas marcadas como favoritas por un usuario específico. |
| [getMovieById](get-movie-by-id.md) | [androidJvm]<br>suspend fun [getMovieById](get-movie-by-id.md)(movieId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-string/index.html)): [Result](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-result/index.html)&lt;[Movie](../../es.jualas.peliculas.data.model/-movie/index.md)&gt;<br>Obtiene una película específica por su ID. |
| [getMovies](get-movies.md) | [androidJvm]<br>suspend fun [getMovies](get-movies.md)(): [Result](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-result/index.html)&lt;[List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin.collections/-list/index.html)&lt;[Movie](../../es.jualas.peliculas.data.model/-movie/index.md)&gt;&gt;<br>Obtiene todas las películas disponibles en la base de datos. |
| [initializeMoviesDatabase](initialize-movies-database.md) | [androidJvm]<br>suspend fun [initializeMoviesDatabase](initialize-movies-database.md)(): [Result](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-result/index.html)&lt;[Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-boolean/index.html)&gt;<br>Inicializa la base de datos con una colección predefinida de películas populares. |
| [searchMovies](search-movies.md) | [androidJvm]<br>suspend fun [searchMovies](search-movies.md)(query: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-string/index.html)): [List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin.collections/-list/index.html)&lt;[Movie](../../es.jualas.peliculas.data.model/-movie/index.md)&gt;<br>Busca películas que coincidan con un término de búsqueda en el título o descripción. |
| [toggleFavorite](toggle-favorite.md) | [androidJvm]<br>suspend fun [toggleFavorite](toggle-favorite.md)(userId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-string/index.html), movieId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-string/index.html), isFavorite: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-boolean/index.html)): [Result](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-result/index.html)&lt;[Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-boolean/index.html)&gt;<br>Añade o elimina una película de los favoritos de un usuario. |
