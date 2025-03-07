package es.jualas.peliculas.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import es.jualas.peliculas.data.model.AuthState
import kotlinx.coroutines.launch

/**
 * ViewModel específico para la funcionalidad de registro de usuarios.
 * Hereda de [AuthViewModel] para reutilizar la funcionalidad común.
 */
class RegisterViewModel : AuthViewModel() {
    
    /** LiveData mutable interno para el estado del registro. */
    private val _registerState = MutableLiveData<AuthState>(AuthState.Idle)
    /** LiveData público que expone el estado del registro. */
    val registerState: LiveData<AuthState> = _registerState
    
    /**
     * Registra un nuevo usuario con la información proporcionada.
     *
     * @param email El correo electrónico del nuevo usuario.
     * @param password La contraseña del nuevo usuario.
     * @param name El nombre del nuevo usuario.
     */
    fun register(email: String, password: String, name: String) {
        viewModelScope.launch {
            _registerState.value = AuthState.Loading
            
            try {
                val result = repository.register(email, password, name)
                if (result.isSuccess) {
                    updateCurrentUser()
                    _registerState.value = AuthState.Success(result.getOrNull())
                } else {
                    _registerState.value = AuthState.Error(result.exceptionOrNull()?.message)
                }
            } catch (e: Exception) {
                _registerState.value = AuthState.Error(e.message)
            }
        }
    }
    
    /**
     * Restablece el estado de registro a su valor inicial.
     * Útil después de navegar o manejar un estado de error.
     */
    fun resetRegisterState() {
        _registerState.value = AuthState.Idle
    }
}