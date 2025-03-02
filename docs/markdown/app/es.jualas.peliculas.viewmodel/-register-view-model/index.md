//[app](../../../index.md)/[es.jualas.peliculas.viewmodel](../index.md)/[RegisterViewModel](index.md)

# RegisterViewModel

[androidJvm]\
class [RegisterViewModel](index.md) : [ViewModel](https://developer.android.com/reference/kotlin/androidx/lifecycle/ViewModel.html)

ViewModel responsable de gestionar la lógica de registro de usuarios.

Esta clase maneja el proceso de registro de nuevos usuarios en la aplicación, incluyendo la validación de datos, comunicación con el repositorio de autenticación y la notificación de resultados a la UI.

## Constructors

| | |
|---|---|
| [RegisterViewModel](-register-view-model.md) | [androidJvm]<br>constructor() |

## Properties

| Name | Summary |
|---|---|
| [isLoading](is-loading.md) | [androidJvm]<br>val [isLoading](is-loading.md): [LiveData](https://developer.android.com/reference/kotlin/androidx/lifecycle/LiveData.html)&lt;[Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-boolean/index.html)&gt;<br>LiveData público que expone el estado de carga durante el proceso de registro. |
| [registrationResult](registration-result.md) | [androidJvm]<br>val [registrationResult](registration-result.md): [LiveData](https://developer.android.com/reference/kotlin/androidx/lifecycle/LiveData.html)&lt;[Result](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-result/index.html)&lt;[User](../../es.jualas.peliculas.data.model/-user/index.md)&gt;&gt;<br>LiveData público que expone el resultado del proceso de registro. |

## Functions

| Name | Summary |
|---|---|
| [addCloseable](../../es.jualas.peliculas.ui.search/-search-view-model/index.md#383812252%2FFunctions%2F-912451524) | [androidJvm]<br>open fun [addCloseable](../../es.jualas.peliculas.ui.search/-search-view-model/index.md#383812252%2FFunctions%2F-912451524)(closeable: [AutoCloseable](https://developer.android.com/reference/kotlin/java/lang/AutoCloseable.html))<br>fun [addCloseable](../../es.jualas.peliculas.ui.search/-search-view-model/index.md#1722490497%2FFunctions%2F-912451524)(key: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-string/index.html), closeable: [AutoCloseable](https://developer.android.com/reference/kotlin/java/lang/AutoCloseable.html)) |
| [getCloseable](../../es.jualas.peliculas.ui.search/-search-view-model/index.md#1102255800%2FFunctions%2F-912451524) | [androidJvm]<br>fun &lt;[T](../../es.jualas.peliculas.ui.search/-search-view-model/index.md#1102255800%2FFunctions%2F-912451524) : [AutoCloseable](https://developer.android.com/reference/kotlin/java/lang/AutoCloseable.html)&gt; [getCloseable](../../es.jualas.peliculas.ui.search/-search-view-model/index.md#1102255800%2FFunctions%2F-912451524)(key: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-string/index.html)): [T](../../es.jualas.peliculas.ui.search/-search-view-model/index.md#1102255800%2FFunctions%2F-912451524)? |
| [register](register.md) | [androidJvm]<br>fun [register](register.md)(email: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-string/index.html), password: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-string/index.html), birthDate: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-string/index.html))<br>Inicia el proceso de registro de un nuevo usuario. |
