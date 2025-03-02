//[app](../../../index.md)/[es.jualas.peliculas.viewmodel](../index.md)/[AuthViewModel](index.md)

# AuthViewModel

[androidJvm]\
class [AuthViewModel](index.md) : [ViewModel](https://developer.android.com/reference/kotlin/androidx/lifecycle/ViewModel.html)

ViewModel responsable de gestionar la autenticación de usuarios en la aplicación.

Esta clase maneja las operaciones de inicio de sesión, registro, recuperación de contraseña y cierre de sesión, comunicándose con el [AuthRepository](../../es.jualas.peliculas.data.repository/-auth-repository/index.md) para realizar estas operaciones. Expone los resultados de estas operaciones a través de LiveData para que las vistas puedan observar y reaccionar a los cambios.

## Constructors

| | |
|---|---|
| [AuthViewModel](-auth-view-model.md) | [androidJvm]<br>constructor() |

## Properties

| Name | Summary |
|---|---|
| [currentUser](current-user.md) | [androidJvm]<br>val [currentUser](current-user.md): [LiveData](https://developer.android.com/reference/kotlin/androidx/lifecycle/LiveData.html)&lt;[User](../../es.jualas.peliculas.data.model/-user/index.md)?&gt;<br>LiveData público que expone el usuario actual. |
| [loginResult](login-result.md) | [androidJvm]<br>val [loginResult](login-result.md): [LiveData](https://developer.android.com/reference/kotlin/androidx/lifecycle/LiveData.html)&lt;[Result](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-result/index.html)&lt;[User](../../es.jualas.peliculas.data.model/-user/index.md)&gt;&gt;<br>LiveData público que expone el resultado del inicio de sesión. |
| [recoverResult](recover-result.md) | [androidJvm]<br>val [recoverResult](recover-result.md): [LiveData](https://developer.android.com/reference/kotlin/androidx/lifecycle/LiveData.html)&lt;[Result](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-result/index.html)&lt;[Unit](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-unit/index.html)&gt;&gt;<br>LiveData público que expone el resultado de la recuperación de contraseña. |
| [registerResult](register-result.md) | [androidJvm]<br>val [registerResult](register-result.md): [LiveData](https://developer.android.com/reference/kotlin/androidx/lifecycle/LiveData.html)&lt;[Result](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-result/index.html)&lt;[User](../../es.jualas.peliculas.data.model/-user/index.md)&gt;&gt;<br>LiveData público que expone el resultado del registro. |

## Functions

| Name | Summary |
|---|---|
| [addCloseable](../../es.jualas.peliculas.ui.search/-search-view-model/index.md#383812252%2FFunctions%2F-912451524) | [androidJvm]<br>open fun [addCloseable](../../es.jualas.peliculas.ui.search/-search-view-model/index.md#383812252%2FFunctions%2F-912451524)(closeable: [AutoCloseable](https://developer.android.com/reference/kotlin/java/lang/AutoCloseable.html))<br>fun [addCloseable](../../es.jualas.peliculas.ui.search/-search-view-model/index.md#1722490497%2FFunctions%2F-912451524)(key: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-string/index.html), closeable: [AutoCloseable](https://developer.android.com/reference/kotlin/java/lang/AutoCloseable.html)) |
| [getCloseable](../../es.jualas.peliculas.ui.search/-search-view-model/index.md#1102255800%2FFunctions%2F-912451524) | [androidJvm]<br>fun &lt;[T](../../es.jualas.peliculas.ui.search/-search-view-model/index.md#1102255800%2FFunctions%2F-912451524) : [AutoCloseable](https://developer.android.com/reference/kotlin/java/lang/AutoCloseable.html)&gt; [getCloseable](../../es.jualas.peliculas.ui.search/-search-view-model/index.md#1102255800%2FFunctions%2F-912451524)(key: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-string/index.html)): [T](../../es.jualas.peliculas.ui.search/-search-view-model/index.md#1102255800%2FFunctions%2F-912451524)? |
| [getCurrentUser](get-current-user.md) | [androidJvm]<br>fun [getCurrentUser](get-current-user.md)(): [User](../../es.jualas.peliculas.data.model/-user/index.md)?<br>Obtiene el usuario actualmente autenticado. |
| [login](login.md) | [androidJvm]<br>fun [login](login.md)(email: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-string/index.html), password: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-string/index.html))<br>Inicia sesión con las credenciales proporcionadas. |
| [logout](logout.md) | [androidJvm]<br>fun [logout](logout.md)()<br>Cierra la sesión del usuario actual. |
| [recoverPassword](recover-password.md) | [androidJvm]<br>fun [recoverPassword](recover-password.md)(email: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-string/index.html))<br>Envía una solicitud para recuperar la contraseña del usuario. |
| [register](register.md) | [androidJvm]<br>fun [register](register.md)(email: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-string/index.html), password: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-string/index.html), name: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-string/index.html))<br>Registra un nuevo usuario con la información proporcionada. |
