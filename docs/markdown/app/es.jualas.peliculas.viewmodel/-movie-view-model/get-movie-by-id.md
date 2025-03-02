//[app](../../../index.md)/[es.jualas.peliculas.viewmodel](../index.md)/[MovieViewModel](index.md)/[getMovieById](get-movie-by-id.md)

# getMovieById

[androidJvm]\
fun [getMovieById](get-movie-by-id.md)(movieId: [String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin-stdlib/kotlin/-string/index.html))

Obtiene los detalles de una película específica por su ID.

#### Parameters

androidJvm

| | |
|---|---|
| movieId | Identificador único de la película a obtener<br>Este método:<br>1.     Establece el estado de carga a true 2.     Limpia cualquier error previo 3.     Solicita al repositorio los detalles de la película especificada 4.     Actualiza el LiveData de película seleccionada con el resultado 5.     Maneja posibles errores 6.     Finaliza el estado de carga |
