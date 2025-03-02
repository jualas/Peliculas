//[app](../../../index.md)/[es.jualas.peliculas.viewmodel](../index.md)/[HomeViewModel](index.md)/[loadMovies](load-movies.md)

# loadMovies

[androidJvm]\
fun [loadMovies](load-movies.md)()

Carga la lista de películas desde Firestore.

Este método actualiza _isLoading mientras se realiza la operación, y al finalizar actualiza _movies con los resultados o _error si ocurre un error.
