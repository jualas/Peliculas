//[app](../../../index.md)/[es.jualas.peliculas.data.repository](../index.md)/[MovieRepository](index.md)/[toggleFavorite](toggle-favorite.md)

# toggleFavorite

[androidJvm]\
suspend fun [toggleFavorite](toggle-favorite.md)(userId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-string/index.html), movieId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-string/index.html), isFavorite: [Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-boolean/index.html)): [Result](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-result/index.html)&lt;[Boolean](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-boolean/index.html)&gt;

Añade o elimina una película de los favoritos de un usuario.

#### Return

[Result](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-result/index.html) que contiene un booleano indicando el nuevo estado de favorito,     o una excepción en caso de error.

#### Parameters

androidJvm

| | |
|---|---|
| userId | ID único del usuario. |
| movieId | ID único de la película a marcar/desmarcar como favorita. |
| isFavorite | `true` para marcar como favorita, `false` para desmarcar. |
