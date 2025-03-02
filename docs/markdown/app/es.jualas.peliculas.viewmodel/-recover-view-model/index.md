//[app](../../../index.md)/[es.jualas.peliculas.viewmodel](../index.md)/[RecoverViewModel](index.md)

# RecoverViewModel

[androidJvm]\
class [RecoverViewModel](index.md) : [ViewModel](https://developer.android.com/reference/kotlin/androidx/lifecycle/ViewModel.html)

ViewModel responsable de gestionar la lógica de recuperación de contraseñas.

Esta clase maneja el proceso de envío de correos electrónicos para restablecer contraseñas olvidadas, utilizando Firebase Authentication como proveedor de autenticación. Proporciona estados observables para que la UI pueda reaccionar a los diferentes estados del proceso.

## Constructors

| | |
|---|---|
| [RecoverViewModel](-recover-view-model.md) | [androidJvm]<br>constructor() |

## Properties

| Name | Summary |
|---|---|
| [error](error.md) | [androidJvm]<br>val [error](error.md): [LiveData](https://developer.android.com/reference/kotlin/androidx/lifecycle/LiveData.html)&lt;[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-string/index.html)?&gt;<br>LiveData público que expone los mensajes de error durante el proceso de recuperación. |
| [loading](loading.md) | [androidJvm]<br>val [loading](loading.md): [LiveData](https://developer.android.com/reference/kotlin/androidx/lifecycle/LiveData.html)&lt;[Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-boolean/index.html)&gt;<br>LiveData público que expone el estado de carga durante el proceso de recuperación. |
| [success](success.md) | [androidJvm]<br>val [success](success.md): [LiveData](https://developer.android.com/reference/kotlin/androidx/lifecycle/LiveData.html)&lt;[Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-boolean/index.html)&gt;<br>LiveData público que expone el estado de éxito del proceso de recuperación. |

## Functions

| Name | Summary |
|---|---|
| [addCloseable](../../es.jualas.peliculas.ui.search/-search-view-model/index.md#383812252%2FFunctions%2F-912451524) | [androidJvm]<br>open fun [addCloseable](../../es.jualas.peliculas.ui.search/-search-view-model/index.md#383812252%2FFunctions%2F-912451524)(closeable: [AutoCloseable](https://developer.android.com/reference/kotlin/java/lang/AutoCloseable.html))<br>fun [addCloseable](../../es.jualas.peliculas.ui.search/-search-view-model/index.md#1722490497%2FFunctions%2F-912451524)(key: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-string/index.html), closeable: [AutoCloseable](https://developer.android.com/reference/kotlin/java/lang/AutoCloseable.html)) |
| [clearError](clear-error.md) | [androidJvm]<br>fun [clearError](clear-error.md)()<br>Limpia el estado de error actual. |
| [getCloseable](../../es.jualas.peliculas.ui.search/-search-view-model/index.md#1102255800%2FFunctions%2F-912451524) | [androidJvm]<br>fun &lt;[T](../../es.jualas.peliculas.ui.search/-search-view-model/index.md#1102255800%2FFunctions%2F-912451524) : [AutoCloseable](https://developer.android.com/reference/kotlin/java/lang/AutoCloseable.html)&gt; [getCloseable](../../es.jualas.peliculas.ui.search/-search-view-model/index.md#1102255800%2FFunctions%2F-912451524)(key: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-string/index.html)): [T](../../es.jualas.peliculas.ui.search/-search-view-model/index.md#1102255800%2FFunctions%2F-912451524)? |
| [recoverPassword](recover-password.md) | [androidJvm]<br>fun [recoverPassword](recover-password.md)(email: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-string/index.html))<br>Inicia el proceso de recuperación de contraseña para el correo electrónico proporcionado. |
