package es.jualas.peliculas.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import es.jualas.peliculas.data.model.Movie
import es.jualas.peliculas.data.repository.MovieRepository
import kotlinx.coroutines.launch

/**
 * ViewModel que gestiona los datos relacionados con películas en la aplicación.
 * 
 * Esta clase se encarga de:
 * - Cargar y mantener listas de películas (generales y favoritas)
 * - Gestionar el estado de carga y errores
 * - Proporcionar acceso a los datos de películas a través de LiveData
 * - Manejar la selección de películas individuales
 */
class MovieViewModel : ViewModel() {
    /** Repositorio que proporciona acceso a los datos de películas */
    private val repository = MovieRepository()
    
    /** LiveData mutable interna para la lista de películas */
    private val _movies = MutableLiveData<List<Movie>>()
    /** LiveData pública expuesta para observar la lista de películas */
    val movies: LiveData<List<Movie>> = _movies
    
    /** LiveData mutable interna para la lista de películas favoritas */
    private val _favoriteMovies = MutableLiveData<List<Movie>>()
    /** LiveData pública expuesta para observar la lista de películas favoritas */
    val favoriteMovies: LiveData<List<Movie>> = _favoriteMovies
    
    /** LiveData mutable interna para la película seleccionada actualmente */
    private val _selectedMovie = MutableLiveData<Movie>()
    /** LiveData pública expuesta para observar la película seleccionada */
    val selectedMovie: LiveData<Movie> = _selectedMovie
    
    /** LiveData mutable interna para el estado de carga */
    private val _loading = MutableLiveData<Boolean>()
    /** LiveData pública expuesta para observar el estado de carga */
    val loading: LiveData<Boolean> = _loading
    
    /** LiveData mutable interna para mensajes de error */
    private val _error = MutableLiveData<String?>()
    /** LiveData pública expuesta para observar mensajes de error */
    val error: LiveData<String?> = _error

    /** LiveData mutable interna para el ID de la película seleccionada */
    private val _selectedMovieId = MutableLiveData<String>()
    /** LiveData pública expuesta para observar el ID de la película seleccionada */
    val selectedMovieId: LiveData<String> = _selectedMovieId
    
    /**
     * Carga la lista de películas desde el repositorio.
     * 
     * Este método:
     * 1. Establece el estado de carga a true
     * 2. Limpia cualquier error previo
     * 3. Solicita las películas al repositorio
     * 4. Actualiza el LiveData correspondiente con el resultado
     * 5. Maneja posibles errores
     * 6. Finaliza el estado de carga
     */
    fun loadMovies() {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null
            
            repository.getMovies()
                .onSuccess { movieList ->
                    _movies.value = movieList
                }
                .onFailure { exception ->
                    _error.value = exception.message ?: "Error al cargar películas"
                }
                
            _loading.value = false
        }
    }
    
        /**
     * Carga la lista de películas favoritas del usuario desde el repositorio.
     * 
     * @param userId Identificador único del usuario cuyos favoritos se desean cargar
     * 
     * Este método:
     * 1. Establece el estado de carga a true
     * 2. Limpia cualquier error previo
     * 3. Solicita las películas favoritas al repositorio para el usuario especificado
     * 4. Actualiza el LiveData de favoritos con el resultado
     * 5. Maneja posibles errores
     * 6. Finaliza el estado de carga
     */
    fun loadFavoriteMovies(userId: String) {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null
            
            repository.getFavoriteMovies(userId)
                .onSuccess { favoriteList ->
                    _favoriteMovies.value = favoriteList
                }
                .onFailure { exception ->
                    _error.value = exception.message ?: "Error al cargar favoritos"
                }
                
            _loading.value = false
        }
    }
    
    /**
     * Cambia el estado de favorito de una película para un usuario específico.
     * 
     * @param userId Identificador único del usuario
     * @param movie Objeto Movie cuyo estado de favorito se desea cambiar
     * 
     * Este método:
     * 1. Determina el nuevo estado de favorito (inverso al actual)
     * 2. Solicita al repositorio actualizar el estado de favorito
     * 3. En caso de éxito:
     *    - Actualiza la película en la lista general de películas
     *    - Actualiza la lista de favoritos (añadiendo o eliminando la película)
     * 4. Maneja posibles errores durante el proceso
     */
    fun toggleFavorite(userId: String, movie: Movie) {
        viewModelScope.launch {
            val newFavoriteStatus = !movie.isFavorite
            
            repository.toggleFavorite(userId, movie.id, newFavoriteStatus)
                .onSuccess { success ->
                    // Actualizar la película en la lista de películas
                    _movies.value = _movies.value?.map {
                        if (it.id == movie.id) it.copy(isFavorite = newFavoriteStatus) else it
                    }
                    
                    // Actualizar la lista de favoritos
                    if (newFavoriteStatus) {
                        _favoriteMovies.value = _favoriteMovies.value?.plus(movie.copy(isFavorite = true))
                                                ?: listOf(movie.copy(isFavorite = true))
                    } else {
                        _favoriteMovies.value = _favoriteMovies.value?.filter { it.id != movie.id }
                    }
                }
                .onFailure { exception ->
                    _error.value = exception.message ?: "Error al actualizar favoritos"
                }
        }
    }
    
    /**
     * Obtiene los detalles de una película específica por su ID.
     * 
     * @param movieId Identificador único de la película a obtener
     * 
     * Este método:
     * 1. Establece el estado de carga a true
     * 2. Limpia cualquier error previo
     * 3. Solicita al repositorio los detalles de la película especificada
     * 4. Actualiza el LiveData de película seleccionada con el resultado
     * 5. Maneja posibles errores
     * 6. Finaliza el estado de carga
     */
    fun getMovieById(movieId: String) {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null
            
            repository.getMovieById(movieId)
                .onSuccess { movie ->
                    _selectedMovie.value = movie
                }
                .onFailure { exception ->
                    _error.value = exception.message ?: "Error al cargar detalles de la película"
                }
                
            _loading.value = false
        }
    }

    /**
     * Establece el ID de la película seleccionada actualmente.
     * 
     * @param movieId Identificador único de la película seleccionada
     * 
     * Este método actualiza el LiveData que contiene el ID de la película seleccionada,
     * lo que permite a los observadores reaccionar a este cambio.
     */
    fun selectMovie(movieId: String) {
        _selectedMovieId.value = movieId
    }
    
    /**
     * Limpia cualquier mensaje de error actual.
     * 
     * Este método restablece el LiveData de error a null, lo que permite
     * limpiar notificaciones de error en la interfaz de usuario.
     */
    fun clearError() {
        _error.value = null
    }
}