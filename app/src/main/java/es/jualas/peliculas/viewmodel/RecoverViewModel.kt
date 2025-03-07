package es.jualas.peliculas.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import es.jualas.peliculas.data.model.AuthState
import kotlinx.coroutines.launch

/**
 * ViewModel específico para la funcionalidad de recuperación de contraseña.
 * Hereda de [AuthViewModel] para reutilizar la funcionalidad común.
 */
class RecoverViewModel : AuthViewModel() {
    
    /** LiveData mutable interno para el estado de la recuperación de contraseña. */
    private val _recoverState = MutableLiveData<AuthState>(AuthState.Idle)
    /** LiveData público que expone el estado de la recuperación de contraseña. */
    val recoverState: LiveData<AuthState> = _recoverState
    
    /**
     * Envía una solicitud para recuperar la contraseña del usuario.
     *
     * @param email El correo electrónico asociado a la cuenta cuya contraseña se desea recuperar.
     */
    fun recoverPassword(email: String) {
        viewModelScope.launch {
            _recoverState.value = AuthState.Loading
            
            try {
                val result = repository.recoverPassword(email)
                if (result.isSuccess) {
                    _recoverState.value = AuthState.Success(null)
                } else {
                    _recoverState.value = AuthState.Error(result.exceptionOrNull()?.message)
                }
            } catch (e: Exception) {
                _recoverState.value = AuthState.Error(e.message)
            }
        }
    }
    
    /**
     * Restablece el estado de recuperación de contraseña a su valor inicial.
     * Útil después de navegar o manejar un estado de error.
     */
    fun resetRecoverState() {
        _recoverState.value = AuthState.Idle
    }
}