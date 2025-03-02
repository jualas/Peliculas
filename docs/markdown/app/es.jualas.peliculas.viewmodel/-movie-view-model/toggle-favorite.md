//[app](../../../index.md)/[es.jualas.peliculas.viewmodel](../index.md)/[MovieViewModel](index.md)/[toggleFavorite](toggle-favorite.md)

# toggleFavorite

[androidJvm]\
fun [toggleFavorite](toggle-favorite.md)(userId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-string/index.html), movie: [Movie](../../es.jualas.peliculas.data.model/-movie/index.md))

Cambia el estado de favorito de una película para un usuario específico.

#### Parameters

androidJvm

| | |
|---|---|
| userId | Identificador único del usuario |
| movie | Objeto Movie cuyo estado de favorito se desea cambiar<br>Este método:<br>1.     Determina el nuevo estado de favorito (inverso al actual) 2.     Solicita al repositorio actualizar el estado de favorito 3.     En caso de éxito: 4. -        Actualiza la película en la lista general de películas    -        Actualiza la lista de favoritos (añadiendo o eliminando la película) 5.     Maneja posibles errores durante el proceso |
