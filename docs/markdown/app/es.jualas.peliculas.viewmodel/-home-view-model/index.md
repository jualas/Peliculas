//[app](../../../index.md)/[es.jualas.peliculas.viewmodel](../index.md)/[HomeViewModel](index.md)

# HomeViewModel

[androidJvm]\
class [HomeViewModel](index.md) : [ViewModel](https://developer.android.com/reference/kotlin/androidx/lifecycle/ViewModel.html)

ViewModel para la pantalla principal de la aplicación.

Esta clase se encarga de gestionar los datos relacionados con las películas, incluyendo la carga desde Firestore, la actualización del estado de favoritos, y la gestión de estados de carga y errores.

## Constructors

| | |
|---|---|
| [HomeViewModel](-home-view-model.md) | [androidJvm]<br>constructor() |

## Properties

| Name | Summary |
|---|---|
| [error](error.md) | [androidJvm]<br>val [error](error.md): [LiveData](https://developer.android.com/reference/kotlin/androidx/lifecycle/LiveData.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-string/index.html)&gt;<br>Mensaje de error observable expuesto a la UI. Permite a la UI mostrar mensajes de error al usuario. |
| [isLoading](is-loading.md) | [androidJvm]<br>val [isLoading](is-loading.md): [LiveData](https://developer.android.com/reference/kotlin/androidx/lifecycle/LiveData.html)&lt;[Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-boolean/index.html)&gt;<br>Estado de carga observable expuesto a la UI. Permite a la UI mostrar indicadores de carga cuando es necesario. |
| [movies](movies.md) | [androidJvm]<br>val [movies](movies.md): [LiveData](https://developer.android.com/reference/kotlin/androidx/lifecycle/LiveData.html)&lt;[List](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin.collections/-list/index.html)&lt;[Movie](../../es.jualas.peliculas.data.model/-movie/index.md)&gt;&gt;<br>Lista observable de películas expuesta a la UI. Este LiveData es inmutable para garantizar que solo se modifique desde el ViewModel. |

## Functions

| Name | Summary |
|---|---|
| [addCloseable](../../es.jualas.peliculas.ui.search/-search-view-model/index.md#383812252%2FFunctions%2F-912451524) | [androidJvm]<br>open fun [addCloseable](../../es.jualas.peliculas.ui.search/-search-view-model/index.md#383812252%2FFunctions%2F-912451524)(closeable: [AutoCloseable](https://developer.android.com/reference/kotlin/java/lang/AutoCloseable.html))<br>fun [addCloseable](../../es.jualas.peliculas.ui.search/-search-view-model/index.md#1722490497%2FFunctions%2F-912451524)(key: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-string/index.html), closeable: [AutoCloseable](https://developer.android.com/reference/kotlin/java/lang/AutoCloseable.html)) |
| [getCloseable](../../es.jualas.peliculas.ui.search/-search-view-model/index.md#1102255800%2FFunctions%2F-912451524) | [androidJvm]<br>fun &lt;[T](../../es.jualas.peliculas.ui.search/-search-view-model/index.md#1102255800%2FFunctions%2F-912451524) : [AutoCloseable](https://developer.android.com/reference/kotlin/java/lang/AutoCloseable.html)&gt; [getCloseable](../../es.jualas.peliculas.ui.search/-search-view-model/index.md#1102255800%2FFunctions%2F-912451524)(key: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-string/index.html)): [T](../../es.jualas.peliculas.ui.search/-search-view-model/index.md#1102255800%2FFunctions%2F-912451524)? |
| [loadMovies](load-movies.md) | [androidJvm]<br>fun [loadMovies](load-movies.md)()<br>Carga la lista de películas desde Firestore. |
| [toggleFavorite](toggle-favorite.md) | [androidJvm]<br>fun [toggleFavorite](toggle-favorite.md)(movie: [Movie](../../es.jualas.peliculas.data.model/-movie/index.md))<br>Cambia el estado de favorito de una película. |
