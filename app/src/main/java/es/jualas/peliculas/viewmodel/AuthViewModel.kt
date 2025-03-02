package es.jualas.peliculas.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import es.jualas.peliculas.data.model.User
import es.jualas.peliculas.data.repository.AuthRepository
import kotlinx.coroutines.launch

/**
 * ViewModel responsable de gestionar la autenticación de usuarios en la aplicación.
 * 
 * Esta clase maneja las operaciones de inicio de sesión, registro, recuperación de contraseña
 * y cierre de sesión, comunicándose con el [AuthRepository] para realizar estas operaciones.
 * Expone los resultados de estas operaciones a través de LiveData para que las vistas puedan
 * observar y reaccionar a los cambios.
 */
class AuthViewModel : ViewModel() {
    /** Repositorio que proporciona acceso a las operaciones de autenticación. */
    private val repository = AuthRepository()
    
    /** LiveData mutable interno para el resultado del inicio de sesión. */
    private val _loginResult = MutableLiveData<Result<User>>()
    /** LiveData público que expone el resultado del inicio de sesión. */
    val loginResult: LiveData<Result<User>> = _loginResult
    
    /** LiveData mutable interno para el resultado del registro. */
    private val _registerResult = MutableLiveData<Result<User>>()
    /** LiveData público que expone el resultado del registro. */
    val registerResult: LiveData<Result<User>> = _registerResult
    
    /** LiveData mutable interno para el resultado de la recuperación de contraseña. */
    private val _recoverResult = MutableLiveData<Result<Unit>>()
    /** LiveData público que expone el resultado de la recuperación de contraseña. */
    val recoverResult: LiveData<Result<Unit>> = _recoverResult
    
    /** LiveData mutable interno para el usuario actual. */
    private val _currentUser = MutableLiveData<User?>()
    /** LiveData público que expone el usuario actual. */
    val currentUser: LiveData<User?> = _currentUser
    
    /**
     * Inicializa el ViewModel cargando el usuario actual desde el repositorio.
     */
    init {
        _currentUser.value = repository.getCurrentUser()
    }
    
    /**
     * Inicia sesión con las credenciales proporcionadas.
     *
     * @param email El correo electrónico del usuario.
     * @param password La contraseña del usuario.
     */
    fun login(email: String, password: String) {
        viewModelScope.launch {
            _loginResult.value = repository.login(email, password)
            _currentUser.value = repository.getCurrentUser()
        }
    }
    
    /**
     * Registra un nuevo usuario con la información proporcionada.
     *
     * @param email El correo electrónico del nuevo usuario.
     * @param password La contraseña del nuevo usuario.
     * @param name El nombre del nuevo usuario.
     */
    fun register(email: String, password: String, name: String) {
        viewModelScope.launch {
            _registerResult.value = repository.register(email, password, name)
            _currentUser.value = repository.getCurrentUser()
        }
    }
    
    /**
     * Envía una solicitud para recuperar la contraseña del usuario.
     *
     * @param email El correo electrónico asociado a la cuenta cuya contraseña se desea recuperar.
     */
    fun recoverPassword(email: String) {
        viewModelScope.launch {
            _recoverResult.value = repository.recoverPassword(email)
        }
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
     * Obtiene el usuario actualmente autenticado.
     *
     * @return El objeto [User] del usuario actual, o null si no hay ningún usuario autenticado.
     */
    fun getCurrentUser(): User? {
        return repository.getCurrentUser()
    }
}