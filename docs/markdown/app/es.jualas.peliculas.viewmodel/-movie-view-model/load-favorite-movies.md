//[app](../../../index.md)/[es.jualas.peliculas.viewmodel](../index.md)/[MovieViewModel](index.md)/[loadFavoriteMovies](load-favorite-movies.md)

# loadFavoriteMovies

[androidJvm]\
fun [loadFavoriteMovies](load-favorite-movies.md)(userId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-string/index.html))

Carga la lista de películas favoritas del usuario desde el repositorio.

#### Parameters

androidJvm

| | |
|---|---|
| userId | Identificador único del usuario cuyos favoritos se desean cargar<br>Este método:<br>1.     Establece el estado de carga a true 2.     Limpia cualquier error previo 3.     Solicita las películas favoritas al repositorio para el usuario especificado 4.     Actualiza el LiveData de favoritos con el resultado 5.     Maneja posibles errores 6.     Finaliza el estado de carga |
