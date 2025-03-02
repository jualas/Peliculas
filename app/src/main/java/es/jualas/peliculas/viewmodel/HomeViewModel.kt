package es.jualas.peliculas.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import es.jualas.peliculas.data.model.Movie

/**
 * ViewModel para la pantalla principal de la aplicación.
 * 
 * Esta clase se encarga de gestionar los datos relacionados con las películas,
 * incluyendo la carga desde Firestore, la actualización del estado de favoritos,
 * y la gestión de estados de carga y errores.
 */
class HomeViewModel : ViewModel() {

    /**
     * Lista mutable interna de películas.
     * Este LiveData es privado para evitar modificaciones externas.
     */
    private val _movies = MutableLiveData<List<Movie>>()
    
    /**
     * Lista observable de películas expuesta a la UI.
     * Este LiveData es inmutable para garantizar que solo se modifique desde el ViewModel.
     */
    val movies: LiveData<List<Movie>> = _movies

    /**
     * Estado de carga interno.
     * Indica si hay una operación de carga en progreso.
     */
    private val _isLoading = MutableLiveData<Boolean>()
    
    /**
     * Estado de carga observable expuesto a la UI.
     * Permite a la UI mostrar indicadores de carga cuando es necesario.
     */
    val isLoading: LiveData<Boolean> = _isLoading

    /**
     * Mensaje de error interno.
     * Almacena cualquier mensaje de error que ocurra durante las operaciones.
     */
    private val _error = MutableLiveData<String>()
    
    /**
     * Mensaje de error observable expuesto a la UI.
     * Permite a la UI mostrar mensajes de error al usuario.
     */
    val error: LiveData<String> = _error

    /**
     * Instancia de Firestore para acceder a la base de datos.
     */
    private val firestore = FirebaseFirestore.getInstance()

    /**
     * Carga la lista de películas desde Firestore.
     * 
     * Este método actualiza [_isLoading] mientras se realiza la operación,
     * y al finalizar actualiza [_movies] con los resultados o [_error] si ocurre un error.
     */
    fun loadMovies() {
        _isLoading.value = true

        firestore.collection("movies")
            .get()
            .addOnSuccessListener { documents ->
                val moviesList = documents.mapNotNull { document ->
                    try {
                        val movie = document.toObject(Movie::class.java)
                        // Crear una nueva instancia con el ID correcto
                        movie?.copy(id = document.id)
                    } catch (e: Exception) {
                        null
                    }
                }
                _movies.value = moviesList
                _isLoading.value = false
            }
            .addOnFailureListener { exception ->
                _error.value = exception.message ?: "Error al cargar películas"
                _isLoading.value = false
            }
    }

    /**
     * Cambia el estado de favorito de una película.
     * 
     * Este método actualiza el estado tanto en Firestore como en la lista local
     * de películas, para mantener la UI sincronizada con la base de datos.
     * 
     * @param movie La película cuyo estado de favorito se va a cambiar.
     */
    fun toggleFavorite(movie: Movie) {
        val movieRef = firestore.collection("movies").document(movie.id)

        // Actualizar en Firestore
        movieRef.update("favorite", !movie.isFavorite)
            .addOnSuccessListener {
                // Actualizar la lista local
                val currentList = _movies.value?.toMutableList() ?: mutableListOf()
                val index = currentList.indexOfFirst { it.id == movie.id }
                if (index != -1) {
                    currentList[index] = movie.copy(isFavorite = !movie.isFavorite)
                    _movies.value = currentList
                }
            }
            .addOnFailureListener { exception ->
                _error.value = exception.message ?: "Error al actualizar favorito"
            }
    }
}