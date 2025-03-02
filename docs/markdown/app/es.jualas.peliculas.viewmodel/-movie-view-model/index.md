//[app](../../../index.md)/[es.jualas.peliculas.viewmodel](../index.md)/[MovieViewModel](index.md)

# MovieViewModel

[androidJvm]\
class [MovieViewModel](index.md) : [ViewModel](https://developer.android.com/reference/kotlin/androidx/lifecycle/ViewModel.html)

ViewModel que gestiona los datos relacionados con películas en la aplicación.

Esta clase se encarga de:

- 
   Cargar y mantener listas de películas (generales y favoritas)
- 
   Gestionar el estado de carga y errores
- 
   Proporcionar acceso a los datos de películas a través de LiveData
- 
   Manejar la selección de películas individuales

## Constructors

| | |
|---|---|
| [MovieViewModel](-movie-view-model.md) | [androidJvm]<br>constructor() |

## Properties

| Name | Summary |
|---|---|
| [error](error.md) | [androidJvm]<br>val [error](error.md): [LiveData](https://developer.android.com/reference/kotlin/androidx/lifecycle/LiveData.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-string/index.html)?&gt;<br>LiveData pública expuesta para observar mensajes de error |
| [favoriteMovies](favorite-movies.md) | [androidJvm]<br>val [favoriteMovies](favorite-movies.md): [LiveData](https://developer.android.com/reference/kotlin/androidx/lifecycle/LiveData.html)&lt;[List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin.collections/-list/index.html)&lt;[Movie](../../es.jualas.peliculas.data.model/-movie/index.md)&gt;&gt;<br>LiveData pública expuesta para observar la lista de películas favoritas |
| [loading](loading.md) | [androidJvm]<br>val [loading](loading.md): [LiveData](https://developer.android.com/reference/kotlin/androidx/lifecycle/LiveData.html)&lt;[Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-boolean/index.html)&gt;<br>LiveData pública expuesta para observar el estado de carga |
| [movies](movies.md) | [androidJvm]<br>val [movies](movies.md): [LiveData](https://developer.android.com/reference/kotlin/androidx/lifecycle/LiveData.html)&lt;[List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin.collections/-list/index.html)&lt;[Movie](../../es.jualas.peliculas.data.model/-movie/index.md)&gt;&gt;<br>LiveData pública expuesta para observar la lista de películas |
| [selectedMovie](selected-movie.md) | [androidJvm]<br>val [selectedMovie](selected-movie.md): [LiveData](https://developer.android.com/reference/kotlin/androidx/lifecycle/LiveData.html)&lt;[Movie](../../es.jualas.peliculas.data.model/-movie/index.md)&gt;<br>LiveData pública expuesta para observar la película seleccionada |
| [selectedMovieId](selected-movie-id.md) | [androidJvm]<br>val [selectedMovieId](selected-movie-id.md): [LiveData](https://developer.android.com/reference/kotlin/androidx/lifecycle/LiveData.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-string/index.html)&gt;<br>LiveData pública expuesta para observar el ID de la película seleccionada |

## Functions

| Name | Summary |
|---|---|
| [addCloseable](../../es.jualas.peliculas.ui.search/-search-view-model/index.md#383812252%2FFunctions%2F-912451524) | [androidJvm]<br>open fun [addCloseable](../../es.jualas.peliculas.ui.search/-search-view-model/index.md#383812252%2FFunctions%2F-912451524)(closeable: [AutoCloseable](https://developer.android.com/reference/kotlin/java/lang/AutoCloseable.html))<br>fun [addCloseable](../../es.jualas.peliculas.ui.search/-search-view-model/index.md#1722490497%2FFunctions%2F-912451524)(key: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-string/index.html), closeable: [AutoCloseable](https://developer.android.com/reference/kotlin/java/lang/AutoCloseable.html)) |
| [clearError](clear-error.md) | [androidJvm]<br>fun [clearError](clear-error.md)()<br>Limpia cualquier mensaje de error actual. |
| [getCloseable](../../es.jualas.peliculas.ui.search/-search-view-model/index.md#1102255800%2FFunctions%2F-912451524) | [androidJvm]<br>fun &lt;[T](../../es.jualas.peliculas.ui.search/-search-view-model/index.md#1102255800%2FFunctions%2F-912451524) : [AutoCloseable](https://developer.android.com/reference/kotlin/java/lang/AutoCloseable.html)&gt; [getCloseable](../../es.jualas.peliculas.ui.search/-search-view-model/index.md#1102255800%2FFunctions%2F-912451524)(key: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-string/index.html)): [T](../../es.jualas.peliculas.ui.search/-search-view-model/index.md#1102255800%2FFunctions%2F-912451524)? |
| [getMovieById](get-movie-by-id.md) | [androidJvm]<br>fun [getMovieById](get-movie-by-id.md)(movieId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-string/index.html))<br>Obtiene los detalles de una película específica por su ID. |
| [loadFavoriteMovies](load-favorite-movies.md) | [androidJvm]<br>fun [loadFavoriteMovies](load-favorite-movies.md)(userId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-string/index.html))<br>Carga la lista de películas favoritas del usuario desde el repositorio. |
| [loadMovies](load-movies.md) | [androidJvm]<br>fun [loadMovies](load-movies.md)()<br>Carga la lista de películas desde el repositorio. |
| [selectMovie](select-movie.md) | [androidJvm]<br>fun [selectMovie](select-movie.md)(movieId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-string/index.html))<br>Establece el ID de la película seleccionada actualmente. |
| [toggleFavorite](toggle-favorite.md) | [androidJvm]<br>fun [toggleFavorite](toggle-favorite.md)(userId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-string/index.html), movie: [Movie](../../es.jualas.peliculas.data.model/-movie/index.md))<br>Cambia el estado de favorito de una película para un usuario específico. |
