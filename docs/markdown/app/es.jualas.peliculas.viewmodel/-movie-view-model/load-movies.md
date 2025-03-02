//[app](../../../index.md)/[es.jualas.peliculas.viewmodel](../index.md)/[MovieViewModel](index.md)/[loadMovies](load-movies.md)

# loadMovies

[androidJvm]\
fun [loadMovies](load-movies.md)()

Carga la lista de películas desde el repositorio.

Este método:

1. 
   Establece el estado de carga a true
2. 
   Limpia cualquier error previo
3. 
   Solicita las películas al repositorio
4. 
   Actualiza el LiveData correspondiente con el resultado
5. 
   Maneja posibles errores
6. 
   Finaliza el estado de carga
