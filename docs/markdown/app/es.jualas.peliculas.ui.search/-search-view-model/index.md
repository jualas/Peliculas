//[app](../../../index.md)/[es.jualas.peliculas.ui.search](../index.md)/[SearchViewModel](index.md)

# SearchViewModel

[androidJvm]\
class [SearchViewModel](index.md) : [ViewModel](https://developer.android.com/reference/kotlin/androidx/lifecycle/ViewModel.html)

ViewModel encargado de gestionar la búsqueda de películas en Firestore.

Esta clase maneja las operaciones de búsqueda de películas por título y género, así como el estado de carga y los posibles errores durante el proceso.

## Constructors

| | |
|---|---|
| [SearchViewModel](-search-view-model.md) | [androidJvm]<br>constructor() |

## Properties

| Name | Summary |
|---|---|
| [error](error.md) | [androidJvm]<br>val [error](error.md): [LiveData](https://developer.android.com/reference/kotlin/androidx/lifecycle/LiveData.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-string/index.html)?&gt;<br>LiveData público que expone los mensajes de error a los observadores. Este valor es inmutable desde fuera del ViewModel. |
| [isLoading](is-loading.md) | [androidJvm]<br>val [isLoading](is-loading.md): [LiveData](https://developer.android.com/reference/kotlin/androidx/lifecycle/LiveData.html)&lt;[Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-boolean/index.html)&gt;<br>LiveData público que expone el estado de carga a los observadores. Este valor es inmutable desde fuera del ViewModel. |
| [searchResults](search-results.md) | [androidJvm]<br>val [searchResults](search-results.md): [LiveData](https://developer.android.com/reference/kotlin/androidx/lifecycle/LiveData.html)&lt;[List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin.collections/-list/index.html)&lt;[Movie](../../es.jualas.peliculas.data.model/-movie/index.md)&gt;&gt;<br>LiveData público que expone los resultados de búsqueda a los observadores. Este valor es inmutable desde fuera del ViewModel. |

## Functions

| Name | Summary |
|---|---|
| [addCloseable](index.md#383812252%2FFunctions%2F-912451524) | [androidJvm]<br>open fun [addCloseable](index.md#383812252%2FFunctions%2F-912451524)(closeable: [AutoCloseable](https://developer.android.com/reference/kotlin/java/lang/AutoCloseable.html))<br>fun [addCloseable](index.md#1722490497%2FFunctions%2F-912451524)(key: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-string/index.html), closeable: [AutoCloseable](https://developer.android.com/reference/kotlin/java/lang/AutoCloseable.html)) |
| [clearError](clear-error.md) | [androidJvm]<br>fun [clearError](clear-error.md)() |
| [getCloseable](index.md#1102255800%2FFunctions%2F-912451524) | [androidJvm]<br>fun &lt;[T](index.md#1102255800%2FFunctions%2F-912451524) : [AutoCloseable](https://developer.android.com/reference/kotlin/java/lang/AutoCloseable.html)&gt; [getCloseable](index.md#1102255800%2FFunctions%2F-912451524)(key: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-string/index.html)): [T](index.md#1102255800%2FFunctions%2F-912451524)? |
| [searchMovies](search-movies.md) | [androidJvm]<br>fun [searchMovies](search-movies.md)(query: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-string/index.html))<br>Realiza una búsqueda de películas en Firestore basada en el texto proporcionado. |
| [toggleFavorite](toggle-favorite.md) | [androidJvm]<br>fun [toggleFavorite](toggle-favorite.md)(movie: [Movie](../../es.jualas.peliculas.data.model/-movie/index.md)) |
