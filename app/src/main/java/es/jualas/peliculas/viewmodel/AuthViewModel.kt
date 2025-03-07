package es.jualas.peliculas.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import es.jualas.peliculas.data.model.User
import es.jualas.peliculas.data.repository.AuthRepository
import kotlinx.coroutines.launch

/**
 * ViewModel base que proporciona funcionalidad común para la autenticación.
 * 
 * Esta clase contiene la funcionalidad compartida entre diferentes ViewModels
 * relacionados con la autenticación, como obtener el usuario actual y cerrar sesión.
 */
open class AuthViewModel : ViewModel() {
    
    /** Repositorio que proporciona acceso a las operaciones de autenticación. */
    protected val repository = AuthRepository()
    
    /** LiveData mutable interno para el usuario actual. */
    protected val _currentUser = MutableLiveData<User?>()
    /** LiveData público que expone el usuario actual. */
    val currentUser: LiveData<User?> = _currentUser
    
    /**
     * Inicializa el ViewModel cargando el usuario actual desde el repositorio.
     */
    init {
        _currentUser.value = repository.getCurrentUser()
    }
    
    /**
     * Obtiene el usuario actualmente autenticado.
     *
     * @return El objeto [User] del usuario actual, o null si no hay ningún usuario autenticado.
     */
    fun getCurrentUser(): User? {
        return repository.getCurrentUser()
    }
    
    /**
     * Actualiza el usuario actual desde el repositorio.
     * Útil después de operaciones de autenticación como registro o inicio de sesión.
     */
    protected fun updateCurrentUser() {
        _currentUser.value = repository.getCurrentUser()
    }
    
    /**
     * Cierra la sesión del usuario actual.
     * 
     * Llama al método logout del repositorio y establece el usuario actual como nulo.
     */
    fun logout() {
        repository.logout()
        _currentUser.value = null
    }
    
    /**
     * Método auxiliar para realizar la operación de inicio de sesión en el repositorio.
     *
     * @param email El correo electrónico del usuario.
     * @param password La contraseña del usuario.
     * @return Resultado de la operación de inicio de sesión.
     */
    protected suspend fun performLogin(email: String, password: String) = repository.login(email, password)
}