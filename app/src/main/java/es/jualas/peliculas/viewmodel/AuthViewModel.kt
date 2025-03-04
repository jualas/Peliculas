package es.jualas.peliculas.viewmodel

import AuthRepository
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import es.jualas.peliculas.data.model.AuthState
import es.jualas.peliculas.data.model.User
import kotlinx.coroutines.launch

/**
 * ViewModel responsable de gestionar la autenticación de usuarios en la aplicación.
 *
 * Esta clase maneja las operaciones de inicio de sesión, registro, recuperación de contraseña
 * y cierre de sesión, comunicándose con el [AuthRepository] para realizar estas operaciones.
 * Expone los estados de estas operaciones a través de LiveData para que las vistas puedan
 * observar y reaccionar a los cambios.
 */
class AuthViewModel : ViewModel() {
    /** Repositorio que proporciona acceso a las operaciones de autenticación. */
    private val repository = AuthRepository()

    /** LiveData mutable interno para el estado del inicio de sesión. */
    private val _loginState = MutableLiveData<AuthState<User>>(AuthState.Idle)
    /** LiveData público que expone el estado del inicio de sesión. */
    val loginState: LiveData<AuthState<User>> = _loginState

    /** LiveData mutable interno para el estado del registro. */
    private val _registerState = MutableLiveData<AuthState<User>>(AuthState.Idle)
    /** LiveData público que expone el estado del registro. */
    val registerState: LiveData<AuthState<User>> = _registerState

    /** LiveData mutable interno para el estado de la recuperación de contraseña. */
    private val _recoverState = MutableLiveData<AuthState<Unit>>(AuthState.Idle)
    /** LiveData público que expone el estado de la recuperación de contraseña. */
    val recoverState: LiveData<AuthState<Unit>> = _recoverState

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
        _loginState.value = AuthState.Loading
        viewModelScope.launch {
            try {
                val result = repository.login(email, password)
                if (result.isSuccess) {
                    _loginState.value = AuthState.Success(result.getOrNull()!!)
                    _currentUser.value = repository.getCurrentUser()
                } else {
                    _loginState.value = AuthState.Error(
                        result.exceptionOrNull(),
                        result.exceptionOrNull()?.message
                    )
                }
            } catch (e: Exception) {
                _loginState.value = AuthState.Error(e, e.message)
            }
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
        _registerState.value = AuthState.Loading
        viewModelScope.launch {
            try {
                val result = repository.register(email, password, name)
                if (result.isSuccess) {
                    _registerState.value = AuthState.Success(result.getOrNull()!!)
                    _currentUser.value = repository.getCurrentUser()
                } else {
                    _registerState.value = AuthState.Error(
                        result.exceptionOrNull(),
                        result.exceptionOrNull()?.message
                    )
                }
            } catch (e: Exception) {
                _registerState.value = AuthState.Error(e, e.message)
            }
        }
    }
    
    /**
     * Envía una solicitud para recuperar la contraseña del usuario.
     *
     * @param email El correo electrónico asociado a la cuenta cuya contraseña se desea recuperar.
     */
    fun recoverPassword(email: String) {
        _recoverState.value = AuthState.Loading
        viewModelScope.launch {
            try {
                val result = repository.recoverPassword(email)
                if (result.isSuccess) {
                    _recoverState.value = AuthState.Success(Unit)
                } else {
                    _recoverState.value = AuthState.Error(
                        result.exceptionOrNull(),
                        result.exceptionOrNull()?.message
                    )
                }
            } catch (e: Exception) {
                _recoverState.value = AuthState.Error(e, e.message)
            }
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
    
    /**
     * Restablece el estado de inicio de sesión a Idle.
     * Debe llamarse después de consumir un estado Success o Error.
     */
    fun resetLoginState() {
        _loginState.value = AuthState.Idle
    }
    
    /**
     * Restablece el estado de registro a Idle.
     * Debe llamarse después de consumir un estado Success o Error.
     */
    fun resetRegisterState() {
        _registerState.value = AuthState.Idle
    }
    
    /**
     * Restablece el estado de recuperación de contraseña a Idle.
     * Debe llamarse después de consumir un estado Success o Error.
     */
    fun resetRecoverState() {
        _recoverState.value = AuthState.Idle
    }
}