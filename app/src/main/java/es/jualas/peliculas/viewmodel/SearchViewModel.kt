package es.jualas.peliculas.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import es.jualas.peliculas.data.model.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

/**
 * ViewModel encargado de gestionar la búsqueda de películas en Firestore.
 * 
 * Esta clase maneja las operaciones de búsqueda de películas por título y género,
 * así como el estado de carga y los posibles errores durante el proceso.
 */
class SearchViewModel : ViewModel() {

    /** Instancia de Firestore para acceder a la base de datos */
    private val db = FirebaseFirestore.getInstance()
    
    /** Instancia de FirebaseAuth para gestionar la autenticación del usuario */
    private val auth = FirebaseAuth.getInstance()
    
    /**
     * LiveData mutable que contiene los resultados de la búsqueda de películas.
     * Este valor es privado y solo se modifica dentro del ViewModel.
     */
    private val _searchResults = MutableLiveData<List<Movie>>()
    
    /**
     * LiveData público que expone los resultados de búsqueda a los observadores.
     * Este valor es inmutable desde fuera del ViewModel.
     */
    val searchResults: LiveData<List<Movie>> = _searchResults
    
    /**
     * LiveData mutable que indica si hay una operación de búsqueda en curso.
     * Este valor es privado y solo se modifica dentro del ViewModel.
     */
    private val _isLoading = MutableLiveData<Boolean>()
    
    /**
     * LiveData público que expone el estado de carga a los observadores.
     * Este valor es inmutable desde fuera del ViewModel.
     */
    val isLoading: LiveData<Boolean> = _isLoading
    
    /**
     * LiveData mutable que contiene mensajes de error durante las operaciones.
     * Este valor es privado y solo se modifica dentro del ViewModel.
     */
    private val _error = MutableLiveData<String?>()
    
    /**
     * LiveData público que expone los mensajes de error a los observadores.
     * Este valor es inmutable desde fuera del ViewModel.
     */
    val error: LiveData<String?> = _error
    
    /**
     * Realiza una búsqueda de películas en Firestore basada en el texto proporcionado.
     * 
     * La búsqueda se realiza tanto por título (películas cuyo título comienza con la consulta)
     * como por género (películas que contienen el género especificado).
     * 
     * @param query Texto de búsqueda introducido por el usuario
     */
    fun searchMovies(query: String) {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                
                val results = withContext(Dispatchers.IO) {
                    // Búsqueda en Firestore
                    val queryLower = query.lowercase()
                    
                    // Buscar por título que comience con la consulta
                    val titleResults = db.collection("movies")
                        .whereGreaterThanOrEqualTo("titleLowercase", queryLower)
                        .whereLessThanOrEqualTo("titleLowercase", queryLower + "\uf8ff")
                        .get()
                        .await()
                        .toObjects(Movie::class.java)
                    
                    // Buscar por género
                    val genreResults = db.collection("movies")
                        .whereArrayContains("genres", queryLower)
                        .get()
                        .await()
                        .toObjects(Movie::class.java)
                    
                    // Combinar resultados y eliminar duplicados
                    (titleResults + genreResults).distinctBy { it.id }
                }
                
                _searchResults.value = results
            } catch (e: Exception) {
                _error.value = "Error al buscar películas: ${e.message}"
                _searchResults.value = emptyList()
            } finally {
                _isLoading.value = false
            }
        }
    }
    
    fun toggleFavorite(movie: Movie) {
        val currentUser = auth.currentUser
        if (currentUser == null) {
            _error.value = "Debes iniciar sesión para guardar favoritos"
            return
        }
        
        viewModelScope.launch {
            try {
                val userId = currentUser.uid
                val favoritesRef = db.collection("users").document(userId)
                    .collection("favorites").document(movie.id)
                
                // Verificar si ya está en favoritos
                val favoriteDoc = favoritesRef.get().await()
                
                if (favoriteDoc.exists()) {
                    // Si existe, eliminar de favoritos
                    favoritesRef.delete().await()
                    _error.value = "Película eliminada de favoritos"
                } else {
                    // Si no existe, añadir a favoritos
                    favoritesRef.set(movie).await()
                    _error.value = "Película añadida a favoritos"
                }
            } catch (e: Exception) {
                _error.value = "Error al actualizar favoritos: ${e.message}"
            }
        }
    }
    
    fun clearError() {
        _error.value = null
    }
}